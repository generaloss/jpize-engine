package pize.tests.minecraft.game.gui.text.formatting;

import pize.graphics.util.color.ImmutableColor;

public enum TextFormatting{

    BLACK              ('0', new ColorFormatting(0  , 0  , 0  )),
    DARK_BLUE          ('1', new ColorFormatting(0  , 0  , 170)),
    DARK_GREEN         ('2', new ColorFormatting(0  , 170, 0  )),
    DARK_AQUA          ('3', new ColorFormatting(0  , 170, 170)),
    DARK_RED           ('4', new ColorFormatting(170, 0  , 0  )),
    DARK_PURPLE        ('5', new ColorFormatting(170, 0  , 170)),
    GOLD               ('6', new ColorFormatting(255, 170, 0  )),
    GRAY               ('7', new ColorFormatting(170, 170, 170)),
    DARK_GRAY          ('8', new ColorFormatting(85 , 85 , 85 )),
    BLUE               ('9', new ColorFormatting(85 , 85 , 255)),
    GREEN              ('a', new ColorFormatting(85 , 255, 85 )),
    AQUA               ('b', new ColorFormatting(85 , 255, 255)),
    RED                ('c', new ColorFormatting(255, 85 , 85 )),
    LIGHT_PURPLE       ('d', new ColorFormatting(255, 85 , 255)),
    YELLOW             ('e', new ColorFormatting(255, 255, 85 )),
    WHITE              ('f', new ColorFormatting(255, 255, 255)),

    //                  BISON XDDD
    BOLD               ('b', StyleFormatting.BOLD              ),
    ITALIC             ('i', StyleFormatting.ITALIC            ),
    UNDERLINE          ('u', StyleFormatting.UNDERLINE         ),
    STRIKETHROUGH      ('s', StyleFormatting.STRIKETHROUGH     ),
    OBFUSCATED         ('o', StyleFormatting.OBFUSCATED        ),
    OBFUSCATED_NUMBERS ('n', StyleFormatting.OBFUSCATED_NUMBERS),

    RESET              ('r', null);


    public static final char FORMATTING_SYMBOL = '§';


    public final char code;
    public final ITextFormatting formatting;

    TextFormatting(char code, ITextFormatting formatting){
        this.code = code;
        this.formatting = formatting;
    }

    public ImmutableColor color(){
        return ((ColorFormatting) formatting).getColor();
    }

    public StyleFormatting style(){
        return (StyleFormatting) formatting;
    }


    public boolean isStyle(){
        return formatting.getClass().equals(StyleFormatting.class);
    }

    public boolean isColor(){
        return formatting.getClass().equals(ColorFormatting.class);
    }


    public static TextFormatting fromCode(char code){
        for(TextFormatting textFormatting: TextFormatting.values())
            if(textFormatting.code == code)
                return textFormatting;

        return null;
    }

}
