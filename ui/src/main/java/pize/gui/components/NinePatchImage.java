package pize.gui.components;

import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.UIComponent;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;

import static pize.gui.components.ExpandType.*;

public class NinePatchImage extends UIComponent<TextureBatch>{

    private final TextureRegion[] regions;
    private TextureRegion texture;
    private RegionMesh mesh;
    private float pixelSize;
    private ExpandType type;


    public NinePatchImage(TextureRegion textureRegion, RegionMesh mesh){
        regions = new TextureRegion[9];
        setTexture(textureRegion, mesh);
        setExpandType(DEFAULT);
    }

    public NinePatchImage(Texture texture, RegionMesh mesh){
        this(new TextureRegion(texture), mesh);
    }


    public void setTexture(TextureRegion texture, RegionMesh mesh){
        if(this.texture == texture)
            return;

        this.texture = texture;
        this.mesh = mesh;

        for(int i = 0; i < 9; i++){
            regions[i] = new TextureRegion(texture,
                mesh.getMesh()[i % 3] / texture.getWidthPx(),
                mesh.getMesh()[4 + i / 3] / texture.getHeightPx(),
                mesh.getMesh()[i % 3 + 1] / texture.getWidthPx(),
                mesh.getMesh()[4 + i / 3 + 1] / texture.getHeightPx()
            );
        }
    }

    public void setTexture(TextureRegion texture){
        setTexture(texture, mesh);
    }

    public void setTexture(Texture texture, RegionMesh mesh){
        setTexture(new TextureRegion(texture), mesh);
    }

    public void setTexture(Texture texture){
        setTexture(texture, mesh);
    }


    public float getPixelSize(){
        return pixelSize;
    }


    public void setExpandType(ExpandType type){
        this.type = type;
    }

    public ExpandType getExpandType(){
        return type;
    }


    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        // Calc Render Info

        if(type == HORIZONTAL)
            pixelSize = height / texture.getHeightPx();
        else if(type == VERTICAL)
            pixelSize = width / texture.getWidthPx();
        else
            if(width > height * texture.aspect())
                pixelSize = height / texture.getHeightPx();
            else
                pixelSize = width / texture.getWidthPx();

        final Vec2f cornerLeftBottomSize = getElementSize(0, 2);
        final Vec2f cornerRightUpSize = getElementSize(2, 0);
        final Vec2f centerElementSize = getElementSize(1, 1);
        final float elementCountXF = (width - cornerLeftBottomSize.x - cornerRightUpSize.x) / centerElementSize.x;
        final float elementCountYF = (height - cornerLeftBottomSize.y - cornerRightUpSize.y) / centerElementSize.y;

        final int elementCountX = type == VERTICAL ? 1 : Math.max(type == HORIZONTAL ? 0 : 1, Maths.floor(elementCountXF));
        final int elementCountY = type == HORIZONTAL ? 1 : Math.max(type == VERTICAL ? 0 : 1, Maths.floor(elementCountYF));

        final float countDifferenceX = elementCountXF - elementCountX;
        final float countDifferenceY = elementCountYF - elementCountY;

        // Draw corners

        renderRegion(batch, 0, 2,
            x,
            y,
            1, 1
        );
        renderRegion(
            batch, 2, 2,
            x + width - cornerRightUpSize.x,
            y,
            1, 1
        );
        renderRegion(
            batch, 2, 0,
            x + width - cornerRightUpSize.x,
            y + height - cornerRightUpSize.y,
            1, 1
        );
        renderRegion(
            batch, 0, 0,
            x,
            y + height - cornerRightUpSize.y,
            1, 1
        );

        // Draw Borders X

        float borderDrawOffsetX = 0;
        for(int i = 0; i < elementCountX; i++){
            final Vec2f elementSize = renderRegion(
                batch, 1, 2,
                x + borderDrawOffsetX + cornerLeftBottomSize.x,
                y,
                1, 1
            );

            renderRegion(
                batch, 1, 0,
                x + borderDrawOffsetX + cornerLeftBottomSize.x,
                y + height - cornerRightUpSize.y,
                1, 1
            );

            borderDrawOffsetX += elementSize.x;
        }

        if(countDifferenceX > 0){
            renderRegion(
                batch, 1, 2,
                x + borderDrawOffsetX + cornerLeftBottomSize.x,
                y,
                countDifferenceX, 1
            );

            renderRegion(
                batch, 1, 0,
                x + borderDrawOffsetX + cornerLeftBottomSize.x,
                y + height - cornerRightUpSize.y,
                countDifferenceX, 1
            );

        }

        // Draw Borders Y

        float borderDrawOffsetY = 0;
        for(int j = 0; j < elementCountY; j++){
            final Vec2f elementSize = renderRegion(
                batch, 0, 1,
                x,
                y + borderDrawOffsetY + cornerLeftBottomSize.y,
                1, 1
            );

            renderRegion(
                batch, 2, 1,
                x + width - cornerRightUpSize.x,
                y + borderDrawOffsetY + cornerLeftBottomSize.y,
                1, 1
            );

            borderDrawOffsetY += elementSize.y;
        }

        if(countDifferenceY > 0){
            renderRegion(
                batch, 0, 1,
                x,
                y + borderDrawOffsetY + cornerLeftBottomSize.y,
                1, countDifferenceY
            );

            renderRegion(
                batch, 2, 1,
                x + width - cornerRightUpSize.x,
                y + borderDrawOffsetY + cornerLeftBottomSize.y,
                1, countDifferenceY
            );

        }

        // Draw center

        float centerDrawOffsetX = 0;
        float centerDrawOffsetY = 0;
        for(int j = 0; j < elementCountY; j++){
            centerDrawOffsetX = 0;

            Vec2f elementSize = new Vec2f();
            for(int i = 0; i < elementCountX; i++){
                elementSize = renderRegion(
                    batch, 1, 1,
                    x + centerDrawOffsetX + cornerLeftBottomSize.x,
                    y + centerDrawOffsetY + cornerLeftBottomSize.y,
                    1, 1
                );
                centerDrawOffsetX += elementSize.x;
            }

            centerDrawOffsetY += elementSize.y;
        }

        if(countDifferenceX > 0)
             renderRegion(
                batch, 1, 1,
                x + centerDrawOffsetX + cornerLeftBottomSize.x,
                y + cornerLeftBottomSize.y,
                countDifferenceX, 1
            );

        if(countDifferenceY > 0)
            renderRegion(
                batch, 1, 1,
                x + cornerLeftBottomSize.x,
                y + centerDrawOffsetY + cornerLeftBottomSize.y,
                1, countDifferenceY
            );
    }

    // PRIVATE

    private Vec2f renderRegion(TextureBatch batch, int i, int j, float x, float y, float u, float v){
        final Vec2f size = getElementSize(i, j);
        batch.draw(getRegion(i, j), x, y, size.x * u, size.y * v, new Region(0, 1 - v, u, 1));

        return size;
    }

    private Vec2f getElementSize(int i, int j){
        final TextureRegion region = getRegion(i, j);

        return new Vec2f(
            region.getWidthPx() * pixelSize,
            region.getHeightPx() * pixelSize
        );
    }

    private TextureRegion getRegion(int i, int j){
        return regions[j * 3 + i];
    }

}