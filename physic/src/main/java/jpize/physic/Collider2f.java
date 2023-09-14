package jpize.physic;

import jpize.math.vecmath.vector.Vec2f;

public class Collider2f{

    public static Vec2f getCollidedMovement(Vec2f movement, RectBody body1, RectBody... otherBodies){
        // If movement == 0, return 0
        if(movement.isZero())
            return movement;

        // Copy body for safe addition to body.position.X for correct calculation movementY
        body1 = body1.copy();

        // Calculate
        final float movementX = minMovementX(movement.x, body1, otherBodies);
        body1.getPosition().x += movementX;
        final float movementY = minMovementY(movement.y, body1, otherBodies);

        // Return calculated movement
        return new Vec2f(movementX, movementY);
    }


    private static float minMovementX(float movementX, RectBody body1, RectBody[] otherBodies){
        // Iterate other bodies
        for(RectBody body2: otherBodies){
            if(movementX == 0)
                break;

            // Minimize movement
            movementX = distX(movementX, body1, body2);
        }
        return movementX;
    }

    private static float minMovementY(float movementY, RectBody body1, RectBody[] otherBodies){
        for(RectBody body2: otherBodies){
            if(movementY == 0)
                break;

            movementY = distY(movementY, body1, body2);
        }
        return movementY;
    }


    private static float distX(float movementX, RectBody body1, RectBody body2){
        // Ensure that the bodies intersect on the other axis and that collision is possible
        if(body1.getMax().y > body2.getMin().y &&
           body1.getMin().y < body2.getMax().y){

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
                if(distance <= 0 && movementX < distance)
                    // Return the distance as a move
                    return distance;
            }
        }

        // If the movementX is less than the collision distance - do nothing
        return movementX;
    }
    
    private static float distY(float movementY, RectBody body1, RectBody body2){
        if(body1.getMax().x > body2.getMin().x &&
           body1.getMin().x < body2.getMax().x){

            if(movementY > 0){
                final float body1Side = Math.max(body1.getMin().y, body1.getMax().y);
                final float body2Side = Math.min(body2.getMin().y, body2.getMax().y);
                final float dist = body2Side - body1Side;

                if(dist >= 0 && movementY > dist)
                    return dist;

            }else{
                final float body1Side = Math.min(body1.getMin().y, body1.getMax().y);
                final float body2Side = Math.max(body2.getMin().y, body2.getMax().y);
                final float dist = body2Side - body1Side;

                if(dist <= 0 && movementY < dist)
                    return dist;
            }
        }

        return movementY;
    }

}
