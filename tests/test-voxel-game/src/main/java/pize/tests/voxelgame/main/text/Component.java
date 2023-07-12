package pize.tests.voxelgame.main.text;

import pize.graphics.util.color.Color;

import java.util.ArrayList;
import java.util.List;

public class Component{
    
    private final List<Component> components;
    private final TextStyle style;
    private final Color color;
    
    public Component(TextStyle style, Color color){
        this.components = new ArrayList<>();
        this.style = style.copy();
        this.color = color.copy();
    }
    
    public Component(){
        this.components = new ArrayList<>();
        this.style = new TextStyle();
        this.color = new Color();
    }
    
    
    
    public Component component(Component component){
        components.add(component);
        return this;
    }
    
    
    public Component style(StyleFormatting styleFormatting){
        style.enable(styleFormatting);
        return this;
    }
    
    public Component color(TextColor textColor){
        this.color.set(textColor.color);
        return this;
    }
    
    public Component color(double r, double g, double b){
        this.color.set(r, g, b, 1.0);
        return this;
    }
    
    public Component reset(){
        style.reset();
        color.reset();
        return this;
    }
    
    public Component text(String text){
        final ComponentText component = new ComponentText(style, color, text);
        components.add(component);
        return this;
    }
    
    public Component text(Object objectToString){
        return text(String.valueOf(objectToString));
    }
    
    public Component translate(String translateKey, Component... arguments){
        final ComponentTranslate component = new ComponentTranslate(style, color, translateKey, arguments);
        components.add(component);
        return this;
    }
    
    public Component formattedText(String text){
        text(text);
        return this;
    }
    
    
    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(Component component: components)
            builder.append(component.toString());
    
        return builder.toString();
    }
    
    
    public List<ComponentText> toFlatList(){
        final List<ComponentText> list = new ArrayList<>();
        this.addToFlatList(list);
        return list;
    }
    
    private void addToFlatList(List<ComponentText> list){
        for(Component component: components){
            if(component instanceof ComponentText componentText)
                list.add(componentText);
            
            component.addToFlatList(list);
        }
    }
    
    
    public void addComponent(Component component){
        components.add(component);
    }
    
    public TextStyle getStyle(){
        return style;
    }
    
    public Color getColor(){
        return color;
    }
    
}
