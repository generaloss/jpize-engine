package jpize.ui.loader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PuiTokenType{

    NEXT_LINE     ("next line", "^\\n"),
    SPACES        ("spaces",    "^\\s+"),

    OPEN_BRACKET  ("{", "^\\{"),
    CLOSE_BRACKET ("}", "^\\}"),
    OPEN_BRACE    ("(", "^\\("),
    CLOSE_BRACE   (")", "^\\)"),
    COMMA         (",", "^,"  ),

    ALIAS         ("$alias"    , "^\\$[\\w\\.]+" ),
    COMMENT       ("comment"   , "^#.*[^\\n\\r]" ),
    COMPONENT     ("@Component", "^@[\\w\\.]+"   ),
    CONSTRAINT    ("constraint", "^(?:(?:[0-9\\.]+(?:px|rw|rh|ap))|auto|zero|match_parent|wrap_content)"),
    LITERAL       ("literal"   , "^(?:(?:'(?:\\\\.|[^\\'\\\\])*')|(?:\"(?:\\\\.|[^\\\"\\\\])*\"))"      ),
    NUMBER        ("number"    , "^[0-9]+(?:(?:\\.[0-9]+)(?:(?:e|E)(\\+|\\-|)[0-9]+|)|)"),
    BOOL          ("bool"      , "^(?:true|false|True|False|TRUE|FALSE)"),
    KEY           ("key:"      , "^[\\w\\.]+:"   ),
    RESOURCE      ("!resource" , "^![\\w\\.\\:]+");

    private final String name;
    private final Pattern pattern;

    PuiTokenType(String name, String regex){
        this.name = name;
        this.pattern = Pattern.compile(regex);
    }

    public String toString(){
        return "'" + name + "'";
    }

    public Matcher match(String input){
        return pattern.matcher(input);
    }


    public boolean isSpaces(){
        return this == SPACES;
    }

    public boolean isNextLine(){
        return this == NEXT_LINE;
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

    public boolean isComment(){
        return this == COMMENT;
    }

    public boolean isLiteral(){
        return this == LITERAL;
    }

    public boolean isComponent(){
        return this == COMPONENT;
    }

    public boolean isConstraint(){
        return this == CONSTRAINT;
    }

    public boolean isNumber(){
        return this == NUMBER;
    }

    public boolean isBool(){
        return this == BOOL;
    }

    public boolean isKey(){
        return this == KEY;
    }

    public boolean isAlias(){
        return this == ALIAS;
    }

    public boolean isResource(){
        return this == RESOURCE;
    }

}
