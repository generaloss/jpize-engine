package pize.util;

public class StringUtils{

    public static boolean isBlank(final CharSequence charSequence){
        int length;
        if(charSequence == null || (length = charSequence.length()) == 0)
            return true;

        for(int i = 0; i < length; i++)
            if(!Character.isWhitespace(charSequence.charAt(i)))
                return false;

        return true;
    }

}
