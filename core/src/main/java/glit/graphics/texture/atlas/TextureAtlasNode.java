package glit.graphics.texture.atlas;

import glit.graphics.texture.Pixmap;

import java.awt.*;

public class TextureAtlasNode{

    public TextureAtlasNode[] child;
    public Rectangle rect;
    public Pixmap image;

    public TextureAtlasNode(int x, int y, int width, int height){
        child = new TextureAtlasNode[2];
        rect = new Rectangle(x, y, width, height);
    }


    public TextureAtlasNode insert(Pixmap image, int padding){
        if(child[0] == null && child[1] == null){ // is leaf
            if(this.image != null) // occupied
                return null;

            if(image.getWidth() > rect.width || image.getHeight() > rect.height) // does not fit
                return null;

            if(image.getWidth() == rect.width && image.getHeight() == rect.height){ // perfect fit
                this.image = image;
                return this;
            }

            int dw = rect.width - image.getWidth();
            int dh = rect.height - image.getHeight();

            if(dw > dh){
                child[0] = new TextureAtlasNode(rect.x, rect.y, image.getWidth(), rect.height);
                child[1] = new TextureAtlasNode(
                    padding + rect.x + image.getWidth(),
                    rect.y,
                    rect.width - image.getWidth() - padding,
                    rect.height
                );
            }else{
                child[0] = new TextureAtlasNode(rect.x, rect.y, rect.width, image.getHeight());
                child[1] = new TextureAtlasNode(
                    rect.x,
                    padding + rect.y + image.getHeight(),
                    rect.width,
                    rect.height - image.getHeight() - padding
                );
            }

            return child[0].insert(image, padding);
        }else{
            TextureAtlasNode newNode = child[0].insert(image, padding);
            if(newNode != null)
                return newNode;

            return child[1].insert(image, padding);
        }
    }

}
