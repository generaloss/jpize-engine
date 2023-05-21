package pize.tests.minecraft.client.game.gui.text;

import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.client.game.gui.text.formatting.Style;
import pize.tests.minecraft.client.game.lang.ClientLanguage;

public class TranslatableComponent extends Component{

    private final Component parent;

    private final String key;
    private final Component[] args;
    private final int[] argsIndices;

    private ClientLanguage decomposedWith;

    protected TranslatableComponent(Component parent, String key, Component... args){
        this.parent = parent;

        this.key = key;
        this.args = args;
        argsIndices = new int[args.length];
    }


    private void decompose(){
        super.components.clear();
        super.color.set(parent.color);
        System.arraycopy(parent.style, 0, super.style, 0, 6);

        String[] parts = decomposedWith.getOrDefault(key).split("%s");

        int argIndex = 0;
        for(String part: parts){
            super.formattedText(part);
            if(argIndex < args.length){
                argsIndices[argIndex] = super.components.size() - 1;
                component(args[argIndex]);
                argIndex++;
            }
        }
    }

    public void update(Session session){
        ClientLanguage currentLanguage = session.getLanguageManager().getLanguage();
        if(decomposedWith != currentLanguage){
            decomposedWith = currentLanguage;
            decompose();
        }
    }


    public Component getArg(int index){
        return getComponent(argsIndices[index]);
    }

    public String getKey(){
        return key;
    }

    public Style getStyle(){
        return Style.DEFAULT;
    }

}
