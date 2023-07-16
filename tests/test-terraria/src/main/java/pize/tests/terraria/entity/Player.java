package pize.tests.terraria.entity;

import pize.Pize;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;
import pize.physic.BoundingBox2;
import pize.physic.Collider2f;
import pize.physic.RectBody;
import pize.tests.terraria.map.MapTile;
import pize.tests.terraria.map.WorldMap;
import pize.tests.terraria.world.World;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity{

    private static final BoundingBox2 TILE_BOUNDING_RECT = new BoundingBox2(0, 0, 1, 1);

    private final List<RectBody> rectList;

    public Player(){
        super(new BoundingBox2(0, 0, 2, 3));
        rectList = new ArrayList<>();
        getVelocity().setMax(50);
    }


    public void render(TextureBatch batch){
        batch.drawQuad(1, 1, 1, 1,  pos().x, pos().y, rect().getWidth(), rect().getHeight());
        
        for(RectBody r: rectList)
            batch.drawQuad(1, 0, 0, 0.5,  r.getMin().x, r.getMin().y, r.rect().getWidth(), r.rect().getHeight());
    }

    public void update(World world){
        WorldMap tileMap = world.getTileMap();

        // Getting the nearest tiles

        RectBody[] rects = getRects(tileMap, new Vec2f(), 1);

        final boolean isCollideUp = isCollide(0, Float.MIN_VALUE, rects);
        final boolean isCollideDown = isCollide(0, -Float.MIN_VALUE, rects);
        final boolean isCollideLeft = isCollide(-Float.MIN_VALUE, 0, rects);
        final boolean isCollideRight = isCollide(Float.MIN_VALUE, 0, rects);

        // Moving

        float delta = Pize.getDt();

        if(Key.A.isPressed())
            getVelocity().x -= 0.7;
        if(Key.D.isPressed())
            getVelocity().x += 0.7;

        // Auto jump

        if(isCollideDown && !isCollideUp){
            RectBody rectBody = this.copy();
            rectBody.pos().y++;

            if(
                (getVelocity().x > 0 && isCollideRight
                && !Collider2f.getCollidedMotion(rectBody, new Vec2f(Float.MIN_VALUE, 0), rects).isZero())
            ||
                (getVelocity().x < 0 && isCollideLeft
                && !Collider2f.getCollidedMotion(rectBody, new Vec2f(-Float.MIN_VALUE, 0), rects).isZero()
            ))
                getVelocity().y = 21;
        }

        // Gravity & Jump

        getVelocity().y -= 2;

        if(Key.SPACE.isPressed() && isCollideDown)
            getVelocity().y = 50;

        // Process collisions

        Vec2f motion = getVelocity().copy().mul(delta);
        rects = getRects(tileMap, motion, 0);
        Vec2f collidedVel = Collider2f.getCollidedMotion(this, motion, rects);

        getVelocity().reduce(0.5);
        getVelocity().collidedAxesToZero(collidedVel);
        getVelocity().clampToMax();

        pos().add(collidedVel);
    }

    public boolean isCollide(float x, float y, RectBody[] rects){
        return Collider2f.getCollidedMotion(this, new Vec2f(x, y), rects).isZero();
    }

    public RectBody[] getRects(WorldMap map, Vec2f vel, float padding){
        rectList.clear();
        for(int i = Maths.floor(getMin().x + vel.x - padding); i < getMax().x + vel.x + padding; i++)
            for(int j = Maths.floor(getMin().y + vel.y - padding); j < getMax().y + vel.y + padding; j++){

                MapTile tile = map.getTile(i, j);
                if(tile != null && tile.getType().collidable){
                    RectBody body = new RectBody(TILE_BOUNDING_RECT);
                    body.pos().set(i, j);
                    rectList.add(body);
                }
            }

        return rectList.toArray(new RectBody[0]);
    }

}
