package megalul.projectvostok.client.control;

import pize.math.util.EulerAngle;
import pize.math.vecmath.tuple.Tuple3f;

public interface CameraTarget{
    
    Tuple3f getPosition();
    
    EulerAngle getDirection();
    
}