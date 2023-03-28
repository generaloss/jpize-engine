package glit.graphics.postprocess;

import glit.context.Disposable;
import glit.context.Resizable;

public interface PostProcessEffect extends Disposable, Resizable{

    void begin();
    
    void end();
    
    void end(PostProcessEffect target);

}
