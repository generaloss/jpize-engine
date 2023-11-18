package jpize.graphics.texture;

public class TextureRegion extends Region{
    
    private Texture texture;


    public TextureRegion(TextureRegion textureRegion){
        setTextureRegion(textureRegion);
    }

    public TextureRegion(Texture texture, int x, int y, int width, int height){
        setTextureRegion(texture, x, y, width, height);
    }

    public TextureRegion(Texture texture, double u1, double v1, double u2, double v2){
        setTextureRegion(texture, u1, v1, u2, v2);
    }

    public TextureRegion(Texture texture, Region region){
        setTextureRegion(texture, region);
    }

    public TextureRegion(Texture texture){
        setTextureRegion(texture, 0D, 0D, 1D, 1D);
    }


    public TextureRegion(TextureRegion texture, int x, int y, int width, int height){
        setTextureRegion(texture, x, y, width, height);
    }

    public TextureRegion(TextureRegion texture, double u1, double v1, double u2, double v2){
        setTextureRegion(texture, u1, v1, u2, v2);
    }

    public TextureRegion(TextureRegion texture, Region region){
        setTextureRegion(texture, region);
    }


    public void setTextureRegion(TextureRegion textureRegion){
        texture = textureRegion.texture;
        super.set(textureRegion.u1, textureRegion.v1, textureRegion.u2, textureRegion.v2);
    }

    public void setTextureRegion(Texture texture, int x, int y, int width, int height){
        setTexture(texture);
        setRegion(x, y, width, height);
    }

    public void setTextureRegion(Texture texture, double u1, double v1, double u2, double v2){
        setTexture(texture);
        setRegion(u1, v1, u2, v2);
    }

    public void setTextureRegion(Texture texture, Region region){
        setTexture(texture);
        setRegion(region);
    }


    public void setTextureRegion(TextureRegion textureRegion, int x, int y, int width, int height){
        setTexture(textureRegion.getTexture());
        setRegion(Region.calcRegionInRegion(textureRegion, calcFromRect(x, y, width, height, texture)));
    }

    public void setTextureRegion(TextureRegion textureRegion, double u1, double v1, double u2, double v2){
        setTexture(textureRegion.getTexture());
        setRegion(Region.calcRegionInRegion(textureRegion, u1, v1, u2, v2));
    }

    public void setTextureRegion(TextureRegion textureRegion, Region region){
        setTexture(textureRegion.getTexture());
        setRegion(Region.calcRegionInRegion(textureRegion, region));
    }


    public void setRegion(int x, int y, int width, int height){
        setRegion(calcFromRect(x, y, width, height, texture));
    }

    public void setRegion(double u1, double v1, double u2, double v2){
        super.set(u1, v1, u2, v2);
    }

    public void setRegion(Region region){
        super.set(region);
    }

    
    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public Texture getTexture(){
        return texture;
    }

    
    public float getWidthPx(){
        return getWidthPx(texture);
    }

    public float getHeightPx(){
        return getHeightPx(texture);
    }


    public TextureRegion copy(){
        return new TextureRegion(this);
    }
    
    public static Region calcFromRect(int x, int y, int width, int height, Texture texture){
        return new Region(
            (double) x / texture.getWidth(),
            (double) y / texture.getHeight(),
            (double) (x + width) / texture.getWidth(),
            (double) (y + height) / texture.getHeight()
        );
    }

}
