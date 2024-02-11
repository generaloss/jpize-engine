package jpize.graphics.font;

import jpize.Jpize;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertAttr;
import jpize.graphics.font.glyph.GlyphSprite;
import jpize.graphics.mesh.Mesh;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.util.array.FloatList;
import jpize.util.color.Color;
import jpize.util.math.matrix.Matrix3f;
import jpize.util.math.matrix.Matrix4f;
import jpize.util.math.vector.Vec2f;

public class TextRenderer{

    public static void render(Font font, TextureBatch batch, String text, float x, float y){
        if(text == null || text.isEmpty() || text.isBlank())
            return;

        final Color color = font.options.color;

        batch.setTransformOrigin(0, 0);
        batch.rotate(font.options.rotation);
        batch.shear(font.options.getItalicAngle(), 0);

        final Vec2f centerPos = font.getBounds(text).mul(font.options.rotateOrigin);
        centerPos.y *= font.options.getLineWrapSign();

        final float descent = font.options.getDescentScaled();

        for(GlyphSprite sprite: font.iterable(text)){
            if((char) sprite.getCode() == ' ' || !sprite.isCanRender())
                continue;

            final Vec2f renderPos = new Vec2f(sprite.getX(), sprite.getY());
            renderPos.y -= descent;
            renderPos.sub(centerPos).rotd(font.options.rotation).add(centerPos).add(x, y);
            renderPos.y += descent;
            sprite.render(batch, renderPos.x, renderPos.y, color.r(), color.g(), color.b(), color.a());
        }
    }


    private static Mesh tmpMesh;
    private static BaseShader tmpShader;
    private static Matrix4f tmpMatrix1, tmpMatrix2;

    public static void render(Font font, String text, float x, float y){
        if(text == null || text.isEmpty() || text.isBlank())
            return;

        if(tmpMesh == null){
            tmpMesh = new Mesh(new GlVertAttr(2, GlType.FLOAT), new GlVertAttr(2, GlType.FLOAT), new GlVertAttr(4, GlType.FLOAT));
            tmpMesh.setMode(GlPrimitive.QUADS);
            tmpShader = BaseShader.getPos2UvColor();
            tmpMatrix1 = new Matrix4f();
            tmpMatrix2 = new Matrix4f();
        }
        tmpMatrix1.setOrthographic(0, 0, Jpize.getWidth(), Jpize.getHeight());

        final Color color = font.options.color;

        final Matrix3f mat = new Matrix3f();
        mat.setRotation(font.options.rotation);
        mat.shear(font.options.getItalicAngle(), 0);

        final Vec2f centerPos = font.getBounds(text).mul(font.options.rotateOrigin);
        centerPos.y *= font.options.getLineWrapSign();

        final float descent = font.options.getDescentScaled();

        final FloatList vertices = new FloatList(text.length() * 4);
        Texture lastTexture = null;

        for(GlyphSprite sprite: font.iterable(text)){
            if((char) sprite.getCode() == ' ' || !sprite.isCanRender())
                continue;

            final Vec2f renderPos = new Vec2f(sprite.getX(), sprite.getY());
            renderPos.y -= descent;
            renderPos.sub(centerPos).rotd(font.options.rotation).add(centerPos).add(x, y);
            renderPos.y += descent;
            renderPos.mulMat3(mat);

            final Texture page = sprite.getPage();
            final float width = sprite.getWidth();
            final float height = sprite.getHeight();
            final Region region = sprite.getRegion();

            final float renderX = renderPos.x;
            final float renderY = renderPos.y;

            final float u1 = region.u1();
            final float u2 = region.u2();
            final float v1 = region.v1();
            final float v2 = region.v2();

            final float r = color.r();
            final float g = color.g();
            final float b = color.b();
            final float a = color.a();

            final Vec2f vertex1 = new Vec2f(0,     height).mulMat3(mat).add(renderX, renderY);
            final Vec2f vertex2 = new Vec2f(0,     0     ).mulMat3(mat).add(renderX, renderY);
            final Vec2f vertex3 = new Vec2f(width, 0     ).mulMat3(mat).add(renderX, renderY);
            final Vec2f vertex4 = new Vec2f(width, height).mulMat3(mat).add(renderX, renderY);

            vertices.add(new float[]{
                vertex1.x, vertex1.y,  u1, v1,  r, g, b, a,
                vertex2.x, vertex2.y,  u1, v2,  r, g, b, a,
                vertex3.x, vertex3.y,  u2, v2,  r, g, b, a,
                vertex4.x, vertex4.y,  u2, v1,  r, g, b, a,
            });

            if(lastTexture == null)
                lastTexture = page;
            if(lastTexture != page){
                lastTexture = page;

                tmpMesh.getBuffer().setData(vertices.slice(vertices.size()));
                vertices.clear();

                tmpShader.bind();
                tmpShader.setMatrices(tmpMatrix1, tmpMatrix2);
                tmpShader.setTexture(lastTexture);
                tmpMesh.render();
            }
        }

        if(lastTexture == null || vertices.isEmpty())
            return;

        tmpMesh.getBuffer().setData(vertices.slice(vertices.size()));
        tmpShader.bind();
        tmpShader.setMatrices(tmpMatrix1, tmpMatrix2);
        tmpShader.setTexture(lastTexture);
        tmpMesh.render();
    }

}
