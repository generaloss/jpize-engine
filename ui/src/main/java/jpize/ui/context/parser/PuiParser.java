package jpize.ui.context.parser;

import jpize.ui.component.UIComponent;
import jpize.ui.context.mapper.PuiMapper;

import java.util.*;

import static jpize.ui.context.parser.PuiTokenType.*;

public class PuiParser{

    private final PuiMapper mapper;
    private final LinkedList<PuiToken> tokens;
    private PuiToken currentToken;

    public PuiParser(PuiMapper mapper, LinkedList<PuiToken> tokens){
        this.mapper = mapper;
        this.tokens = tokens;
    }

    public PuiMapper getMapper(){
        return mapper;
    }

    /** TOKENS */

    private void next(){
        currentToken = tokens.poll();
    }

    private PuiToken current(){
        return currentToken;
    }

    private void error(String message){
        final PuiToken token = current();
        final int line = token.line;
        final int symbol = token.symbol;
        throw new RuntimeException(message + " (" + line + ":" + symbol + ")");
    }

    private void require(PuiTokenType... types){
        final PuiToken token = current();

        for(PuiTokenType type: types)
            if(type == token.type)
                return;

        final StringJoiner joiner = new StringJoiner(" or ");
        for(PuiTokenType type: types)
            joiner.add(type.toString());

        error("Expected " + joiner + ", but found '" + token.string + "'");
    }

    /** PARSER */

    public void parse(){
        next();
        parseAliases();
        parseComponent();
    }

    private void parseAliases(){
        final Map<String, List<PuiToken>> aliases = new HashMap<>();

        while(current().type.isAlias())
            parseAlias(aliases);

        System.out.println("Aliases: " + aliases.size());
        for(List<PuiToken> tokens: aliases.values())
            System.out.println(Arrays.toString(tokens.toArray()));

        for(int i = 0; i < tokens.size(); i++){
            if(!tokens.get(i).type.isAlias())
                continue;

            final PuiToken token = tokens.remove(i);
            List<PuiToken> alias = aliases.get(token.string);
            tokens.addAll(i, alias);
        }
    }

    private void parseAlias(Map<String, List<PuiToken>> aliases){
        final String alias = current().string;
        next();

        final List<PuiToken> aliasTokens = new ArrayList<>();
        aliases.put(alias, aliasTokens);

        while(!tokens.isEmpty()){
            if(current().type.isAlias() || current().type.isComponent())
                break;

            aliasTokens.add(current());
            next();
        }
    }


    private void parseComponent(){
        final PuiToken token = current();
        require(COMPONENT);
        next();
        final List<Object> args = parseComponentArgs();
        final UIComponent component = mapper.mapComponent(token.string, args);
        mapper.beginComponent(component);

        parseComponentBlock();

        mapper.endComponent();
    }

    private List<Object> parseComponentArgs(){
        if(current().type.isOpenBracket()) // '{' => empty args
            return Collections.emptyList();

        return parseVector();
    }

    private void parseField(String prefix){
        require(KEY);
        final String key = current().string;
        next();
        parseValue(key, prefix);
    }

    private void parseValue(String key, String prefix){
        if(!prefix.isEmpty())
            key = prefix + "." + key;

        final PuiToken token = current(); // '{' or '(' or single-value
        if(token.type.isOpenBracket()){
            parseGroup(key);

        }else if(token.type.isOpenBrace()){
            final List<Object> values = parseVector();
            mapper.mapFieldVector(key, values);

        }else if(token.type.isLiteral() || token.type.isNumber() || token.type.isConstraint() || token.type.isResource()){
            final Object value = parseSingleValue();
            mapper.mapFieldValue(key, value);
        }else
            error("Expected group or vector or single-value, but found '" + token.string + "'");
    }

    private void parseComponentBlock(){
        require(OPEN_BRACKET);
        next(); // skip '{'

        while(!tokens.isEmpty()){
            final PuiToken token = current();
            if(token.type.isCloseBracket())
                break;

            if(token.type.isKey())
                parseField("");
            else if(token.type.isComponent())
                parseComponent();
            else
                error("Expected field or component, but found '" + token.string + "'");
        }

        next(); // skip '}'
    }

    private void parseGroup(String prefix){
        require(OPEN_BRACKET);
        next(); // skip '{'

        while(!tokens.isEmpty()){
            if(current().type.isCloseBracket())
                break;
            parseField(prefix);
        }

        next(); // skip '}'
    }

    private Object parseSingleValue(){
        final PuiToken token = current();
        require(LITERAL, NUMBER, CONSTRAINT, RESOURCE);
        next();
        return mapper.parseTokenToObject(token);
    }

    private List<Object> parseVector(){
        require(OPEN_BRACE);
        next(); // skip '('

        if(current().type.isCloseBrace()){
            next(); // skip ')'
            return Collections.emptyList();
        }

        final List<Object> values = new ArrayList<>();

        while(!tokens.isEmpty()){
            final Object value = parseSingleValue();
            values.add(value);

            require(COMMA, CLOSE_BRACE);

            if(current().type.isCloseBrace())
                break;

            next(); // skip ','
        }
        next(); // skip ')'

        return values;
    }

}
