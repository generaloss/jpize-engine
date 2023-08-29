package jpize.tests.minecraft.gui.text.formatting;

import jpize.graphics.util.color.ImmutableColor;

public class ColorFormatting implements ITextFormatting{

    private final ImmutableColor color;

    public ColorFormatting(int r, int g, int b){
        this.color = new ImmutableColor(r, g, b, 255);
    }

    public ImmutableColor getColor(){
        return color;
    }

}
