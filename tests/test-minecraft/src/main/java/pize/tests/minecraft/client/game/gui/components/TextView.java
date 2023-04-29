package pize.tests.minecraft.client.game.gui.components;

import pize.graphics.font.BitmapFont;
import pize.graphics.font.Glyph;
import pize.graphics.util.batch.Batch;
import pize.graphics.util.color.Color;
import pize.graphics.util.TextureUtils;
import pize.gui.constraint.Constraint;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.tuple.Tuple2f;
import pize.math.vecmath.vector.Vec2f;
import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.client.game.gui.screen.Screen;
import pize.tests.minecraft.client.game.gui.text.Component;
import pize.tests.minecraft.client.game.gui.text.TextComponent;
import pize.tests.minecraft.client.game.gui.text.formatting.Style;

import java.util.ArrayList;
import java.util.List;

public class TextView extends MComponent{

    public static final float SHADOW_OFFSET = 1;
    public static final float BOLD_OFFSET = 1;


    private final Session session;

    private BitmapFont font;
    private Component text;
    private Constraint size;

    private float rotation;
    private boolean blocked;
    private boolean disableShadow;

    private List<TextComponent> components;
    private final List<String> textList;
    private Tuple2f bounds;

    public TextView(Session session, Component text){
        this.session = session;

        this.font = session.getResourceManager().getFont("font_minecraft");
        this.text = text;

        size = MConstraint.text(Screen.TEXT_HEIGHT);

        textList = new ArrayList<>();
    }


    @Override
    public void update(){
        if(font == null)
            return;

        font.setScale(super.calcConstraintY(size) / font.getLineHeight());
        components = text.getAllComponents(session);
        bounds = getBounds(components);

        width = bounds.x;
        height = bounds.y;
    }

    @Override
    public void render(Batch batch, float x, float y, float width, float height){
        if(font == null || components.size() == 0)
            return;

        // Rotate glyphs on batch

        batch.setTransformOrigin(0, 0);
        batch.rotate(rotation);

        // Init

        int lineHeight = font.getLineHeight();
        float scale = font.getScale();

        float advanceX = 0;
        float advanceY = lineHeight * (text.getAllText(session).split("\n").length - 1);

        // Calculate centering offset


        double angle = rotation * Maths.toRad + Math.atan(bounds.y / bounds.x);
        float boundsCenter = Mathc.hypot(bounds.x / 2, bounds.y / 2);
        float centeringOffsetX = boundsCenter * Mathc.cos(angle) - bounds.x / 2;
        float centeringOffsetY = boundsCenter * Mathc.sin(angle) - bounds.y / 2;

        // Rotation

        float cos = Mathc.cos(rotation * Maths.toRad);
        float sin = Mathc.sin(rotation * Maths.toRad);

        // Calc shadow offset

        float shadowOffsetX = (cos + sin) * scale * SHADOW_OFFSET;
        float shadowOffsetY = (sin - cos) * scale * SHADOW_OFFSET;

        // Text cycle

        int textIndex = 0;
        for(TextComponent textComponent: components){
            String text = textList.get(textIndex++);
            Style style = textComponent.getStyle();

            boolean italic = style.italic;
            boolean bold = style.bold;
            boolean underline = style.underline;
            boolean strikethrough = style.strikethrough;

            float lineBeginX = advanceX * scale;
            float lineBeginY = advanceY * scale;
            float lineWidth = 0;

            Color color = new Color(style.color);
            if(blocked)
                color.mul(0.6F, 0.6F, 0.6F, 1);

            for(int i = 0; i < text.length(); i++){
                // Getting glyph

                int code = Character.codePointAt(text, i);

                if(code == 10){
                    advanceY -= lineHeight;
                    advanceX = 0;
                    continue;
                }

                Glyph glyph = font.getGlyph(code);
                if(glyph == null)
                    continue;

                // Calculate glyph render position

                float xOffset = (advanceX + glyph.offsetX) * scale;
                float yOffset = (advanceY + glyph.offsetY) * scale;

                float renderX = x + xOffset * cos - yOffset * sin - centeringOffsetX;
                float renderY = y + yOffset * cos + xOffset * sin - centeringOffsetY;

                // Render shadow

                batch.shear(italic ? BitmapFont.ITALIC_ANGLE : 0, 0);
                batch.setColor(color);

                if(!disableShadow && !blocked){
                    batch.setColor(color.r() * 0.25F, color.g() * 0.25F, color.b() * 0.25F, color.a());
                    glyph.render(batch, (renderX + shadowOffsetX), (renderY + shadowOffsetY));

                    if(bold)
                        glyph.render(batch,
                            renderX + cos * scale * BOLD_OFFSET + shadowOffsetX,
                            renderY + sin * scale * BOLD_OFFSET + shadowOffsetY
                        );
                }

                // Render glyph

                batch.setColor(color);
                glyph.render(batch, renderX, renderY);

                if(bold)
                    glyph.render(batch, renderX + cos * scale, renderY + sin * scale);

                // AdvanceX increase num

                float advanceXIncrease = glyph.advanceX + (bold ? BOLD_OFFSET : 0);

                // Strikethrough & Underline

                if(strikethrough && i == text.length() - 1){
                    batch.draw(TextureUtils.quadTexture(),
                        x - centeringOffsetX + lineBeginX,
                        y - centeringOffsetY + lineBeginY + font.getLineHeight() * 3 / 8F * scale,
                        (lineWidth + advanceXIncrease) * scale,
                        scale
                    );
                }

                if(underline && i == text.length() - 1){
                    batch.draw(TextureUtils.quadTexture(),
                        x - centeringOffsetX + lineBeginX,
                        y - centeringOffsetY + lineBeginY - font.getLineHeight() / 8F * scale,
                        (lineWidth + advanceXIncrease) * scale,
                        scale
                    );
                }

                lineWidth += advanceXIncrease;

                // AdvanceX

                advanceX += advanceXIncrease;
            }
        }

        // End

        font.setRotation(0);

        batch.rotate(0);
        batch.shear(0, 0);
        batch.scale(1);
        batch.resetColor();
    }

    private Tuple2f getBounds(List<TextComponent> components){
        int lineHeight = font.getLineHeight();
        float scale = font.getScale();

        int advanceX = 0;
        int advanceY = lineHeight;

        int maxX = 0;

        textList.clear();
        for(TextComponent component: components){
            String text = component.getText();
            textList.add(text);
            Style style = component.getStyle();

            for(int i = 0; i < text.length(); i++){
                int code = Character.codePointAt(text, i);

                if(code == 10){
                    advanceY += lineHeight;
                    advanceX = 0;
                    continue;
                }

                Glyph glyph = font.getGlyph(code);
                if(glyph == null)
                    continue;

                advanceX += glyph.advanceX + (style.bold ? BOLD_OFFSET : 0);

                maxX = Math.max(maxX, advanceX);
            }
        }

        return new Vec2f(maxX, advanceY).mul(scale);
    }


    public void block(boolean blocked){
        this.blocked = blocked;
    }


    public void setSize(Constraint lineHeight){
        size = lineHeight;
    }


    public BitmapFont getFont(){
        return font;
    }

    public void setFont(BitmapFont font){
        this.font = font;
    }

    public Component getText(){
        return text;
    }

    public void setText(Component component){
        this.text = component;
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    public float getRotation(){
        return rotation;
    }

    public void disableShadow(boolean disableShadow){
        this.disableShadow = disableShadow;
    }

    @Override
    protected float getWidthPixel(){
        return getHeightPixel() * bounds.x / bounds.y;
    }

    @Override
    protected float getHeightPixel(){
        return font.getLineHeight();
    }

}
