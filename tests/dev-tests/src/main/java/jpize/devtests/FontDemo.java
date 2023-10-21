package jpize.devtests;

import jpize.Jpize;
import jpize.files.Resource;
import jpize.gl.Gl;
import jpize.gl.texture.GlFilter;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.pixmap.PixmapA;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.JpizeApplication;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static jpize.math.Mathc.round;
import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memSlice;

public class FontDemo extends JpizeApplication{

    private final STBTTFontinfo info;
    private final STBTTBakedChar.Buffer charData;

    private final int ascent;
    private final int descent;
    private final int lineGap;


    protected final String text;

    private int fontHeight;

    private int scale = 1;

    private float contentScaleX = 1;
    private float contentScaleY = 1;

    private Texture texture;


    private int texWidth;
    private int texHeight;

    private boolean kerningEnabled = true;
    private boolean lineBBEnabled = false;

    TextureBatch batch = new TextureBatch();

    private float lineOffset;

    public FontDemo(){
        this.fontHeight = 64;

        this.text = "I want pizza.\naaa";

        final ByteBuffer fontFileData = Resource.readByteBuffer("OpenSans-Regular.ttf");

        info = STBTTFontinfo.create();
        if(!stbtt_InitFont(info, fontFileData)){
            throw new IllegalStateException("Failed to initialize font information.");
        }

        try(MemoryStack stack = stackPush()){
            IntBuffer pAscent = stack.mallocInt(1);
            IntBuffer pDescent = stack.mallocInt(1);
            IntBuffer pLineGap = stack.mallocInt(1);

            stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap);

            ascent = pAscent.get(0);
            descent = pDescent.get(0);
            lineGap = pLineGap.get(0);
        }

        texWidth = round(512 * contentScaleX);
        texHeight = round(512 * contentScaleY);

        charData = STBTTBakedChar.malloc(96);

        final PixmapA pixmap = new PixmapA(texWidth, texHeight);
        stbtt_BakeFontBitmap(fontFileData, fontHeight * contentScaleY, pixmap.getBuffer(), texWidth, texHeight, 32, charData);

        texture = new Texture(pixmap.toPixmapRGBA());
        texture.getParameters().setFilter(GlFilter.LINEAR);
        texture.update();

