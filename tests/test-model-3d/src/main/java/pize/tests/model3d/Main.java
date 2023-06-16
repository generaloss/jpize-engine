package pize.tests.model3d;

import pize.Pize;
import pize.app.AppAdapter;
import pize.files.Resource;
import pize.io.glfw.Key;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.math.vecmath.vector.Vec3i;
import pize.util.io.FastReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main extends AppAdapter{

    public static void main(String[] args){
        Pize.create("Model 3D", 1280, 720);
        Pize.run(new Main());
    }


    public void loadModel(Resource res){
        FastReader reader = res.getReader();

        List<Vec3f> loadedVertices = new ArrayList<>();
        List<Vec3f> loadedNormals = new ArrayList<>();
        List<Vec2f> loadedUvs = new ArrayList<>();
        List<Vec3i> faces = new ArrayList<>();

        while(reader.hasNext()){
            String[] tokens = reader.nextLine().split("\\s+");
            switch(tokens[0]){
                case "v" -> {
                    loadedVertices.add(new Vec3f(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2]),
                        Float.parseFloat(tokens[3])
                    ));
                }
                case "vn" -> {
                    loadedNormals.add(new Vec3f(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2]),
                        Float.parseFloat(tokens[3])
                    ));
                }
                case "vt" -> {
                    loadedUvs.add(new Vec2f(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2])
                    ));
                }
                case "f" -> {
                    processFace(tokens[1], faces);
                    processFace(tokens[2], faces);
                    processFace(tokens[3], faces);
                }
            }
        }

        List<Integer> loadedIndices = new ArrayList<>();


        Float[] vertices = loadedVertices.stream().flatMap(pos->Stream.of(pos.x, pos.y, pos.z)).toList().toArray(new Float[0]);
        Integer[] indices = loadedIndices.toArray(new Integer[0]);


    }

    public void processVertex(int position, int uv, int normal, List<Vec2f> uvList, List<Vec3f> normalList, List<Integer> indexList, Float[] uvArray, Float[] normalArray){
        indexList.add(position);

        if(uv >= 0){
            Vec2f uvVec = uvList.get(uv);
            uvArray[position] = uvVec.x;
            uvArray[position + 1] = uvVec.y;
        }
    }

    public void processFace(String token, List<Vec3i> faces){
        String[] tokens = token.split("/");

        int position = Integer.parseInt(tokens[0]);
        int uv = tokens.length > 1 ? Integer.parseInt(tokens[1]) : -1;
        int normal = tokens.length > 2 ? Integer.parseInt(tokens[2]) : -1;

        faces.add(new Vec3i(position, uv, normal));
    }


    @Override
    public void init(){
        loadModel(new Resource("cube.obj"));
    }

    @Override
    public void render(){
        if(Key.ESCAPE.isDown())
            Pize.exit();
    }

}
