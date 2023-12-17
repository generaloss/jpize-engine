package jpize.ui.context.mapper;

import jpize.ui.context.parser.UIToken;

@FunctionalInterface
public interface UITokenParser{

    Object parse(UIToken... tokens);

}
