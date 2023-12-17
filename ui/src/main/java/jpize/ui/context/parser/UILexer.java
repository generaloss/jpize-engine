package jpize.ui.context.parser;

import java.util.*;
import java.util.regex.Matcher;

public class UILexer{

    public static Queue<UIToken> lexAnalysis(String input){
        final Queue<UIToken> tokens = new LinkedList<>();

        int i = 0;
        final int length = input.length();

        while(i < length){
            final String substring = input.substring(i);

            for(UITokenType type: UITokenType.values()){
                final Matcher matcher = type.match(substring);
                if(!matcher.find())
                    continue;

                i += matcher.end() - 1;
                if(type.isSpaces())
                    break;

                String string = matcher.group();
                if(type.isLiteral())
                    string = string.substring(1, string.length() - 1);
                else if(type.isComponent() || type.isResource())
                    string = string.substring(1);
                else if(type.isKey())
                    string = string.substring(0, string.length() - 1);

                final UIToken token = new UIToken(type, string);
                tokens.add(token);

                break;
            }
            i++;
        }

        return tokens;
    }

}
