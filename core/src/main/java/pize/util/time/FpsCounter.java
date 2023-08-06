package pize.util.time;

public class FpsCounter{

    private long prevTime;
    private int counter, fps;

    public void count(){
        long currentTime = System.currentTimeMillis();

        long difference = currentTime - prevTime;
        if(difference > 1000){
            prevTime = currentTime;

            fps = counter;
            counter = 0;
        }else
            counter++;
    }

    public int get(){
        return fps;
    }

}
