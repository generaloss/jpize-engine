package jpize.ui.context.mapper;

import jpize.graphics.util.color.Color;
import jpize.ui.component.UIComponent;
import jpize.ui.constraint.*;
import jpize.ui.context.UIContext;
import jpize.ui.context.parser.UIToken;
import jpize.ui.context.parser.UITokenType;
import jpize.ui.palette.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class UIMapper{

    private final UIContext context;
    private final Deque<UIComponent> componentPath;

    private final Map<String, Object> resources;
    private final Map<UITokenType, UITokenParser> tokenParsers;
    private final Map<Type, UITypeSetter> typeSetters;
    private final Map<String, Class<?>> componentAliases;

    public UIMapper(){
        this.context = new UIContext();
        this.componentPath = new ArrayDeque<>();

        this.resources = new HashMap<>();

        this.tokenParsers = new HashMap<>();
        putTokenParsers();
        this.typeSetters = new HashMap<>();
        putTypeParsers();
        this.componentAliases = new HashMap<>();
        putComponentAliases();
    }

    public UIContext getContext(){
        return context;
    }

    public void beginComponent(UIComponent component){
        componentPath.addFirst(component);
    }

    public void endComponent(){
        componentPath.pop();
    }

    public void putResource(String name, Object resource){
        resources.put(name, resource);
    }

    public void addComponentAlias(String alias, Class<?> componentClass){
        componentAliases.put(alias, componentClass);
    }

    private void putTokenParsers(){
        // String
        tokenParsers.put(UITokenType.LITERAL_1, (tokens) -> tokens[0].string);
        tokenParsers.put(UITokenType.LITERAL_2, (tokens) -> tokens[0].string);
        // Number
        tokenParsers.put(UITokenType.NUMBER, (tokens) -> Float.parseFloat(tokens[0].string));
        // Resource
        tokenParsers.put(UITokenType.RESOURCE, (tokens) -> resources.get(tokens[0].string));
        // Constraint
        tokenParsers.put(UITokenType.CONSTRAINT, (tokens) -> {
            final String constr = tokens[0].string;
            final String numPart = constr.substring(0, constr.length() - 2);
            final String postfix = constr.substring(constr.length() - 2);
            return switch(postfix){
                case "px" -> Constr.px(Float.parseFloat(numPart));
                case "rw" -> Constr.relw(Float.parseFloat(numPart));
                case "rh" -> Constr.relh(Float.parseFloat(numPart));
                case "ap" -> Constr.aspect(Float.parseFloat(numPart));
                default -> ConstraintFlag.fromString(constr);
            };
        });
    }

    private void putTypeParsers(){
        // Insets
        typeSetters.put(Insets.class, (object, args) -> {
            switch(args.length){
                case 1 -> ((Insets) object).set((Constraint) args[0]);
                case 2 -> ((Insets) object).set((Constraint) args[0], (Constraint) args[1]);
                case 4 -> ((Insets) object).set((Constraint) args[0], (Constraint) args[1], (Constraint) args[2], (Constraint) args[3]);
            }
        });
        // Dimension
        typeSetters.put(Dimension.class, (object, args) -> {
            switch(args.length){
                case 1 -> ((Dimension) object).set((Constraint) args[0]);
                case 2 -> ((Dimension) object).set((Constraint) args[0], (Constraint) args[1]);
            }
        });
        // Color
        typeSetters.put(Color.class, (object, args) -> {
            switch(args.length){
                case 1 -> ((Color) object).setA((float) args[0]);
                case 3 -> ((Color) object).setRgb((float) args[0], (float) args[1], (float) args[2]);
                case 4 -> ((Color) object).set((float) args[0], (float) args[1], (float) args[2], (float) args[3]);
            }
        });
    }

    private void putComponentAliases(){
        addComponentAlias("TextView", TextView.class);
        addComponentAlias("Button", Button.class);
        addComponentAlias("Slider", Slider.class);
        addComponentAlias("Rect", Rect.class);

        addComponentAlias("ConstraintLayout", ConstraintLayout.class);
        addComponentAlias("HBox", HBox.class);
        addComponentAlias("VBox", VBox.class);
    }

    /** MAPPER */

    public Object parseTokenToObject(UIToken token){
        final UITokenParser parser = tokenParsers.get(token.type);
        if(parser == null)
            return null;
        return parser.parse(token);
    }

    public List<Object> parseTokensToObjects(List<UIToken> tokens){
        final List<Object> objects = new ArrayList<>();
        for(UIToken token: tokens)
            objects.add(parseTokenToObject(token));
        return objects;
    }

    public Constructor<?> findComponentConstructor(Constructor<?>[] constructors, List<Object> arguments){
        constructorsCycle:
        for(Constructor<?> constructor: constructors){
            if(constructor.getParameterCount() != arguments.size())
                continue;

            final Class<?>[] constructorParams = constructor.getParameterTypes();
            for(int i = 0; i < arguments.size(); i++){
                final Class<?> argumentClass = arguments.get(i).getClass();
                if(constructorParams[i] != argumentClass && constructorParams[i] != argumentClass.getSuperclass()){
                    continue constructorsCycle;
                }
            }
            return constructor;
        }
        return null;
    }

    public Class<?> getComponentClass(String name) throws ClassNotFoundException{
        if(componentAliases.containsKey(name))
            return componentAliases.get(name);
        return Class.forName(name);
    }

    @SuppressWarnings("unchecked")
    public UIComponent mapComponent(String name, List<UIToken> args){
        try{
            final Class<?> componentClass = getComponentClass(name);
            final Constructor<?>[] componentConstructors = componentClass.getConstructors();
            final List<Object> argsObj = parseTokensToObjects(args);

            final Constructor<UIComponent> constructor = (Constructor<UIComponent>) findComponentConstructor(componentConstructors, argsObj);
            if(constructor == null)
                throw new RuntimeException("No such constructor for class " + name);

            final UIComponent component = constructor.newInstance(argsObj.toArray());
            if(componentPath.isEmpty())
                context.setRootComponent(component);
            else
                componentPath.peek().add(component);
            return component;

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public Field getFieldOfSuperclasses(Object object, String name){
        Class<?> c = object.getClass();
        while(c != null){
            try{
                final Field field = c.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            }catch(Exception ignored){
                c = c.getSuperclass();
            }
        }
        return null;
    }


    public void setFieldByKey(Object object, String key, Object value){
        try{
            if(key.contains(".")){
                final String[] links = key.split("\\.");
                Field field = getFieldOfSuperclasses(object, links[0]);
                for(int i = 1; i < links.length; i++){
                    if(field == null)
                        break;

                    final String link = links[i];
                    object = field.get(object);
                    field = getFieldOfSuperclasses(object, link);
                }
                if(field != null)
                    field.set(object, value);
            }else{
                final Field field = getFieldOfSuperclasses(object, key);
                if(field != null)
                    field.set(object, value);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public ObjField getFieldByKey(Object object, String key){
        try{
            if(key.contains(".")){
                final String[] links = key.split("\\.");
                Field field = getFieldOfSuperclasses(object, links[0]);
                for(int i = 1; i < links.length; i++){
                    if(field == null)
                        break;

                    final String link = links[i];
                    object = field.get(object);
                    field = getFieldOfSuperclasses(object, link);
                }
                if(field != null){
                    object = field.get(object);
                    return new ObjField(field, object);
                }
            }else{
                final Field field = getFieldOfSuperclasses(object, key);
                if(field != null){
                    object = field.get(object);
                    return new ObjField(field, object);
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public void mapComponentField(String key, UIToken valueToken){
        final UIComponent component = componentPath.peek();
        final Object valueObject = parseTokenToObject(valueToken);
        setFieldByKey(component, key, valueObject);
    }

    public void mapComponentField(String key, List<UIToken> vectorPartsTokens){
        final UIComponent component = componentPath.peek();
        final ObjField field = getFieldByKey(component, key);
        if(field == null)
            throw new RuntimeException("Field " + key + " of " + component + " doesn't exists");

        final UITypeSetter setter = typeSetters.get(field.object.getClass());
        if(setter == null)
            throw new RuntimeException("UITypeSetters doesn't exists for " + field.object.getClass());

        final Object[] args = parseTokensToObjects(vectorPartsTokens).toArray();
        setter.set(field.object, args);
    }

}
