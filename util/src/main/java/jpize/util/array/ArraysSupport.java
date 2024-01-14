package jpize.util.array;

public class ArraysSupport{

    public static int newLength(int oldLength, int minGrowth, int prefGrowth){
        final int prefLength = oldLength + Math.max(minGrowth, prefGrowth);
        return 0 < prefLength && prefLength <= 2147483639 ? prefLength : hugeLength(oldLength, minGrowth);
    }

    private static int hugeLength(int oldLength, int minGrowth){
        final int minLength = oldLength + minGrowth;
        if(minLength < 0)
            throw new OutOfMemoryError("Required array length " + oldLength + " + " + minGrowth + " is too large");
        return Math.max(minLength, 2147483639);
    }

}
