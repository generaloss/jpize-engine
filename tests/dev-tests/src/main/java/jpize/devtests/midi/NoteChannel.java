package jpize.devtests.midi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NoteChannel{

    private final Map<Integer, Note> pressed;

    public NoteChannel(){
        pressed = new ConcurrentHashMap<>();
    }

    public void noteOn(int note, int velocity){
        pressed.put(note, new Note(note, velocity));
    }

    public void noteOff(int note){
        pressed.remove(note);
    }

    public Note[] getPressed(){
        return pressed.values().toArray(new Note[0]);
    }

    public boolean isEmpty(){
        return pressed.values().isEmpty();
    }

}
