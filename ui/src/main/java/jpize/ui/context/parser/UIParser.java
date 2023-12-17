package jpize.ui.context.parser;

import jpize.ui.component.UIComponent;
import jpize.ui.context.mapper.UIMapper;

import java.util.*;

public class UIParser{

    private final UIMapper mapper;
    private final Queue<UIToken> tokens;

    public UIParser(UIMapper mapper, Queue<UIToken> tokens){
        this.mapper = mapper;
        this.tokens = tokens;
    }

    public UIMapper getMapper(){
        return mapper;
    }

    /** TOKEN */

    private void require(String name, UITokenType... types){
        final UIToken token = peek();
        for(UITokenType type: types)
            if(type == token.type)
                return;

        throw new RuntimeException("Expected '" + name + "', but found '" + token.string);
    }

    private UIToken next(){
        final UIToken token = tokens.poll();
        if(token == null)
            throw new NoSuchElementException();
        return token;
    }

    private UIToken peek(){
        final UIToken token = tokens.peek();
        if(token == null)
            throw new NoSuchElementException();
        return token;
    }

    private UIToken requireAndGet(String message, UITokenType... types){
        require(message, types);
        return next();
    }

    private void requireAndSkip(String message, UITokenType... types){
        require(message, types);
        next();
    }

    /** PARSE */

    public void parse(){
        while(!tokens.isEmpty())
            parseNext(next());
    }

    private void parseNext(UIToken token){
        if(token.type.isComponent())
            parseComponent(token.string);
    }

    private List<UIToken> parseComponentArgs(){
        UIToken token = peek();
        if(token.type.isOpenBracket()){
            next();
            return Collections.emptyList();
        }

        requireAndSkip("(", UITokenType.OPEN_BRACE);
        final List<UIToken> args = new ArrayList<>();

        token = next();
        while(!token.type.isOpenBrace()){
            args.add(token);
            token = next();
            if(token.type.isComma())
                token = next();
            else
                break;
        }

        requireAndSkip("{", UITokenType.OPEN_BRACKET);
        return args;
    }

    private void parseComponent(String name){
        final List<UIToken> args = parseComponentArgs();
        final UIComponent component = mapper.mapComponent(name, args);

        mapper.beginComponent(component);
        parseComponentFields();
        mapper.endComponent();
    }

    private void parseComponentFields(){
        UIToken token = next();
        while(!token.type.isCloseBracket()){
            parseComponentField(token);
            token = next();
        }
    }

    private void parseComponentField(UIToken token){
        if(token.type.isComponent())
            parseComponent(token.string);
        else if(token.type.isKey())
            parseComponentField(token.string, "");
    }

    private void parseComponentField(String key, String prefix){
        if(!prefix.isEmpty())
            key = prefix + "." + key;

        final UIToken token = next();
        if(token.type.isOpenBrace())
            mapper.mapComponentField(key, parseVector());
        else if(token.type.isOpenBracket())
            parseComponentFieldGroup(key);
        else
            mapper.mapComponentField(key, token);
    }

    private void parseComponentFieldGroup(String prefix){
        UIToken token = next();
        while(!token.type.isCloseBracket()){
            parseComponentField(token.string, prefix);
            token = next();
        }
    }

    private List<UIToken> parseVector(){
        final List<UIToken> values = new ArrayList<>();
        UIToken token = next();
        while(!token.type.isCloseBrace()){
            values.add(token);

            token = next();
            if(token.type.isComma())
                token = next();
            else
                break;
        }
        return values;
    }

}
