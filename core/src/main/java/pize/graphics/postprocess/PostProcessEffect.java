package pize.graphics.postprocess;

import pize.activity.Disposable;
import pize.activity.Resizable;

public interface PostProcessEffect extends Disposable, Resizable{

    void begin();
    
    void end();
    
    void end(PostProcessEffect target);

}
