package pize.graphics.postprocess;

import pize.util.Disposable;
import pize.util.Resizable;

public interface PostProcessEffect extends Disposable, Resizable{

    void begin();
    
    void end();
    
    void end(PostProcessEffect target);

}
