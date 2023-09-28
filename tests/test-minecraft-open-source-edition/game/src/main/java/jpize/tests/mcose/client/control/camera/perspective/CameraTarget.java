package jpize.tests.mcose.client.control.camera.perspective;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;

public interface CameraTarget{
    
    Vec3f getPosition();
    
    EulerAngles getRotation();
    
}
