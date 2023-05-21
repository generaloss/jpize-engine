package pize.util.time;

public class PerSecCounter{

    private long prevTime;
    private int counter, count;

    public void count(){
        long currentTime = System.currentTimeMillis();

        long difference = currentTime - prevTime;
        if(difference > 1000){
            prevTime = currentTime;

            count = counter;
            counter = 0;
        }else
            counter++;
    }

    public int get(){
        return count;
    }

}
