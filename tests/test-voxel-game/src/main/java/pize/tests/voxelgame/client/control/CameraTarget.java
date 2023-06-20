package pize.tests.voxelgame.client.control;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;

public interface CameraTarget{
    
    Vec3f getPosition();
    
    EulerAngles getRotation();
    
}
