package pize.tests.minecraft.game.gui.text;

import pize.graphics.font.FontCharset;
import pize.math.Maths;
import pize.tests.minecraft.game.gui.text.formatting.Style;

public class TextComponent extends Component{

    private String text;
    private Style style;

    protected TextComponent(Component parent, String text, Style style){
        this.text = text;

        if(parent != null && (style == Style.DEFAULT || style == null))
            this.style = new Style(parent.color, parent.style);
        else
            this.style = style;
    }


    public String getText(){
        if(style.obfuscated){
            StringBuilder obfuscatedBuilder = new StringBuilder();
            for(int i = 0; i < text.length(); i++)
                obfuscatedBuilder.append(FontCharset.DEFAULT.charAt(Maths.random(0, FontCharset.DEFAULT.size() - 1)));

            return obfuscatedBuilder.toString();
        }else if(style.obfuscated_numbers){
            StringBuilder obfuscatedBuilder = new StringBuilder();
            for(int i = 0; i < text.length(); i++)
                obfuscatedBuilder.append(FontCharset.NUMBERS.charAt(Maths.random(0, FontCharset.NUMBERS.size() - 1)));

            return obfuscatedBuilder.toString();
        }

        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public Style getStyle(){
        return style;
    }

    public void setStyle(Style style){
        this.style = style;
    }

}
