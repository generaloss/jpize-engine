package glit.util.time;

public class FpsCounter{

    private long lastTime;
    private int tps, tpsCount;

    public void update(){
        long currentTime = System.currentTimeMillis();

        long difference = currentTime - lastTime;
        if(difference > 1000){
            lastTime = currentTime;

            tps = tpsCount;
            tpsCount = 0;
        }else
            tpsCount++;
    }

    public int get(){
        return tps;
    }

}
