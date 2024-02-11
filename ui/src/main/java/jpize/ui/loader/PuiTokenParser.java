package jpize.ui.loader;

@FunctionalInterface
public interface PuiTokenParser{

    Object parse(PuiToken... tokens);

}
