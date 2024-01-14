package test.midi;

import jpize.util.math.Mathc;

import java.util.Objects;

public class Note{

    public final int note;
    public final float velocity;
    public final float frequency;

    public Note(int note, int velocity){
        this.note = note;
        this.velocity = velocity / 127F;
        this.frequency = Mathc.pow( Math.pow(2, 1 / 12F), note - 45) * 440;
    }


    @Override
    public int hashCode(){
        return Objects.hash(note);
    }

    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;
        if(object == null || getClass() != object.getClass())
            return false;

        final Note note = (Note) object;
        return this.note == note.note;
    }

}
