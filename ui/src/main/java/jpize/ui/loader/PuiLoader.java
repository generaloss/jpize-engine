package jpize.ui.loader;

import jpize.ui.component.UIComponent;
import jpize.ui.UIContext;
import jpize.util.res.Resource;

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


    @SuppressWarnings("unchecked")
    public <C extends UIComponent> C loadComp(Resource resource){
        // Lex analysis
        final String string = resource.readString();
        final LinkedList<PuiToken> tokens = PuiLexer.lexAnalysis(string);
        // Parse tokens & Map components
        final PuiParser parser = new PuiParser(mapper, tokens);
        parser.parse();
        // Return
        final UIComponent root = mapper.getRootComponent();
        mapper.reset();
        return (C) root;
    }

    public <C extends UIComponent> C loadComp(File file){
        return loadComp(Resource.external(file));
    }

    public <C extends UIComponent> C loadCompRes(String resourceName){
        return loadComp(Resource.internal(resourceName));
    }

    public <C extends UIComponent> C loadCompFile(String filepath){
        return loadComp(Resource.external(filepath));
    }


    public UIContext loadCtx(Resource resource){
        return new UIContext(loadComp(resource));
    }

    public UIContext loadCtx(File file){
        return loadCtx(Resource.external(file));
    }

    public UIContext loadCtxRes(String resourceName){
        return loadCtx(Resource.internal(resourceName));
    }

    public UIContext loadCtxFile(String filepath){
        return loadCtx(Resource.external(filepath));
    }

}
