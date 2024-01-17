package jpize.graphics.font;

import jpize.Jpize;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertAttr;
import jpize.graphics.font.glyph.GlyphIterator;
import jpize.graphics.font.glyph.GlyphMap;
import jpize.graphics.font.glyph.GlyphPages;
import jpize.graphics.font.glyph.GlyphSprite;
import jpize.graphics.mesh.Mesh;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.util.color.Color;
import jpize.util.math.vecmath.matrix.Matrix3f;
import jpize.util.math.vecmath.matrix.Matrix4f;
import jpize.util.math.vecmath.vector.Vec2f;
import jpize.app.Disposable;
import jpize.util.array.list.FloatList;

public class BitmapFont implements Disposable{

    private final FontInfo info;
    private final GlyphPages pages;
    private final GlyphMap glyphs;
    private final FontOptions options;

    protected BitmapFont(FontInfo info, GlyphPages pages, GlyphMap glyphs){
        this.info = info;
        this.pages = pages;
        this.glyphs = glyphs;
        this.options = new FontOptions(this);
    }

    public FontInfo info(){
        return info;
    }

    public GlyphMap glyphs(){
        return glyphs;
    }

    public GlyphPages pages(){
        return pages;
    }

    public FontOptions options(){
        return options;
    }


    public float getLineHeight(){
        return info.getHeight();
    }

    public float getDescentScaled(){
        return info.getDescent() * options.scale;
    }


    public float getScale(){
        return options.scale;
    }

    public void setScale(float scale){
        options.scale = scale;
    }


    public Vec2f getBounds(String text){
        float width = 0;
        float height = 0;

        for(GlyphSprite glyph: iterableText(text)){
            final float glyphX = glyph.getX() + ((char) glyph.getCode() == ' ' ? glyph.getAdvanceX() : glyph.getWidth());
            final float glyphY = Math.abs(glyph.getY() + info.getDescent() + glyph.getHeight()) - info.getDescent();

            width = Math.max(width, glyphX);
            height = Math.max(height, glyphY);
        }

        return new Vec2f(width, height);
    }

    public float getTextWidth(String text){
        float width = 0;
        for(GlyphSprite glyph: iterableText(text)){
            final float glyphX = glyph.getX() + ((char) glyph.getCode() == ' ' ? glyph.getAdvanceX() : glyph.getWidth());
            width = Math.max(width, glyphX);
        }
        return width;
    }

    public float getTextHeight(String text){
        float height = 0;
        for(GlyphSprite glyph: iterableText(text)){
            final float glyphY = Math.abs(glyph.getY() + info.getDescent() + glyph.getHeight()) - info.getDescent();
            height = Math.max(height, glyphY);
        }
        return height;
    }


    public void drawText(TextureBatch batch, String text, float x, float y){
        if(text == null || text.isEmpty() || text.isBlank())
            return;

        final Color color = options.color;

        batch.setTransformOrigin(0, 0);
        batch.rotate(options.rotation);
        batch.shear(options.getItalicAngle(), 0);

        final Vec2f centerPos = getBounds(text).mul(options.rotateOrigin);
        centerPos.y *= options.getLineWrapSign();

        final float descent = getDescentScaled();

        for(GlyphSprite sprite: iterableText(text)){
            if((char) sprite.getCode() == ' ' || !sprite.isCanRender())
                continue;

            final Vec2f renderPos = new Vec2f(sprite.getX(), sprite.getY());
            renderPos.y -= descent;
            renderPos.sub(centerPos).rotd(options.rotation).add(centerPos).add(x, y);
            renderPos.y += descent;
            sprite.render(batch, renderPos.x, renderPos.y, color.r(), color.g(), color.b(), color.a());
        }
    }

    public void drawText(String text, float x, float y){
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

        final Color color = options.color;

        final Matrix3f mat = new Matrix3f();
        mat.setRotation(options.rotation);
        mat.shear(options.getItalicAngle(), 0);

        final Vec2f centerPos = getBounds(text).mul(options.rotateOrigin);
        centerPos.y *= options.getLineWrapSign();

        final float descent = getDescentScaled();

        final FloatList vertices = new FloatList(text.length() * 4);
        Texture lastTexture = null;

        for(GlyphSprite sprite: iterableText(text)){
            if((char) sprite.getCode() == ' ' || !sprite.isCanRender())
                continue;

            final Vec2f renderPos = new Vec2f(sprite.getX(), sprite.getY());
            renderPos.y -= descent;
            renderPos.sub(centerPos).rotd(options.rotation).add(centerPos).add(x, y);
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

    private static Mesh tmpMesh;
    private static BaseShader tmpShader;
    private static Matrix4f tmpMatrix1, tmpMatrix2;


    public Iterable<GlyphSprite> iterableText(String text){
        return () -> new GlyphIterator(glyphs, options, text, 1, 1);
    }


    @Override
    public void dispose(){
        pages.dispose();
    }

}