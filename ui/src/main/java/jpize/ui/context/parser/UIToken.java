package jpize.ui.context.parser;

public class UIToken{

    public final UITokenType type;
    public final String string;

    public UIToken(UITokenType type, String string){
        this.type = type;
        this.string = string;
    }

    @Override
    public String toString(){
        return type + ": '" + string + "'";
    }

}
