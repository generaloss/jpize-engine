package pize.tests.voxelgame.main.text;

import pize.graphics.util.color.ImmutableColor;

public enum TextColor{
    
    BLACK        (new ImmutableColor(0  , 0  , 0  , 255)),
    DARK_BLUE    (new ImmutableColor(0  , 0  , 170, 255)),
    DARK_GREEN   (new ImmutableColor(0  , 170, 0  , 255)),
    DARK_AQUA    (new ImmutableColor(0  , 170, 170, 255)),
    DARK_RED     (new ImmutableColor(200, 0  , 0  , 255)),
    DARK_PURPLE  (new ImmutableColor(170, 0  , 170, 255)),
    ORANGE       (new ImmutableColor(255, 170, 0  , 255)),
    GRAY         (new ImmutableColor(170, 170, 170, 255)),
    DARK_GRAY    (new ImmutableColor(85 , 85 , 85 , 255)),
    BLUE         (new ImmutableColor(30 , 144, 255, 255)),
    GREEN        (new ImmutableColor(85 , 255, 85 , 255)),
    AQUA         (new ImmutableColor(85 , 255, 255, 255)),
    RED          (new ImmutableColor(255, 85 , 85 , 255)),
    LIGHT_PURPLE (new ImmutableColor(255, 85 , 255, 255)),
    YELLOW       (new ImmutableColor(255, 255, 85 , 255)),
    WHITE        (new ImmutableColor(255, 255, 255, 255));
    
    
    public final ImmutableColor color;
    
    TextColor(ImmutableColor color){
        this.color = color;
    }
    
}
