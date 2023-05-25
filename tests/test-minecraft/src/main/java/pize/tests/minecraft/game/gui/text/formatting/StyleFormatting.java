package pize.tests.minecraft.game.gui.text.formatting;

public enum StyleFormatting implements ITextFormatting{

    BOLD(0),
    ITALIC(1),
    UNDERLINE(2),
    STRIKETHROUGH(3),
    OBFUSCATED(4),
    OBFUSCATED_NUMBERS(5);



    public final int id;

    StyleFormatting(int id){
        this.id = id;
    }

}
