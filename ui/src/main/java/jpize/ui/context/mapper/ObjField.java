package jpize.ui.context.mapper;

import java.lang.reflect.Field;

public class ObjField{

    public final Field field;
    public final Object object;

    public ObjField(Field field, Object object){
        this.field = field;
        this.object = object;
    }

}
