package jpize.graphics.postprocess;

import jpize.app.Disposable;
import jpize.app.Resizable;

public interface PostProcessEffect extends Disposable, Resizable{

    void begin();
    
    void end();
    
    void end(PostProcessEffect target);

}
