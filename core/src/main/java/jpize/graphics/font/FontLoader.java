package jpize.graphics.font;

import jpize.files.Resource;
import jpize.graphics.font.glyph.Glyph;
import jpize.graphics.font.glyph.GlyphMap;
import jpize.graphics.font.glyph.GlyphPages;
import jpize.graphics.texture.Pixmap;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.util.io.FastReader;
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

public class FontLoader{

    private static BitmapFont defaultFont;

    public static BitmapFont getDefault(){
        if(defaultFont == null)
            defaultFont = loadTrueType("font/OpenSans-Regular.ttf", 64, FontCharset.DEFAULT_ENG_RUS);

        return defaultFont;
    }


    public static BitmapFont loadFnt(String filepath){
        final BitmapFont font = new BitmapFont();
        final GlyphMap glyphs = font.getGlyphs();
        final GlyphPages pages = font.getPages();

        final FastReader reader = new Resource(filepath).getReader();

        while(reader.hasNext()){
            final String[] tokens = reader.nextLine().trim().split("\\s+");

            switch(tokens[0].toLowerCase()){
                case "info" -> font.setItalic(Integer.parseInt(getValue(tokens[4])) == 1);
                case "common" -> font.setLineHeight(Integer.parseInt(getValue(tokens[1])));
                case "page" -> {
                    final int id = Integer.parseInt(getValue(tokens[1]));

                    final String relativeTexturePath = getValue(tokens[2]).replace("\"", "");

                    final Path path = Path.of(filepath);
                    pages.add(id, new Texture(new Resource(
                        path.getParent() == null ?
                            relativeTexturePath :
                            Path.of(path.getParent() + "/" + relativeTexturePath).normalize().toString()
                    )));
                }
                case "char" -> {
                    final int code = Integer.parseInt(getValue(tokens[1]));

                    final int page = Integer.parseInt(getValue(tokens[9]));
                    final Texture pageTexture = pages.get(page);

                    final float s0 = (float) Integer.parseInt(getValue(tokens[2])) / pageTexture.getWidth();
                    final float t0 = (float) Integer.parseInt(getValue(tokens[3])) / pageTexture.getHeight();
                    final float s1 = (float) Integer.parseInt(getValue(tokens[4])) / pageTexture.getWidth() + s0;
                    final float t1 = (float) Integer.parseInt(getValue(tokens[5])) / pageTexture.getHeight() + t0;

                    final int offsetX = Integer.parseInt(getValue(tokens[6]));
                    final int offsetY = Integer.parseInt(getValue(tokens[7]));
                    final int advanceX = Integer.parseInt(getValue(tokens[8]));

                    final Region regionOnTexture = new Region(s0, t0, s1, t1);
                    final float glyphHeight = regionOnTexture.getHeightPx(pages.get(page));
                    final float glyphWidth = regionOnTexture.getWidthPx(pages.get(page));

                    glyphs.add(new Glyph(
                        code,

                        offsetX,
                        font.getLineHeight() - offsetY - glyphHeight,
                        glyphWidth,
                        glyphHeight,

                        regionOnTexture,
                        advanceX,
                        page,
                        pages
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
        final GlyphMap glyphs = font.getGlyphs();
        final GlyphPages pages = font.getPages();
        font.setLineHeight(size);

        final int width = size * charset.size();
        final int height = size * 3;
        
        final ByteBuffer data;
        try(final InputStream inStream = new Resource(filepath).inStream()){
            final byte[] bytes = inStream.readAllBytes();
            data = BufferUtils.createByteBuffer(bytes.length);
            data.put(bytes);
            data.flip();
        }catch(IOException e){
            throw new Error("Failed to load " + filepath + " (" + e + ")");
        }

        final ByteBuffer bitmap = BufferUtils.createByteBuffer(width * height);
        final STBTTBakedChar.Buffer charData = STBTTBakedChar.malloc(charset.getLastChar() + 1);
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
        pages.add(0, texture);

        // STB
        try(final MemoryStack stack = MemoryStack.stackPush()){
            // Creating font
            final STBTTFontinfo fontInfo = STBTTFontinfo.create();
            stbtt_InitFont(fontInfo, data);

            // Getting descent
            final IntBuffer descentBuffer = stack.mallocInt(1);
            stbtt_GetFontVMetrics(fontInfo, null, descentBuffer, null);
            float descent = descentBuffer.get() * stbtt_ScaleForPixelHeight(fontInfo, size);
            
            final STBTTAlignedQuad quad = STBTTAlignedQuad.malloc(stack);

            for(int i = 0; i < charset.size(); i++){
                final int code = charset.charAt(i);

                // Getting advanceX
                final FloatBuffer advanceXBuffer = stack.floats(0);
                final FloatBuffer advanceYBuffer = stack.floats(0);
                stbtt_GetBakedQuad(charData, width, height, code - charset.getFirstChar(), advanceXBuffer, advanceYBuffer, quad, false);
                final float advanceX = advanceXBuffer.get();

                // Calculating glyph Region on the texture & glyph Width and Height
                final Region regionOnTexture = new Region(quad.s0(), quad.t0(), quad.s1(), quad.t1());
                float glyphHeight = quad.y1() - quad.y0();
                float glyphWidth = quad.x1() - quad.x0();

                // Adding Glyph to the font
                glyphs.add(new Glyph(
                    code,

                    quad.x0(),
                    -quad.y0() - glyphHeight - descent,
                    glyphWidth,
                    glyphHeight,

                    regionOnTexture,
                    advanceX,
                    0,
                    pages
                ));
            }
        }

        return font;
    }

    public static BitmapFont loadTrueType(String filePath, int size){
        return loadTrueType(filePath, size, FontCharset.DEFAULT);
    }

}
