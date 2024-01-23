package jpize.ui.context.parser;

import java.util.*;
import java.util.regex.Matcher;

public class PuiLexer{

    public static LinkedList<PuiToken> lexAnalysis(String input){
        final LinkedList<PuiToken> tokens = new LinkedList<>();

        int line = 1;
        int symbol = 1;

        int i = 0;
        final int length = input.length();

        while(i < length){
            final String substring = input.substring(i);

            for(PuiTokenType type: PuiTokenType.values()){
                final Matcher matcher = type.match(substring);
                if(!matcher.find())
                    continue;

                if(type.isNextLine()){
                    symbol = 0;
                    line++;
                }else
                    symbol += matcher.end();

                i += matcher.end() - 1;
                if(type.isSpaces() || type.isComment() || type.isNextLine())
                    break;

                String string = matcher.group();
                if(type.isLiteral())
                    string = string.substring(1, string.length() - 1).replace("\\n", "\n");
                else if(type.isComponent() || type.isResource() || type.isAlias())
                    string = string.substring(1);
                else if(type.isKey())
                    string = string.substring(0, string.length() - 1);

                final PuiToken token = new PuiToken(type, string, line, symbol - string.length());
                tokens.add(token);

                break;
            }
            i++;
        }

        return tokens;
    }

}
