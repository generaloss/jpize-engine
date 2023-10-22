package jpize.graphics.font;

public class FontCharset{

    public static final FontCharset SPECIAL_SYMBOLS = new FontCharset("~`!@#$%^&*()-_+={}[]|\\/:;\"'<>,.? ");
    public static final FontCharset NUMBERS = new FontCharset("0123456789");
    public static final FontCharset ENG = new FontCharset("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    public static final FontCharset RUS = new FontCharset("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя");
    public static final FontCharset DEFAULT = new FontCharset(SPECIAL_SYMBOLS.toString() + NUMBERS + ENG);
    public static final FontCharset DEFAULT_ENG_RUS = new FontCharset(SPECIAL_SYMBOLS.toString() + NUMBERS + ENG + RUS);


    private String charset;
    private int minChar, maxChar;

    public FontCharset(String charset){
        set(charset);
    }


    private void set(String charset){
        this.charset = charset;

        maxChar = 0;
        for(int i = 0; i < charset.length(); i++)
            maxChar = Math.max(maxChar, charset.charAt(i));

        minChar = maxChar;
        for(int i = 0; i < charset.length(); i++)
            minChar = Math.min(minChar, charset.charAt(i));
    }


    public boolean contains(char character){
        return charset.contains(String.valueOf(character));
    }

    public char charAt(int index){
        return charset.charAt(index);
    }

    public int size(){
        return charset.length();
    }

    public int getFirstChar(){
        return minChar;
    }

    public int getLastChar(){
        return maxChar;
    }


    public String toString(){
        return charset;
    }

}
