package jpize.graphics.postprocess;

import jpize.util.Disposable;
import jpize.util.Resizable;

public interface PostProcessEffect extends Disposable, Resizable{

    void begin();
    
    void end();
    
    void end(PostProcessEffect target);

}