        //glBindTexture(GL_TEXTURE_2D, texture.getID());
        //glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, texWidth, texHeight, 0, GL_ALPHA, GL_UNSIGNED_BYTE, pixmap.getBuffer());
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        //glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color
    }

    @Override
    public void update(){
        lineOffset += Jpize.mouse().getScroll();
    }

    @Override
    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.6, 0.6, 0.6);
        batch.begin();

        // Zoom
        float scaleFactor = 1.0f + scale * 0.25f;
        batch.scale(scaleFactor * 5);
        //// Scroll
        //batch.translate(4.0f, fontHeight * 0.5f + 4.0f - lineOffset * fontHeight);
        // Color
        batch.setColor(0.6, 0.7, 0.8);
        // Render
        renderText(charData);

        batch.draw(texture, 0, 0, 200, 200);

        batch.end();
    }

    private void renderText(STBTTBakedChar.Buffer cdata){

        float scale = stbtt_ScaleForPixelHeight(info, fontHeight);

        try(MemoryStack stack = stackPush()){
            IntBuffer pCodePoint = stack.mallocInt(1);

            FloatBuffer x = stack.floats(0.0f);
            FloatBuffer y = stack.floats(0.0f);

            STBTTAlignedQuad q = STBTTAlignedQuad.malloc(stack);

            int lineStart = 0;

            float factorX = 1.0f / contentScaleX;
            float factorY = 1.0f / contentScaleY;

            float lineY = 0.0f;

            for(int i = 0, to = text.length(); i < to; ){
                i += getCP(text, to, i, pCodePoint);

                int cp = pCodePoint.get(0);
                if(cp == '\n'){
                    if(lineBBEnabled)
                        renderLineBB(lineStart, i - 1, y.get(0), scale);

                    y.put(0, lineY = y.get(0) + (ascent - descent + lineGap) * scale);
                    x.put(0, 0.0f);

                    lineStart = i;
                    continue;
                }else if(cp < 32 || 128 <= cp){
                    continue;
                }

                float cpX = x.get(0);
                stbtt_GetBakedQuad(cdata, texWidth, texHeight, cp - 32, x, y, q, true);
                x.put(0, scale(cpX, x.get(0), factorX));
                if(kerningEnabled && i < to){
                    getCP(text, to, i, pCodePoint);
                    x.put(0, x.get(0) + stbtt_GetCodepointKernAdvance(info, cp, pCodePoint.get(0)) * scale);
                }

                float x0 = scale(cpX, q.x0(), factorX);
                float y0 = scale(lineY, q.y0(), factorY);

                float x1 = scale(cpX, q.x1(), factorX);
                float y1 = scale(lineY, q.y1(), factorY);

                final Region region = new Region(q.s0(), q.t0(), q.s1(), q.t1());
                batch.draw(texture, x0, y0, x1 - x0, y1 - y0, region);

                // glTexCoord2f(q.s0(), q.t0());
                // glVertex2f(x0, y0);

                // glTexCoord2f(q.s1(), q.t0());
                // glVertex2f(x1, y0);

                // glTexCoord2f(q.s1(), q.t1());
                // glVertex2f(x1, y1);

                // glTexCoord2f(q.s0(), q.t1());
                // glVertex2f(x0, y1);
            }
            if(lineBBEnabled){
                renderLineBB(lineStart, text.length(), lineY, scale);
            }
        }
    }

    private static float scale(float center, float offset, float factor){
        return (offset - center) * factor + center;
    }

    @Override
    public void dispose(){
        charData.free();
    }

    private void renderLineBB(int from, int to, float y, float scale){
        glDisable(GL_TEXTURE_2D);
        glPolygonMode(GL_FRONT, GL_LINE);
        glColor3f(1.0f, 1.0f, 0.0f);

        float width = getStringWidth(info, text, from, to, fontHeight);
        y -= descent * scale;

        glBegin(GL_QUADS);
        glVertex2f(0.0f, y);
        glVertex2f(width, y);
        glVertex2f(width, y - fontHeight);
        glVertex2f(0.0f, y - fontHeight);
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glPolygonMode(GL_FRONT, GL_FILL);
        glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color
    }

    private float getStringWidth(STBTTFontinfo info, String text, int from, int to, int fontHeight){
        int width = 0;

        try(MemoryStack stack = stackPush()){
            IntBuffer pCodePoint = stack.mallocInt(1);
            IntBuffer pAdvancedWidth = stack.mallocInt(1);
            IntBuffer pLeftSideBearing = stack.mallocInt(1);

            int i = from;
            while(i < to){
                i += getCP(text, to, i, pCodePoint);
                int cp = pCodePoint.get(0);

                stbtt_GetCodepointHMetrics(info, cp, pAdvancedWidth, pLeftSideBearing);
                width += pAdvancedWidth.get(0);

                if(kerningEnabled && i < to){
                    getCP(text, to, i, pCodePoint);
                    width += stbtt_GetCodepointKernAdvance(info, cp, pCodePoint.get(0));
                }
            }
        }

        return width * stbtt_ScaleForPixelHeight(info, fontHeight);
    }

    private static int getCP(String text, int to, int i, IntBuffer cpOut){
        char c1 = text.charAt(i);
        if(Character.isHighSurrogate(c1) && i + 1 < to){
            char c2 = text.charAt(i + 1);
            if(Character.isLowSurrogate(c2)){
                cpOut.put(0, Character.toCodePoint(c1, c2));
                return 2;
            }
        }
        cpOut.put(0, c1);
        return 1;
    }


    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity){
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException{
        ByteBuffer buffer;

        try(InputStream source = FontDemo.class.getClassLoader().getResourceAsStream(resource); ReadableByteChannel rbc = Channels.newChannel(source)){
            buffer = createByteBuffer(bufferSize);

            while(true){
                int bytes = rbc.read(buffer);
                if(bytes == -1){
                    break;
                }
                if(buffer.remaining() == 0){
                    buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                }
            }
        }

        buffer.flip();
        return memSlice(buffer);
    }

}