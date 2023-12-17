package jpize.ui.context;

import jpize.ui.context.parser.UILexer;
import jpize.ui.context.mapper.UIMapper;
import jpize.ui.context.parser.UIParser;
import jpize.ui.context.parser.UIToken;
import jpize.util.file.Resource;

import java.io.File;
import java.util.Queue;

public class UILoader{

    private final UIMapper mapper;

    public UILoader(){
        this.mapper = new UIMapper();
    }

    public UILoader putRes(String name, Object res){
        mapper.putResource(name, res);
        return this;
    }

    public UILoader addComponentAlias(String alias, Class<?> componentClass){
        mapper.addComponentAlias(alias, componentClass);
        return this;
    }


    public UIContext mapUI(Resource resource){
        final String string = resource.readString();
        final Queue<UIToken> tokens = UILexer.lexAnalysis(string);

        final UIParser parser = new UIParser(mapper, tokens);
        parser.parse();
        return mapper.getContext();
    }

    public UIContext mapUIFromResource(String resourceName){
        return mapUI(Resource.internal(resourceName));
    }

    public UIContext mapUIFromFile(String filepath){
        return mapUI(Resource.external(filepath));
    }

    public UIContext mapUI(File file){
        return mapUI(Resource.external(file));
    }

}
