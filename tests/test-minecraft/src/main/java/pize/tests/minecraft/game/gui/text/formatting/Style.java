package pize.tests.minecraft.game.gui.text.formatting;

import pize.graphics.util.color.IColor;
import pize.graphics.util.color.ImmutableColor;

public class Style{

    public static final Style DEFAULT = new Style(TextFormatting.WHITE.color());


    public final ImmutableColor color;
    public final boolean bold;
    public final boolean italic;
    public final boolean underline;
    public final boolean strikethrough;
    public final boolean obfuscated;
    public final boolean obfuscated_numbers;

    public Style(IColor color, boolean bold, boolean italic, boolean underline, boolean strikethrough, boolean obfuscated, boolean obfuscated_numbers){
        this.color = new ImmutableColor(color);
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.obfuscated_numbers = obfuscated_numbers;
    }

    public Style(IColor color, boolean... style){
        this.color = new ImmutableColor(color);
        this.bold = style.length > 0 && style[0];
        this.italic = style.length > 1 && style[1];
        this.underline = style.length > 2 && style[2];
        this.strikethrough = style.length > 3 && style[3];
        this.obfuscated = style.length > 4 && style[4];
        this.obfuscated_numbers = style.length > 5 && style[5];
    }

}
