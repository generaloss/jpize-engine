package jpize.ui.context.mapper;

import jpize.ui.context.parser.UIToken;

public interface UITokenParser{

    Object parse(UIToken... tokens);

}
