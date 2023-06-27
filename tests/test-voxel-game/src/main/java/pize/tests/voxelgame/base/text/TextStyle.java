package pize.tests.voxelgame.base.text;

public class TextStyle implements Cloneable{
    
    private byte style;
    
    public TextStyle(){
        style = 0;
    }
    
    public TextStyle(TextStyle textStyle){
        style = textStyle.style;
    }
    
    public TextStyle(byte data){
        style = data;
    }
    
    
    public void enable(StyleFormatting style){
        this.style |= (1 << style.styleID);
    }
    
    public void reset(){
        style = 0;
    }
    
    
    public boolean isItalic(){
        return (style & (1 << StyleFormatting.ITALIC.styleID)) == 1;
    }
    
    public boolean isBold(){
        return (style & (1 << StyleFormatting.BOLD.styleID)) == 1;
    }
    
    
    @Override
    public TextStyle clone(){
        return new TextStyle(this);
    }
    
    public byte getData(){
        return style;
    }
    
}
