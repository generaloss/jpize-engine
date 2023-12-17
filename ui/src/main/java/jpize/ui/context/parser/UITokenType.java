package jpize.ui.context.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UITokenType{

    SPACES        ("^\\s+"),

    OPEN_BRACKET  ("^\\{"),
    CLOSE_BRACKET ("^\\}"),
    OPEN_BRACE    ("^\\("),
    CLOSE_BRACE   ("^\\)"),
    COMMA         ("^,"),

    COMPONENT     ("^@[\\w\\.]+"),
    CONSTRAINT    ("^(?:(?:[0-9\\.]+(?:px|rw|rh|ap))|auto|zero|match_parent|wrap_content)"),
    LITERAL_1     ("^'[\\w\\s\\.\\/\\\\{}\\[\\]()\\-\\+\\:]*'"),
    LITERAL_2     ("^\"[\\w\\s\\.\\/\\\\{}\\[\\]()\\-\\+\\:]*\""),
    NUMBER        ("^[0-9]+(?:(?:\\.[0-9]+)(?:(?:e|E)(\\+|\\-|)[0-9]+|)|)"),
    KEY           ("^[\\w\\.]+:"),
    RESOURCE      ("^![\\w\\.\\:]+");

    private final Pattern pattern;

    UITokenType(String regex){
        this.pattern = Pattern.compile(regex);
    }

    public Matcher match(String input){
        return pattern.matcher(input);
    }


    public boolean isSpaces(){
        return this == SPACES;
    }

    public boolean isOpenBracket(){
        return this == OPEN_BRACKET;
    }

    public boolean isCloseBracket(){
        return this == CLOSE_BRACKET;
    }

    public boolean isOpenBrace(){
        return this == OPEN_BRACE;
    }

    public boolean isCloseBrace(){
        return this == CLOSE_BRACE;
    }

    public boolean isComma(){
        return this == COMMA;
    }

    public boolean isLiteral(){
        return this == LITERAL_1 || this == LITERAL_2;
    }

    public boolean isComponent(){
        return this == COMPONENT;
    }

    public boolean isConstraint(){
        return this == CONSTRAINT;
    }

    public boolean isKey(){
        return this == KEY;
    }

    public boolean isResource(){
        return this == RESOURCE;
    }

}
