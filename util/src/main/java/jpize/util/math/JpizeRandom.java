package jpize.util.math;

import java.util.Random;

public class JpizeRandom{

    private final Random random;

    public JpizeRandom(){
        random = new Random();
    }

    public JpizeRandom(long seed){
        random = new Random(seed);
    }

    public JpizeRandom(int seed){
        random = new Random(seed);
    }

    public Random getRandom(){
        return random;
    }


    public void setSeed(long seed){
        random.setSeed(seed);
    }

    public void setSeed(int seed){
        random.setSeed(seed);
    }


    public float random(){
        return random.nextFloat();
    }

    public int random(int start, int end){
        return start + random.nextInt(end - start + 1);
    }

    public int random(int max){
        return random.nextInt() * max;
    }

    public float random(float start, float end){
        return start + random.nextFloat() * (end - start);
    }

    public float random(float max){
        return random.nextFloat() * max;
    }

    public long random(long start, long end){
        final long rand = random.nextLong();
        if(end < start){
            long t = end;
            end = start;
            start = t;
        }
        long bound = end - start + 1L;
        final long randLow = rand & 0xFFFFFFFFL;
        final long boundLow = bound & 0xFFFFFFFFL;
        final long randHigh = (rand >>> 32);
        final long boundHigh = (bound >>> 32);
        return start + (randHigh * boundLow >>> 32) + (randLow * boundHigh >>> 32) + randHigh * boundHigh;
    }

    public long random(long max){
        return random.nextLong() * max;
    }

    public boolean randomBoolean(){
        return random.nextBoolean();
    }

    public boolean randomBoolean(float chance){
        return random.nextFloat() < chance;
    }

    public boolean randomBoolean(double chance){
        return random.nextDouble() < chance;
    }

    public int randomSign(){
        return 1 | (random.nextInt() >> 31);
    }
    
    
    public void randomBytes(byte[] bytes){
        random.nextBytes(bytes);
    }

}
