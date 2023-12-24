package jpize.ui.context;

import jpize.ui.context.mapper.UITypeSetter;
import jpize.ui.context.parser.UILexer;
import jpize.ui.context.mapper.UIMapper;
import jpize.ui.context.parser.UIParser;
import jpize.ui.context.parser.UIToken;
import jpize.util.file.Resource;

import java.io.File;
import java.lang.reflect.Type;
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

    public UILoader addTypeSetter(Type type, UITypeSetter typeSetter){
        mapper.addTypeSetter(type, typeSetter);
        return this;
    }


    public UIContext load(Resource resource){
        final String string = resource.readString();
        final Queue<UIToken> tokens = UILexer.lexAnalysis(string);

        final UIParser parser = new UIParser(mapper, tokens);
        parser.parse();
        return mapper.getContext();
    }

    public UIContext loadRes(String resourceName){
        return load(Resource.internal(resourceName));
    }

    public UIContext loadFile(String filepath){
        return load(Resource.external(filepath));
    }

    public UIContext load(File file){
        return load(Resource.external(file));
    }

}
