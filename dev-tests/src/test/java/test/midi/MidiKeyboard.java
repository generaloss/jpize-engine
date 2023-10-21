package test.midi;

import javax.sound.midi.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MidiKeyboard{

    private final MidiDevice device;
    private final List<NoteChannel> noteChannels;
    private volatile boolean closed;

    public MidiKeyboard(){
        this.noteChannels = new CopyOnWriteArrayList<>();

        final MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        MidiDevice device = null;
        Transmitter transmitter = null;

        for(MidiDevice.Info info: infos){
            try{
                device = MidiSystem.getMidiDevice(info);
                System.out.print("Midi device available, ");
            }catch(MidiUnavailableException e){
                System.out.println("Midi unavailable, trying next...");
                continue;
            }

            if(!(device instanceof Sequencer) && !(device instanceof Synthesizer)){
                if(!(device.isOpen())){
                    try{
                        device.open();
                    }catch(MidiUnavailableException e){
                        System.out.println("Unable to open Midi device, trying next...");
                        continue;
                    }
                }

                try{
                    transmitter = device.getTransmitter();
                }catch(MidiUnavailableException e){
                    System.out.println("Failed to get transmitter, trying next...");
                    device.close();
                    continue;
                }
                System.out.println("Valid MIDI controller connected.");
                break;
            }else{
                System.out.println("Not a MIDI keyboard controller, trying next...");
                device = null;
            }
        }

        if(device == null)
            throw new RuntimeException("Unable to connect to a MIDI keyboard controller.");

        this.device = device;

        try{
            transmitter.setReceiver(new Receiver(){

                @Override
                public void send(MidiMessage message, long timeStamp){
                    if(message instanceof ShortMessage shortMessage){
                        switch(shortMessage.getCommand()){
                            case ShortMessage.NOTE_ON -> {
                                final int note = shortMessage.getData1();
                                final int velocity = shortMessage.getData2();
                                for(NoteChannel noteChannel: noteChannels)
                                    noteChannel.noteOn(note, velocity);
                            }
                            case ShortMessage.NOTE_OFF -> {
                                final int note = shortMessage.getData1();
                                for(NoteChannel noteChannel: noteChannels)
                                    noteChannel.noteOff(note);
                            }
                        }
                    }
                }

                @Override
                public void close(){
                    closed = true;
                }

            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public MidiDevice getDevice(){
        return device;
    }

    public boolean isClosed(){
        return closed;
    }

    public void registerNoteChannel(NoteChannel noteChannel){
        noteChannels.add(noteChannel);
    }

    public void unregisterNoteChannel(NoteChannel noteChannel){
        noteChannels.remove(noteChannel);
    }

}
