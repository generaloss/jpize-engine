package pize.util;

public class StringUtils{

    public static boolean isBlank(String string){
        int length;
        if(string == null || (length = string.length()) == 0)
            return true;

        for(int i = 0; i < length; i++)
            if(!Character.isWhitespace(string.charAt(i)))
                return false;

        return true;
    }

    public static int count(String string, String pattern){
        int count = 0;
        for(int i = 0; i < string.length() - pattern.length() + 1; i += pattern.length())
            if(string.startsWith(pattern, i))
                count++;
        
        return count;
    }
    
}
