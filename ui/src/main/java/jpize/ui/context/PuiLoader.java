package jpize.ui.context;

import jpize.ui.context.mapper.PuiTypeSetter;
import jpize.ui.context.parser.PuiLexer;
import jpize.ui.context.mapper.PuiMapper;
import jpize.ui.context.parser.PuiParser;
import jpize.ui.context.parser.PuiToken;
import jpize.util.file.Resource;

import java.io.File;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class PuiLoader{

    private final PuiMapper mapper;

    public PuiLoader(){
        this.mapper = new PuiMapper();
    }

    public PuiLoader putRes(String name, Object res){
        mapper.putResource(name, res);
        return this;
    }

    public PuiLoader addComponentAlias(String alias, Class<?> componentClass){
        mapper.addComponentAlias(alias, componentClass);
        return this;
    }

    public PuiLoader addTypeSetter(Type type, PuiTypeSetter typeSetter){
        mapper.addTypeSetter(type, typeSetter);
        return this;
    }


    public UIContext load(Resource resource){
        final String string = resource.readString();
        final LinkedList<PuiToken> tokens = PuiLexer.lexAnalysis(string);

        final PuiParser parser = new PuiParser(mapper, tokens);
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
