package pize.tests.terraria.entity;

import pize.Pize;
import pize.graphics.util.batch.Batch;
import pize.graphics.util.TextureUtils;
import pize.io.glfw.Key;
import pize.math.Maths;
import pize.math.vecmath.tuple.Tuple2d;
import pize.math.vecmath.vector.Vec2d;
import pize.physic.BoundingRect;
import pize.physic.Collider2D;
import pize.physic.RectBody;
import pize.tests.terraria.world.World;
import pize.tests.terraria.map.MapTile;
import pize.tests.terraria.map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity{

    private static final BoundingRect TILE_BOUNDING_RECT = new BoundingRect(0, 0, 1, 1);

    private final List<RectBody> rectList;

    public Player(){
        super(new BoundingRect(0, 0, 2, 3));
        rectList = new ArrayList<>();
        getMotion().setMax(50);
    }


    public void render(Batch batch){
        batch.draw(TextureUtils.quadTexture(), pos().x, pos().y, rect().getWidth(), rect().getHeight());
        batch.setColor(1, 0, 0, 0.5F);

        for(RectBody r: rectList)
            batch.draw(TextureUtils.quadTexture(), r.getMin().x, r.getMin().y, r.rect().getWidth(), r.rect().getHeight());

        batch.resetColor();
    }

    public void update(World world){
        WorldMap tileMap = world.getTileMap();

        // Getting the nearest tiles

        RectBody[] rects = getRects(tileMap, new Tuple2d(), 1);

        boolean isCollideUp = isCollide(0, Float.MIN_VALUE, rects);
        boolean isCollideDown = isCollide(0, -Float.MIN_VALUE, rects);
        boolean isCollideLeft = isCollide(-Float.MIN_VALUE, 0, rects);
        boolean isCollideRight = isCollide(Float.MIN_VALUE, 0, rects);

        // Moving

        float delta = Pize.getDt();

        if(Key.A.isPressed())
            getMotion().x -= 0.7;
        if(Key.D.isPressed())
            getMotion().x += 0.7;

        // Auto jump

        if(isCollideDown && !isCollideUp){
            RectBody rectBody = this.clone();
            rectBody.pos().y++;

            if(
                (getMotion().x > 0 && isCollideRight
                && !Collider2D.getCollidedMotion(rectBody, new Vec2d(Float.MIN_VALUE, 0), rects).isZero())
            ||
                (getMotion().x < 0 && isCollideLeft
                && !Collider2D.getCollidedMotion(rectBody, new Vec2d(-Float.MIN_VALUE, 0), rects).isZero()
            ))
                getMotion().y = 21;
        }

        // Gravity & Jump

        getMotion().y -= 2;

        if(Key.SPACE.isPressed() && isCollideDown)
            getMotion().y = 50;

        // Process collisions

        Vec2d motion = getMotion().clone().mul(delta);
        rects = getRects(tileMap, motion, 0);
        Vec2d collidedVel = Collider2D.getCollidedMotion(this, motion, rects);

        getMotion().reduce(0.5);
        getMotion().collidedAxesToZero(collidedVel);
        getMotion().clampToMax();

        pos().add(collidedVel);
    }

    public boolean isCollide(float x, float y, RectBody[] rects){
        return Collider2D.getCollidedMotion(this, new Vec2d(x, y), rects).isZero();
    }

    public RectBody[] getRects(WorldMap map, Tuple2d vel, float padding){
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
