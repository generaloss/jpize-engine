package test.midi;

import jpize.io.context.JpizeApplication;
import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.util.Utils;

public class MidiTest extends JpizeApplication{

    public static final int SAMPLE_RATE = 44100;
    public static final int CHANNELS = 1;


    final NoteChannel noteChannel = new NoteChannel();
    final MidiKeyboard keyboard = new MidiKeyboard();

    final AudioOutputStream output = new AudioOutputStream(SAMPLE_RATE, CHANNELS, 512);

    public MidiTest(){
        keyboard.registerNoteChannel(noteChannel);

        while(!Thread.interrupted() && !keyboard.isClosed()){
            Thread.yield();

            output.clearWriteBuffer();
            renderAudioInBuffer();


            if(!noteChannel.isEmpty())
                output.render();

            Utils.delayElapsed(output.getRenderBuffer().audioBuffer.getDurationSec());

            output.swapBuffers();
        }
    }

    public void renderAudioInBuffer(){
        final Note[] pressedNotes = noteChannel.getPressed();
        for(final Note note: pressedNotes){
            float sinPos = 0;
            final float sinPosInc = note.frequency / SAMPLE_RATE;

            final short[] buffer = output.getWriteBuffer().dataBuffer;

            for(int j = 0; j < buffer.length; j += CHANNELS){
                final short sample = (short) (Short.MAX_VALUE * Mathc.sin(sinPos * Maths.twoPI) / pressedNotes.length);
                buffer[j] += sample;

                sinPos += sinPosInc;
                if(sinPos > 1)
                    sinPos -= 1;
            }
        }
    }



}
