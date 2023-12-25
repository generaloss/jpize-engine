package jpize.ui.context.mapper;

import jpize.ui.context.parser.PuiToken;

@FunctionalInterface
public interface PuiTokenParser{

    Object parse(PuiToken... tokens);

}
