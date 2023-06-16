package pize.graphics.postprocess;

import pize.app.Disposable;
import pize.app.Resizable;

public interface PostProcessEffect extends Disposable, Resizable{

    void begin();
    
    void end();
    
    void end(PostProcessEffect target);

}
