package jpize.physic.axisaligned.box;

import jpize.math.vecmath.vector.Vec3f;

import java.util.Collection;

public class AABoxCollider{

    public static boolean isIntersects(Vec3f movement, AABoxBody body1, AABoxBody... otherBodies){
        body1 = body1.copy();
        body1.getPosition().add(movement);

        for(AABoxBody body2 : otherBodies)
            if(body1.isIntersectsBox(body2))
                return true;

        return false;
    }

    public static boolean isIntersects(Vec3f movement, AABoxBody body1, Collection<AABoxBody> otherBodies){
        body1 = body1.copy();
        body1.getPosition().add(movement);

        for(AABoxBody body2 : otherBodies)
            if(body1.isIntersectsBox(body2))
                return true;

        return false;
    }


    public static Vec3f getCollidedMovement(Vec3f movement, AABoxBody body1, AABoxBody... otherBodies){
        // If movement == 0, return 0
        if(movement.isZero() || otherBodies == null)
            return movement;

        // Copy body for safe addition to body.position.X & Y for correct calculation movementY & Z
        body1 = body1.copy();

        // Calculate
        final float movementX = minMovementX(movement.x, body1, otherBodies);
        body1.getPosition().x += movementX;
        final float movementY = minMovementY(movement.y, body1, otherBodies);
        body1.getPosition().y += movementY;
        final float movementZ = minMovementZ(movement.z, body1, otherBodies);

        // Return calculated movement
        return new Vec3f(movementX, movementY, movementZ);
    }


    private static float minMovementX(float movementX, AABoxBody body1, AABoxBody[] otherBodies){
        // Iterate other bodies
        for(AABoxBody body2: otherBodies){
            if(movementX == 0)
                break;

            // Minimize movement
            movementX = distX(movementX, body1, body2);
        }
        return movementX;
    }

    private static float minMovementY(float movementY, AABoxBody body1, AABoxBody[] otherBodies){
        for(AABoxBody body2: otherBodies){
            if(movementY == 0)
                break;

            movementY = distY(movementY, body1, body2);
        }
        return movementY;
    }

    private static float minMovementZ(float movementZ, AABoxBody body1, AABoxBody[] otherBodies){
        for(AABoxBody body2: otherBodies){
            if(movementZ == 0)
                break;

            movementZ = distZ(movementZ, body1, body2);
        }
        return movementZ;
    }


    public static Vec3f getCollidedMovement(Vec3f movement, AABoxBody body1, Collection<AABoxBody> otherBodies){
        // If movement == 0, return 0
        if(movement.isZero() || otherBodies == null)
            return movement;

        // Copy body for safe addition to body.position.X & Y for correct calculation movementY & Z
        body1 = body1.copy();

        // Calculate
        final float movementX = minMovementX(movement.x, body1, otherBodies);
        body1.getPosition().x += movementX;
        final float movementY = minMovementY(movement.y, body1, otherBodies);
        body1.getPosition().y += movementY;
        final float movementZ = minMovementZ(movement.z, body1, otherBodies);

        // Return calculated movement
        return new Vec3f(movementX, movementY, movementZ);
    }


    private static float minMovementX(float movementX, AABoxBody body1, Collection<AABoxBody> otherBodies){
        // Iterate other bodies
        for(AABoxBody body2: otherBodies){
            if(movementX == 0)
                break;

            // Minimize movement
            movementX = distX(movementX, body1, body2);
        }
        return movementX;
    }

    private static float minMovementY(float movementY, AABoxBody body1, Collection<AABoxBody> otherBodies){
        for(AABoxBody body2: otherBodies){
            if(movementY == 0)
                break;

            movementY = distY(movementY, body1, body2);
        }
        return movementY;
    }

    private static float minMovementZ(float movementZ, AABoxBody body1, Collection<AABoxBody> otherBodies){
        for(AABoxBody body2: otherBodies){
            if(movementZ == 0)
                break;

            movementZ = distZ(movementZ, body1, body2);
        }
        return movementZ;
    }


    private static float distX(float movementX, AABoxBody body1, AABoxBody body2){
        // Ensure that the bodies intersect on the other axes and that collision is possible
        if(body2.getMax().y > body1.getMin().y && body2.getMin().y < body1.getMax().y &&
           body2.getMax().z > body1.getMin().z && body2.getMin().z < body1.getMax().z){

            // When moving positively:
            if(movementX > 0){
                // Find body1 and body2 sides between which the distance to the collision is calculated
                final float body1Side = Math.max(body1.getMin().x, body1.getMax().x);
                final float body2Side = Math.min(body2.getMin().x, body2.getMax().x);
                final float distance = body2Side - body1Side;

                // If the collision distance is less than planned to move them
                if(distance >= 0 && distance < movementX)
                    // Return the distance as a move
                    return distance;

            // When moving negatively:
            }else{
                // Find body1 and body2 sides between which the distance to the collision is calculated
                final float body1Side = Math.min(body1.getMin().x, body1.getMax().x);
                final float body2Side = Math.max(body2.getMin().x, body2.getMax().x);
                final float distance = body2Side - body1Side;

                // If the collision distance is less than planned to move them (-distance < -movementX  =  distance > movementX)
                if(distance <= 0 && distance > movementX)
                    // Return the distance as a move
                    return distance;
            }
        }

        // If the movementX is less than the collision distance - do nothing
        return movementX;
    }
    
    private static float distY(float movementY, AABoxBody body1, AABoxBody body2){
        if(body2.getMax().x > body1.getMin().x && body2.getMin().x < body1.getMax().x &&
           body2.getMax().z > body1.getMin().z && body2.getMin().z < body1.getMax().z){

            if(movementY > 0){
                final float body1Side = Math.max(body1.getMin().y, body1.getMax().y);
                final float body2Side = Math.min(body2.getMin().y, body2.getMax().y);
                final float distance = body2Side - body1Side;

                if(distance >= 0 && distance < movementY)
                    return distance;

            }else{
                final float body1Side = Math.min(body1.getMin().y, body1.getMax().y);
                final float body2Side = Math.max(body2.getMin().y, body2.getMax().y);
                final float distance = body2Side - body1Side;

                if(distance <= 0 && distance > movementY)
                    return distance;
            }
        }

        return movementY;
    }
    
    private static float distZ(float movementZ, AABoxBody body1, AABoxBody body2){
        if(body2.getMax().x > body1.getMin().x && body2.getMin().x < body1.getMax().x &&
           body2.getMax().y > body1.getMin().y && body2.getMin().y < body1.getMax().y){

            if(movementZ > 0){
                final float body1Side = Math.max(body1.getMin().z, body1.getMax().z);
                final float body2Side = Math.min(body2.getMin().z, body2.getMax().z);
                final float distance = body2Side - body1Side;

                if(distance >= 0 && distance < movementZ)
                    return distance;

            }else{
                final float body1Side = Math.min(body1.getMin().z, body1.getMax().z);
                final float body2Side = Math.max(body2.getMin().z, body2.getMax().z);
                final float distance = body2Side - body1Side;

                if(distance <= 0 && distance > movementZ)
                    return distance;
            }
        }

        return movementZ;
    }

}