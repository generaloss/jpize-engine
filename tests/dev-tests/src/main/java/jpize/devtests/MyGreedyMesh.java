package jpize.devtests;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.Align;
import jpize.gui.components.Image;
import jpize.gui.components.Layout;
import jpize.gui.components.TextView;
import jpize.gui.constraint.Constraint;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import jpize.math.Maths;

public class MyGreedyMesh extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext(1280, 720, "My Greedy Meshing")
                .register().setAdapter(new MyGreedyMesh());

        Jpize.runContexts();
    }


    TextureBatch batch = new TextureBatch(2048);
    Layout layout = new Layout();
    Texture tile1 = new Texture("texture11.png");
    Texture tile2 = new Texture("texture17.png");
    Image button1 = new Image(tile1);
    Image button2 = new Image(tile2);
    Texture button3d = new Texture("texture2.png");
    Texture button3h = new Texture("texture1.png");
    Image button3 = new Image(button3d);
    TextView selectedText = new TextView("selected: 1", FontLoader.getDefault());

    int gridSize = 64;

    int selectedTile = 1;
    Texture[] tileTexture = new Texture[]{ null, tile1, tile2 };
    int[][] tiles = new int[gridSize][gridSize];

    public void init(){
        button1.setSize(Constraint.pixel(100));
        button2.setSize(Constraint.pixel(100));
        button3.setSize(Constraint.pixel(100));

        button1.setPosition(Constraint.pixel(10));
        button2.setPosition(Constraint.pixel(120), Constraint.pixel(10));
        button3.setPosition(Constraint.pixel(10), Constraint.pixel(10));
        button3.alignSelf(Align.RIGHT_UP);

        button1.getColor().set3(0.9, 1, 0.9);
        button2.getColor().set3(0.5, 0.5, 0.5);

        selectedText.setPosition(Constraint.pixel(340), Constraint.pixel(-10));
        selectedText.setSize(Constraint.pixel(200), Constraint.pixel(10));

        layout.alignItems(Align.LEFT_UP);
        layout.put("button1", button1);
        layout.put("button2", button2);
        layout.put("button3", button3);
        layout.put("selectedText", selectedText);

        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
                tiles[i][j] = Maths.random(1, 2);
    }

    public void render(){
        if(layout.get("button1").isTouchDown()){
            selectedTile = 1;
            ((Image) layout.get("button1")).getColor().set3(0.9, 1, 0.9);
            ((Image) layout.get("button2")).getColor().set3(0.5, 0.5, 0.5);
            selectedText.setText("selected: 1");
        }
        if(layout.get("button2").isTouchDown()){
            selectedTile = 2;
            ((Image) layout.get("button1")).getColor().set3(0.5, 0.5, 0.5);
            ((Image) layout.get("button2")).getColor().set3(0.9, 1, 0.9);
            selectedText.setText("selected: 2");
        }
        if(layout.get("button3").isHover()){
            ((Image) layout.get("button3")).setTexture(button3h);
        }else{
            ((Image) layout.get("button3")).setTexture(button3d);
        }
        if(layout.get("button3").isTouchDown()){
            int tile = Maths.random(1, 2);
            for(int i = 0; i < gridSize; i++)
                for(int j = 0; j < gridSize; j++)
                    tiles[i][j] = tile;
        }

        batch.begin();

        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.25);

        layout.render(batch);

        int size = (Jpize.getHeight() - 100) / gridSize;
        int x = Jpize.getWidth() / 2 - size * gridSize / 2;
        int y = 50;

        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                int tile = tiles[i][j];

                Texture texture = tileTexture[tile];
                batch.draw(texture, x + i * size, y + j * size, size, size);
            }
        }


        if(Jpize.mouse().isInBounds(x, y, size * gridSize, size * gridSize)){
            int i = Maths.floor((Jpize.getX() - x) / size);
            int j = Maths.floor((Jpize.getY() - y) / size);

            if(Jpize.isTouched())
                tiles[i][j] = selectedTile;

            batch.drawQuad(1, 1, 1, 0.3, x + i * size, y + j * size, size, size);
        }

        batch.end();
    }

}
