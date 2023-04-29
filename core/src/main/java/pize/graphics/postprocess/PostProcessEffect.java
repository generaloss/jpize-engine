package pize.graphics.postprocess;

import pize.context.Disposable;
import pize.context.Resizable;

public interface PostProcessEffect extends Disposable, Resizable{

    void begin();
    
    void end();
    
    void end(PostProcessEffect target);

}
