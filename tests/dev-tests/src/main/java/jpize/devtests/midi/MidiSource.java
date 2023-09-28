package jpize.devtests.midi;

/******************************************************************************
 *  Compilation:  javac MidiSource.java
 *  Execution:    java MidiSource  [-p] [filename.mid]
 *
 *  A MidiSource object produces MIDI (Musical Instrument Digital Interface)
 *  messages, where the source can be a  hardware MIDI controller
 *  keyboard or a MIDI file.   Generating MIDI messages from a MIDI
 *  file uses the default Java MIDI Sequencer, so that MIDI messages are
 *  scheduled appropriately.
 *
 *  Version: .3
 *
 ******************************************************************************/

import javax.sound.midi.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 The {@code MidiSource} class is used to create objects that produce MIDI
 (Musical Instrument Digital Interface) messages.  The source can be a
 hardware MIDI controller keyboard or a MIDI file.   Generating MIDI messages
 from a MIDIfile uses the default Java MIDI Sequencer, so that MIDI messages
 are scheduled appropriately.

 @author Nico Toy
 @author Alan Kaplan */

public final class MidiSource{

    // keep track if source if "live" controller or static file
    private static final int MIDI_CONTROLLER = 0;
    private static final int MIDI_FILE = 1;
    private int sourceType;

    // queue for midi messages- produced by MIDI transmitter (keyboard controller or sequencer)
    private LinkedBlockingDeque<MidiMessage> midiMessageQueue;
    private MidiDevice device;       // hardware keyboard controller
    private Sequencer sequencer;    // Java MIDI sequencer

    private boolean verbose = false;    // indicates if MidiSource should print information
    // about MidiMessages to stdout as messages are
    // produced

    private boolean playSynth = false;  // indicates if MidiSource should play notes using
    // default Java Synthesizer as messages are
    // produced


    // MetaMessage event code for end of track
    private static final int MIDI_END_OF_TRACK = 47;

    // short message field names
    private static final HashMap<Integer, String> SM_FIELDS = MidiSource.setShortMessageFields();

