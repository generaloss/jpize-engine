package pize.graphics.font;

import pize.files.Resource;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.util.io.FastReader;
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
import java.nio.file.Path;

import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class FontLoader{

    private static BitmapFont defaultFont;


    public static BitmapFont loadFnt(String filepath){
        BitmapFont font = new BitmapFont();

        FastReader reader = new Resource(filepath).getReader();

        while(reader.hasNext()){
            String[] tokens = reader.nextLine().trim().split("\\s+");

            switch(tokens[0].toLowerCase()){
                case "info" -> font.setItalic(Integer.parseInt(getValue(tokens[4])) == 1);
                case "common" -> font.setLineHeight(Integer.parseInt(getValue(tokens[1])));
                case "page" -> {
                    int id = Integer.parseInt(getValue(tokens[1]));

                    String relativeTexturePath = getValue(tokens[2]).replace("\"", "");

                    font.addPage(id, new Texture(new Resource(
                        Path.of(Path.of(filepath).getParent().toString() + "/" + relativeTexturePath).normalize().toString()
                    )));
                }
                case "char" -> {
                    int id = Integer.parseInt(getValue(tokens[1]));

                    int page = Integer.parseInt(getValue(tokens[9]));
                    Texture pageTexture = font.getPage(page);

                    float s0 = (float) Integer.parseInt(getValue(tokens[2])) / pageTexture.getWidth();
                    float t0 = (float) Integer.parseInt(getValue(tokens[3])) / pageTexture.getHeight();
                    float s1 = (float) Integer.parseInt(getValue(tokens[4])) / pageTexture.getWidth() + s0;
                    float t1 = (float) Integer.parseInt(getValue(tokens[5])) / pageTexture.getHeight() + t0;

                    int offsetX = Integer.parseInt(getValue(tokens[6]));
                    int offsetY = Integer.parseInt(getValue(tokens[7]));
                    int advanceX = Integer.parseInt(getValue(tokens[8]));

                    Region regionOnTexture = new Region(s0, t0, s1, t1);
                    float glyphHeight = regionOnTexture.getHeightOn(font.getPage(page));
                    float glyphWidth = regionOnTexture.getWidthOn(font.getPage(page));

                    font.addGlyph(new Glyph(
                        font,
                        id,

                        offsetX,
                        font.getLineHeight() - offsetY - glyphHeight,
                        glyphWidth,
                        glyphHeight,

                        regionOnTexture,
                        advanceX,
                        page
                    ));
                }
            }
        }

        return font;
    }


    private static String getValue(String token){
        return token.split("=")[1];
    }


    public static BitmapFont loadTrueType(String filepath, int size, FontCharset charset){
        final BitmapFont font = new BitmapFont();
        font.setLineHeight(size);

        int width = size * charset.size();
        int height = size * 3;
        
        final ByteBuffer data;
        try(final InputStream inStream = new Resource(filepath).inStream()){
            byte[] bytes = inStream.readAllBytes();
            data = BufferUtils.createByteBuffer(bytes.length);
            data.put(bytes);
            data.flip();
        }catch(IOException e){
            throw new Error("Failed to load " + filepath + " (" + e + ")");
        }

        final ByteBuffer bitmap = BufferUtils.createByteBuffer(width * height);
        STBTTBakedChar.Buffer charData = STBTTBakedChar.malloc(charset.getLastChar() + 1);
        stbtt_BakeFontBitmap(data, size, bitmap, width, height, charset.getFirstChar(), charData);

        final ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for(int i = 0; i < buffer.capacity(); i += 4){
            buffer.put((byte) 255);
            buffer.put((byte) 255);
            buffer.put((byte) 255);
            buffer.put(bitmap.get());
        }
        buffer.flip();

        final Texture texture = new Texture(new Pixmap(buffer, width, height));
        font.addPage(0, texture);

        // STB
        try(final MemoryStack stack = stackPush()){
            // Creating font
            final STBTTFontinfo fontInfo = STBTTFontinfo.create();
            stbtt_InitFont(fontInfo, data);

            // Getting descent
            final IntBuffer descentBuffer = stack.mallocInt(1);
            stbtt_GetFontVMetrics(fontInfo, null, descentBuffer, null);
            float descent = descentBuffer.get() * stbtt_ScaleForPixelHeight(fontInfo, size);
            
            final STBTTAlignedQuad quad = STBTTAlignedQuad.malloc(stack);

            for(int i = 0; i < charset.size(); i++){
                int id = charset.charAt(i);

                // Getting advanceX
                final FloatBuffer advanceXBuffer = stack.floats(0);
                final FloatBuffer advanceYBuffer = stack.floats(0);
                stbtt_GetBakedQuad(charData, width, height, id - charset.getFirstChar(), advanceXBuffer, advanceYBuffer, quad, false);
                float advanceX = advanceXBuffer.get();

                // Calculating glyph Region on the texture & glyph Width and Height
                final Region regionOnTexture = new Region(quad.s0(), quad.t0(), quad.s1(), quad.t1());
                float glyphHeight = quad.y1() - quad.y0();
                float glyphWidth = quad.x1() - quad.x0();

                // Adding Glyph to the font
                font.addGlyph(new Glyph(
                    font,
                    id,

                    quad.x0(),
                    - quad.y0() - glyphHeight - descent,
                    glyphWidth,
                    glyphHeight,

                    regionOnTexture,
                    advanceX,
                    0
                ));
            }
        }

        return font;
    }

    public static BitmapFont loadTrueType(String filePath, int size){
        return loadTrueType(filePath, size, FontCharset.DEFAULT);
    }


    public static BitmapFont getDefault(){
        if(defaultFont == null)
            defaultFont = loadTrueType("font/notosans-bold.ttf", 32);
        return defaultFont;
    }

}
