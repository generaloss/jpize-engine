package test;

import jpize.gl.Gl;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.JpizeApplication;
import jpize.math.vecmath.vector.Vec2f;
import jpize.sdl.input.Key;
import jpize.util.array.list.IntList;

public class ListTest extends JpizeApplication{

    public static final int CELL_SIZE = 100;

    IntList list = new IntList(1);

    TextureBatch batch = new TextureBatch();
    BitmapFont font = FontLoader.getDefault();

    @Override
    public void update(){
        if(Key.A.isDown()) list.add(54);
        if(Key.M.isDown()) list.add(new int[]{ 1, 2, 3, 4 });
        if(Key.N.isDown()) list.add(1, new int[]{ -1, -2, -1 });
        if(Key.R.isDown()) list.remove(1, 2);
        if(Key.P.isDown()) list.add(1, 27);
        if(Key.C.isDown()) list.clear();
        if(Key.T.isDown()) list.trim();
    }

    @Override
    public void render(){
        Gl.clearColor(0.2, 0.23, 0.3);
        Gl.clearColorBuffer();
        batch.begin();

        int[] arr = list.array();
        for(int i = 0; i < arr.length; i++){
            int x = i * CELL_SIZE;
            batch.drawRect(0.5, x, 0, CELL_SIZE, CELL_SIZE);

            if(i >= list.size())
                continue;

            String text = String.valueOf(arr[i]);
            Vec2f bounds = font.getBounds(text);

            font.drawText(batch, text, x + (CELL_SIZE - bounds.x) / 2, (CELL_SIZE - bounds.y) / 2);
        }

        batch.end();
    }

}