    private static HashMap<Integer, String> setShortMessageFields(){
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        Field[] declaredFields = ShortMessage.class.getDeclaredFields();
        for(Field field: declaredFields){
            if(java.lang.reflect.Modifier.isStatic(field.getModifiers())){
                try{
                    map.put(field.getInt(null), field.getName());
                }catch(IllegalAccessException e){
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return map;
    }

    /**
     Helper method - pretty prints a MidiMessage
     */
    private static void print(MidiMessage message){
        if(message instanceof ShortMessage){
            ShortMessage shortMessage = (ShortMessage) message;
            if(shortMessage.getCommand() != 240){ // some MIDI controllers continousouly output a 240
                System.out.print("ShortMessage: ");
                System.out.print(" Command: " + SM_FIELDS.get(shortMessage.getCommand()) + " (" + shortMessage.getCommand() + ") ");
                System.out.print(" Channel: " + shortMessage.getChannel());
                if(shortMessage.getCommand() == ShortMessage.NOTE_ON){
                    System.out.print(" Number:   " + shortMessage.getData1());
                    System.out.print(" Velocity: " + shortMessage.getData2());
                }else if(shortMessage.getCommand() == ShortMessage.NOTE_OFF){
                    System.out.print(" Number:   " + shortMessage.getData1());
                    System.out.print(" Velocity: " + shortMessage.getData2());
                }else if(shortMessage.getCommand() == ShortMessage.CONTROL_CHANGE){
                    System.out.print(" Number:   " + shortMessage.getData1());
                    System.out.print(" Data2:    " + shortMessage.getData2());
                }else{
                    System.out.print(" Data1:    " + shortMessage.getData1());
                    System.out.print(" Data2:    " + shortMessage.getData2());
                }
                System.out.println();
            }else if(message instanceof SysexMessage){
                System.out.println("SysexMessage");
            }else if(message instanceof MetaMessage){
                System.out.print("MetaMessage: ");
                MetaMessage metaMessage = (MetaMessage) message;
                System.out.println(metaMessage.getType());
            }else{
            }
        }
    }


    /**
     Private helper class that receives MidiMessages, and
     adds each MIDI message received to a MidiSource queue. Optionally
     (1) prints messages to stdout and (2) plays messages using Java
     Synthesizer
     */
    private class MidiKeyboardControllerReceiver implements Receiver{
        private boolean verbose = false; // default - do not print message to stdout
        private boolean playSynth = false; // default - do not play synthesizer
        private Synthesizer synth = null;  // default Java Synthesizer
        private MidiChannel[] channels = null;  // defaul - Java Sythesizer channels

        public MidiKeyboardControllerReceiver(boolean verbose, boolean playSynth){
            midiMessageQueue = new LinkedBlockingDeque<>();
            this.verbose = verbose;
            this.playSynth = playSynth;

            // if this Receiver needs to play notes, set up channels
            if(playSynth){
                try{
                    synth = MidiSystem.getSynthesizer();
                }catch(MidiUnavailableException e){
                    e.printStackTrace();
                    System.exit(1);
                }
                try{
                    synth.open();
                }catch(MidiUnavailableException e){
                    e.printStackTrace();
                    System.exit(1);
                }
                channels = synth.getChannels();
            }
        }

        @Override
        // Invoked each time Receiver gets a MidiMessage
        public void send(MidiMessage message, long timeStamp){
            // add the message to the queue
            midiMessageQueue.add(message);

            // print message?
            if(verbose)
                print(message);

            // play this note for a keyboard controller
            if(playSynth)
                if(message instanceof ShortMessage){
                    ShortMessage shortMessage = (ShortMessage) message;
                    if(shortMessage.getCommand() == ShortMessage.NOTE_ON)
                        channels[shortMessage.getChannel()].noteOn(shortMessage.getData1(), shortMessage.getData2());
                    else if(shortMessage.getCommand() == ShortMessage.NOTE_OFF){
                        channels[shortMessage.getChannel()].noteOff(shortMessage.getData1(), shortMessage.getData2());
                    }
                }
        }


        // close the Receiver stream
        public void close(){
            synth.close();
            midiMessageQueue = null;

        }
    }


    /**
     Private helper class that receives MidiMessages, and
     adds each MIDI message received to a MidiSource queue. Optionally
     prints messages to stdout
     */
    private class MidiFileReceiver implements Receiver{
        private boolean verbose = false; // default - do not print message to stdout

        public MidiFileReceiver(boolean verbose){
            midiMessageQueue = new LinkedBlockingDeque<MidiMessage>();
            this.verbose = verbose;
        }

        @Override
        // Invoked each time Receiver gets a MidiMessage
        public void send(MidiMessage message, long timeStamp){
            // add the message to the queue
            midiMessageQueue.add(message);

            // print message?
            if(verbose)
                print(message);

        }


        // close the Receiver stream
        public void close(){
            midiMessageQueue = null;

        }

    }


    /**
     Search for connected Midi Keyboard controller.   If found, returns a
     a openned MidiDevice.

     @param verbose log information about the device to stdout
     */
    private static MidiDevice openMidiController(boolean verbose){

        // get installed Midi devices
        MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
        MidiDevice device = null;
        for(int i = 0; i < deviceInfo.length; i++){
            if(verbose){
                System.out.print("DEVICE " + i + ": ");
                System.out.print(deviceInfo[i].getName() + ", ");
                System.out.print(deviceInfo[i].getVendor() + ", ");
                System.out.print(deviceInfo[i].getDescription() + ", ");
            }
            try{
                device = MidiSystem.getMidiDevice(deviceInfo[i]);
                if(verbose)
                    System.out.print("Midi device available, ");
            }catch(MidiUnavailableException e){
                if(verbose)
                    System.out.println("Midi unavailable, trying next...");
                continue;
            }

            // To detect if a MidiDevice represents a hardware MIDI port:
            // https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/MidiDevice.html
            if(!(device instanceof Sequencer) && !(device instanceof Synthesizer)){
                if(!(device.isOpen())){
                    try{
                        device.open();
                    }catch(MidiUnavailableException e){
                        if(verbose)
                            System.out.println("Unable to open Midi device, trying next...");
                        continue;
                    }
                }

                // check for a valid Transmitter
                try{
                    Transmitter transmitter = device.getTransmitter();
                }catch(MidiUnavailableException e){
                    if(verbose)
                        System.out.println("Failed to get transmitter, trying next...");
                    device.close();
                    continue;
                }
                if(verbose)
                    System.out.println("Valid MIDI controller connected.");
                break;
            }else{
                if(verbose)
                    System.out.println("Not a MIDI keyboard controller, trying next...");
                device = null;
            }
        }
        return device;
    }


    /**
     Creates a MIDISource object listens to the first found connected MIDI input device.

     @param verbose        true turns on logging
     @param connectToSynth use default Java sound synthesizer

     @throws RuntimeException if no device was found or if writing to the log
     file failed
     */
    public MidiSource(boolean verbose, boolean connectToSynth){
        MidiDevice keyboard = openMidiController(verbose);
        if(keyboard == null)
            throw new RuntimeException("Unable to connect to a MIDI keyboard controller.");

        try{
            Transmitter transmitter = keyboard.getTransmitter();
            transmitter.setReceiver(new MidiKeyboardControllerReceiver(verbose, connectToSynth));
            sourceType = MIDI_CONTROLLER;
        }catch(MidiUnavailableException e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     *
     * @param connectToSynth use default Java sound synthesizer
     * @throws RuntimeException if no device was found or if writing to the log
     *                          file failed
     */


    /**
     Creates a MIDISource object the produces MIDI messages from a
     time-stamped MIDI file, where each
     each message is buffered and becomes available for consumption by the
     client once it is "played" from the file

     @param filename       the name of the file to play from
     @param verbose        true turns on logging
     @param connectToSynth true if Sequencer should connect to Sequencer

     @throws RuntimeException if the file is not found or not a valid MIDI
     file, or if reading from the file failed
     */
    public MidiSource(String filename, boolean verbose, boolean connectToSynth){

        playSynth = connectToSynth;
        sourceType = MIDI_FILE;
        try{
            sequencer = MidiSystem.getSequencer(connectToSynth);
        }catch(MidiUnavailableException e){
            e.printStackTrace();
        }

        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream(filename);
        }catch(FileNotFoundException e){
            throw new RuntimeException("File not found");
        }


        // connect file to sequencer
        try{
            sequencer.setSequence(fileInputStream);
            sequencer.getTransmitter().setReceiver(new MidiFileReceiver(verbose));
        }catch(IOException e){
            throw new RuntimeException("Error reading file: " + filename);
        }catch(InvalidMidiDataException e){
            throw new RuntimeException("Invalid MIDI file: " + filename);
        }catch(MidiUnavailableException e){
            throw new RuntimeException("MIDI unavailable: " + filename);
        }

        try{
            // Add a listener for meta message events
            sequencer.addMetaEventListener(new MetaEventListener(){
                public void meta(MetaMessage event){
                    // close the Sequencer when done
                    if(event.getType() == MIDI_END_OF_TRACK){
                        // Sequencer is done playing
                        close();

                    }
                }
            });
            sequencer.open();
        }catch(MidiUnavailableException e){
            e.printStackTrace();
        }

    }

    /**
     Starts the MIDISource so it can produce messages.
     */
    public void start(){
        if(sourceType == MIDI_CONTROLLER){
        }else if(sourceType == MIDI_FILE)
            sequencer.start();
        else
            throw new RuntimeException("MidiSource: Illegal source type: " + sourceType);
    }

    /**
     Return whether there are new MIDI messages available.

     @return true if and only if there are new messages available to consume
     */
    public boolean isEmpty(){
        return midiMessageQueue.size() == 0;
    }

    /**
     Return the next available short MIDI message (in FIFO order). All messages,
     including the short MIDI message are  "consumed", i.e., it will no longer be
     available after this call.  Returns null if queue is empty.

     @return The next available {@link MidiMessage}
     */
    private ShortMessage getMidiMessage(){
        while(!this.isEmpty()){
            MidiMessage message = midiMessageQueue.remove();
            if(message instanceof ShortMessage)
                return (ShortMessage) message;
            // ignore other messages
        }
        return null; // if empty
    }

    /**
     Return the code of the MIDIController key pressed.

     @return code of key pressed
     */
    public int nextKeyPressed(){
        ShortMessage message = getMidiMessage();
        if(message == null)
            return -1;
        else if(message.getCommand() == ShortMessage.NOTE_ON)
            return message.getData1();
        else
            return -1;
    }

    /**
     Return the next short MIDI message in the queue.

     @return Short MIDI message, null otherwise
     */
    public ShortMessage nextMessage(){
        ShortMessage message = getMidiMessage();
        if(message == null)
            return null;
        else
            return message;
    }

    /**
     Static helper method. Extract the key code from a short
     MIDI message, where commmand == NOTE_ON

     @param message ShortMessage object

     @return key code number
     */
    public static int getKey(ShortMessage message){
        return message.getData1();
    }

    /**
     Static helper method. Extract the velocity from a short
     MIDI message, where commmand == NOTE_ON

     @param message ShortMessage object

     @return key code number
     */
    public static int getVelocity(ShortMessage message){
        return message.getData2();
    }

    /**
     Static helper method. Extract the channel from a short
     MIDI message.

     @param message ShortMessage object

     @return channel number
     */
    public static int getChannel(ShortMessage message){
        return message.getChannel();
    }

    /**
     Either stop listening for input from the device or stop playback from
     the MIDI file.
     */
    public void close(){
        if(sourceType == MIDI_CONTROLLER && device.isOpen()){
            device.close();
        }else if(sourceType == MIDI_FILE){
            sequencer.stop();
            sequencer.close();
        }
    }

    /**
     Return whether this MidiSource is still active

     @return if listening from device, true if and only if this instance is
     still listening; if using from file, true if and only if the
     playback is still active
     */
    public boolean isActive(){
        if(sourceType == MIDI_CONTROLLER){
            return device.isOpen();
        }else if(sourceType == MIDI_FILE){
            return sequencer.isRunning();
        }else{
            return false;
        }
    }

}
