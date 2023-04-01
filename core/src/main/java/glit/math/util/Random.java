package glit.math.util;

public class Random{

    private final java.util.Random random;

    public Random(long seed){
        random = new java.util.Random(seed);
    }


    public float random(){
        return random.nextFloat();
    }

    public int random(int start, int end){
        return start + random.nextInt(end - start + 1);
    }

    public float random(float start, float end){
        return start + random.nextFloat() * (end - start);
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
