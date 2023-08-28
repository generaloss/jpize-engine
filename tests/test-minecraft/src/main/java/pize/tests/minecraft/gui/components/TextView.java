package pize.tests.minecraft.gui.components;

import pize.Pize;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.Glyph;
import pize.graphics.util.batch.TextureBatch;
import pize.graphics.util.color.Color;
import pize.gui.UIComponent;
import pize.gui.constraint.Constraint;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;
import pize.tests.minecraft.Session;
import pize.tests.minecraft.gui.text.Component;
import pize.tests.minecraft.gui.text.TextComponent;
import pize.tests.minecraft.gui.text.formatting.Style;

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

    private boolean scissor;
    private List<TextComponent> components;
    private final List<String> textList;
    private Vec2f bounds;
    
    private boolean scrollDir;
    private float scrollShiftX;
    
    public TextView(Session session, Component text){
        this.session = session;

        this.font = session.getResourceManager().getFont("font_minecraft");
        this.text = text;

        size = Constraint.pixel(8);

        textList = new ArrayList<>();
    }


    @Override
    public void correctSize(){
        if(font == null)
            return;

        font.setScale(super.calcConstraintY(size) / font.getLineHeight());
        components = text.getAllComponents(session);
        bounds = getBounds(components);

        width = bounds.x;
        height = bounds.y;
    }
    
    @Override
    public void correctPos(){
        // Scroll
        
        final UIComponent<?> parent = getParent();
        final float widthDifference;
        
        if(scissor && parent != null && (widthDifference = width - parent.getWidth()) > 0){
            final float xDifference = this.x - parent.getX();
            final float relativeX = xDifference + scrollShiftX;
            final float increment = Pize.getDt() * parent.getWidth() * widthDifference / 5000;
            final int scissorOffset = Math.round(parent.getHeight() / 20) * 2;
            
            if(scrollDir){
                if(relativeX + scissorOffset <= -widthDifference){
                    scrollShiftX = -widthDifference - xDifference - scissorOffset;
                    scrollDir = false;
                }else
                    scrollShiftX -= increment;
            }else{
                if(relativeX - scissorOffset >= 0){
                    scrollShiftX = 0 - xDifference + scissorOffset;
                    scrollDir = true;
                }else
                    scrollShiftX += increment;
            }
        }else
            scrollShiftX = 0;
        
        super.correctPos();
    }

    @Override
    public void render(TextureBatch batch, float x, float y, float width, float height){
        if(font == null || components.size() == 0)
            return;
        
        // Rotate glyphs on batch

        batch.setTransformOrigin(0, 0);
        batch.rotate(rotation);
        
        // Init
        
        final float lineHeight = font.getLineHeight();
        final float scale = font.getScale();

        float advanceX = 0;
        float advanceY = lineHeight * (text.getAllText(session).split("\n").length - 1);

        // Calculate centering offset
        
        final double angle = rotation * Maths.ToRad + Math.atan(bounds.y / bounds.x);
        final float boundsCenter = Mathc.hypot(bounds.x / 2, bounds.y / 2);
        final float centeringOffsetX = boundsCenter * Mathc.cos(angle) - bounds.x / 2;
        final float centeringOffsetY = boundsCenter * Mathc.sin(angle) - bounds.y / 2;

        // Rotation

        final float cos = Mathc.cos(rotation * Maths.ToRad);
        final float sin = Mathc.sin(rotation * Maths.ToRad);

        // Calc shadow offset

        final float shadowOffsetX = (cos + sin) * scale * SHADOW_OFFSET;
        final float shadowOffsetY = (sin - cos) * scale * SHADOW_OFFSET;

        // Begin scissor
        
        final UIComponent<?> parent = getParent();
        if(scissor && parent != null){
            float offset = parent.getHeight() / 20 * 2;
            
            batch.getScissor().begin(
                54,
                parent.getX() + offset, parent.getY() + offset,
                parent.getWidth() - offset * 2, parent.getHeight() - offset * 2,
                228
            );
        }
        // Text cycle
        
        int textIndex = 0;
        for(TextComponent textComponent: components){
            String text = textList.get(textIndex++);
            Style style = textComponent.getStyle();

            final boolean italic = style.italic;
            final boolean bold = style.bold;
            final boolean underline = style.underline;
            final boolean strikethrough = style.strikethrough;

            final float lineBeginX = advanceX * scale;
            final float lineBeginY = advanceY * scale;
            float lineWidth = 0;
            
            final Color color = new Color(style.color);
            if(blocked)
                color.mul(0.6F, 0.6F, 0.6F, 1);

            for(int i = 0; i < text.length(); i++){
                // Getting glyph
                
                final int code = Character.codePointAt(text, i);

                if(code == 10){
                    advanceY -= lineHeight;
                    advanceX = 0;
                    continue;
                }
                
                final Glyph glyph = font.getGlyph(code);
                if(glyph == null)
                    continue;

                // Calculate glyph render position

                final float xOffset = (advanceX + glyph.offsetX) * scale + scrollShiftX;
                final float yOffset = (advanceY + glyph.offsetY) * scale;

                final float renderX = x + xOffset * cos - yOffset * sin - centeringOffsetX;
                final float renderY = y + yOffset * cos + xOffset * sin - centeringOffsetY;

                // Render shadow

                batch.shear(italic ? BitmapFont.ITALIC_ANGLE : 0, 0);
                batch.setColor(color);

                if(!disableShadow && !blocked){
                    batch.setColor(color.r() * 0.25, color.g() * 0.25, color.b() * 0.25, color.a());
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
                
                final float advanceXIncrease = glyph.advanceX + (bold ? BOLD_OFFSET : 0);

                // Strikethrough & Underline

                if(strikethrough && i == text.length() - 1){
                    batch.drawQuad(
                        1, 1, 1, 1,
                        x - centeringOffsetX + lineBeginX,
                        y - centeringOffsetY + lineBeginY + font.getLineHeight() * 3 / 8F * scale,
                        (lineWidth + advanceXIncrease) * scale,
                        scale
                    );
                }

                if(underline && i == text.length() - 1){
                    batch.drawQuad(
                        1, 1, 1, 1,
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

        // End scissor
        
        if(scissor && parent != null)
            batch.getScissor().end(54);
        
        // End

        font.setRotation(0);

        batch.rotate(0);
        batch.shear(0, 0);
        batch.scale(1);
        batch.resetColor();
    }

    private Vec2f getBounds(List<TextComponent> components){
        float lineHeight = font.getLineHeight();
        float scale = font.getScale();

        int advanceX = 0;
        float advanceY = lineHeight;

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
    
    
    public Constraint getSizeConstraint(){
        return size;
    }
    
    @Override
    public void setSize(Constraint lineHeight){
        size = lineHeight;
    }

    
    public boolean isScissor(){
        return scissor;
    }
    
    public void setScissor(boolean scissor){
        this.scissor = scissor;
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

}
