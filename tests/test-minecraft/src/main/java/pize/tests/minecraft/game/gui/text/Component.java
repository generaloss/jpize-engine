package pize.tests.minecraft.game.gui.text;

import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.gui.text.formatting.Style;
import pize.tests.minecraft.game.gui.text.formatting.StyleFormatting;
import pize.tests.minecraft.game.gui.text.formatting.TextFormatting;

import java.util.*;

public class Component{

    protected final List<Component> components;
    protected final Color color;
    protected final boolean[] style;

    public Component(){
        components = new ArrayList<>();
        color = new Color();
        style = new boolean[StyleFormatting.values().length];
    }


    public Component text(Object objectToString){
        components.add(new TextComponent(
            this,
            String.valueOf(objectToString),
            new Style(color.copy(), style[0], style[1], style[2], style[3], style[4], style[5])
        ));

        return this;
    }

    public Component formattedText(String text){
        StringBuilder textPart = new StringBuilder();

        for(int i = 0; i < text.length(); i++){
            char code = text.charAt(i);
            if(code == TextFormatting.FORMATTING_SYMBOL && i < text.length() - 1){
                if(!textPart.toString().equals("")){
                    text(textPart.toString());
                    textPart = new StringBuilder();
                }

                TextFormatting format = TextFormatting.fromCode(text.charAt(i + 1));
                if(format != null){
                    if(format == TextFormatting.RESET)
                        reset();
                    else if(format.isColor())
                        color(format);
                    else
                        style(format);
                }

                i++;
            }else
                textPart.append(code);
        }

        if(!textPart.toString().equals(""))
            text(textPart.toString());

        return this;
    }

    public Component formattedText(TextComponent[] components){
        Collections.addAll(this.components, components);

        return this;
    }

    public Component translation(String translationKey, Component... args){
        components.add(new TranslationComponent(this, translationKey, args));

        return this;
    }

    public Component component(Component component){
        components.add(component);

        return this;
    }

    public Component color(TextFormatting format){
        color.set(format.color());

        return this;
    }

    public Component color(IColor color){
        this.color.set(color);

        return this;
    }

    public Component style(TextFormatting format){
        this.style[format.style().id] = true;

        return this;
    }

    public Component style(StyleFormatting style){
        this.style[style.id] = true;

        return this;
    }

    public Component reset(){
        color.set(TextFormatting.WHITE.color());
        Arrays.fill(style, false);

        return this;
    }

    public Component clear(){
        components.clear();

        return this;
    }


    public Component getComponent(int index){
        return components.get(index);
    }
    
    public TextComponent getComponentAsTest(int index){
        return (TextComponent) components.get(index);
    }
    
    public TranslationComponent getComponentAsTranslation(int index){
        return (TranslationComponent) components.get(index);
    }

    public int size(){
        return components.size();
    }


    public String getAllText(Session session){
        StringBuilder builder = new StringBuilder();

        for(Component component: components){
            builder.append(
                switch(component.getClass().getSimpleName()){
                    case "TextComponent" -> ((TextComponent) component).getText();
                    case "TranslationComponent" ->{
                        TranslationComponent translationComponent = ((TranslationComponent) component);
                        translationComponent.update(session);
                        yield translationComponent.getAllText(session);
                    }
                    default -> component.getAllText(session);
                }
            );
        }

        return builder.toString();
    }

    public List<TextComponent> getAllComponents(Session session){
        List<TextComponent> allComponents = new ArrayList<>();
        getAllComponents(session, allComponents, this);
        return allComponents;
    }


    private void getAllComponents(Session session, List<TextComponent> allComponents, Component currentComponent){
        for(Component component: currentComponent.components){
            if(component.size() == 0){
                if(component.getClass() == TranslationComponent.class){
                    TranslationComponent translationComponent = ((TranslationComponent) component);
                    translationComponent.update(session);
                    getAllComponents(session, allComponents, translationComponent);
                }else
                    allComponents.add((TextComponent) component);
            }else
                getAllComponents(session, allComponents, component);
        }
    }


}
