package jpize.ui.loader;

public class PuiToken{

    public final PuiTokenType type;
    public final String string;
    public final int line;
    public final int symbol;

    public PuiToken(PuiTokenType type, String string, int line, int symbol){
        this.type = type;
        this.string = string;
        this.line = line;
        this.symbol = symbol;
    }

    @Override
    public String toString(){
        return type + ": '" + string + "'";
    }

}
