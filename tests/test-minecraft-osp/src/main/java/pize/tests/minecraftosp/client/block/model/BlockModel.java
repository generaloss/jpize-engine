package pize.tests.minecraftosp.client.block.model;

import pize.graphics.texture.Region;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.math.vecmath.vector.Vec3i;
import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.BlockRotation;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMesh;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshPackedCullingOn;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.chunk.mesh.builder.ChunkBuilder;
import pize.tests.minecraftosp.main.Direction;
import pize.tests.minecraftosp.main.biome.BiomeProperties;

import java.util.*;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class BlockModel{

    private final ChunkMeshType meshType;
    private final List<Face> faces, nxFaces, pxFaces, nyFaces, pyFaces, nzFaces, pzFaces;
    private final Map<Direction, List<Face>> facesFromDirection;
    private final boolean[] transparentForNeighbors;

    public BlockModel(ChunkMeshType meshType){
        this.meshType = meshType;

        this.faces = new ArrayList<>();
        this.nxFaces = new ArrayList<>();
        this.pxFaces = new ArrayList<>();
        this.nyFaces = new ArrayList<>();
        this.pyFaces = new ArrayList<>();
        this.nzFaces = new ArrayList<>();
        this.pzFaces = new ArrayList<>();

        this.facesFromDirection = new HashMap<>();

        facesFromDirection.put(Direction.NEGATIVE_X, nxFaces);
        facesFromDirection.put(Direction.POSITIVE_X, pxFaces);
        facesFromDirection.put(Direction.NEGATIVE_Y, nyFaces);
        facesFromDirection.put(Direction.POSITIVE_Y, pyFaces);
        facesFromDirection.put(Direction.NEGATIVE_Z, nzFaces);
        facesFromDirection.put(Direction.POSITIVE_Z, pzFaces);
        facesFromDirection.put(Direction.NONE, faces);

        this.transparentForNeighbors = new boolean[6];
    }

    public ChunkMeshType getMeshType(){
        return meshType;
    }

    private ChunkMesh getChunkBuilderMesh(ChunkBuilder chunkBuilder){
        return switch(meshType){
            case SOLID -> chunkBuilder.solidMesh;
            case CUSTOM -> chunkBuilder.customMesh;
            case TRANSLUCENT -> chunkBuilder.translucentMesh;
        };
    }


    public List<Face> getDirectionFaces(Direction direction){
        return facesFromDirection.get(direction);
    }

    public List<Face> getFacesFromNormal(int x, int y, int z){
        return getDirectionFaces(Direction.fromNormal(x, y, z));
    }


    public boolean isFaceTransparentForNeighbors(int x, int y, int z){
        final int index = Direction.fromNormal(x, y, z).ordinal();
        if(index > 5)
            return false;

        return transparentForNeighbors[index];
    }

    public BlockModel setFacesTransparentForNeighbors(boolean... transparentForNeighbors){
        System.arraycopy(transparentForNeighbors, 0, this.transparentForNeighbors, 0, transparentForNeighbors.length);
        return this;
    }

    public BlockModel setFacesTransparentForNeighbors(boolean transparentForNeighbors){
        Arrays.fill(this.transparentForNeighbors, transparentForNeighbors);
        return this;
    }


    public BlockModel face(Face face){
        faces.add(face);
        return this;
    }

    public BlockModel face(Direction direction, Face face){
        switch(direction){
            case NEGATIVE_X -> nxFaces.add(face);
            case POSITIVE_X -> pxFaces.add(face);
            case NEGATIVE_Y -> nyFaces.add(face);
            case POSITIVE_Y -> pyFaces.add(face);
            case NEGATIVE_Z -> nzFaces.add(face);
            case POSITIVE_Z -> pzFaces.add(face);
            case NONE -> faces.add(face);
        }
        return this;
    }


    public BlockModel nxFace(Region region, IColor color, byte faceData){
        nxFaces.add(
                new Face(Quad.getNxQuad(), region, color)
                .setFaceData(faceData)
        );
        return this;
    }

    public BlockModel pxFace(Region region, IColor color, byte faceData){
        pxFaces.add(
                new Face(Quad.getPxQuad(), region, color)
                .setFaceData(faceData)
        );
        return this;
    }

    public BlockModel nyFace(Region region, IColor color, byte faceData){
        nyFaces.add(
                new Face(Quad.getNyQuad(), region, color)
                .setFaceData(faceData)
        );
        return this;
    }

    public BlockModel pyFace(Region region, IColor color, byte faceData){
        pyFaces.add(
                new Face(Quad.getPyQuad(), region, color)
                .setFaceData(faceData)
        );
        return this;
    }

    public BlockModel nzFace(Region region, IColor color, byte faceData){
        nzFaces.add(
                new Face(Quad.getNzQuad(), region, color)
                .setFaceData(faceData)
        );
        return this;
    }

    public BlockModel pzFace(Region region, IColor color, byte faceData){
        pzFaces.add(
                new Face(Quad.getPzQuad(), region, color)
                .setFaceData(faceData)
        );
        return this;
    }


    public BlockModel nxFace(Region region, byte faceData){
        return nxFace(region, Color.WHITE, faceData);
    }

    public BlockModel pxFace(Region region, byte faceData){
        return pxFace(region, Color.WHITE, faceData);
    }

    public BlockModel nyFace(Region region, byte faceData){
        return nyFace(region, Color.WHITE, faceData);
    }

    public BlockModel pyFace(Region region, byte faceData){
        return pyFace(region, Color.WHITE, faceData);
    }

    public BlockModel nzFace(Region region, byte faceData){
        return nzFace(region, Color.WHITE, faceData);
    }

    public BlockModel pzFace(Region region, byte faceData){
        return pzFace(region, Color.WHITE, faceData);
    }


    public BlockModel nxFace(Region region, IColor color){
        return nxFace(region, color, (byte) 0);
    }

    public BlockModel pxFace(Region region, IColor color){
        return pxFace(region, color, (byte) 0);
    }

    public BlockModel nyFace(Region region, IColor color){
        return nyFace(region, color, (byte) 0);
    }

    public BlockModel pyFace(Region region, IColor color){
        return pyFace(region, color, (byte) 0);
    }

    public BlockModel nzFace(Region region, IColor color){
        return nzFace(region, color, (byte) 0);
    }

    public BlockModel pzFace(Region region, IColor color){
        return pzFace(region, color, (byte) 0);
    }


    public BlockModel allFaces(Region region, IColor color, byte faceData){
        nxFace(region, color, faceData);
        pxFace(region, color, faceData);
        nyFace(region, color, faceData);
        pyFace(region, color, faceData);
        nzFace(region, color, faceData);
        pzFace(region, color, faceData);
        return this;
    }

    public BlockModel allFaces(Region region, IColor color){
        return allFaces(region, color, (byte) 0);
    }

    public BlockModel allFaces(Region region, byte faceData){
        return allFaces(region, Color.WHITE, faceData);
    }

    public BlockModel sideXZFaces(Region region, IColor color, byte faceData){
        nxFace(region, color, faceData);
        pxFace(region, color, faceData);
        nzFace(region, color, faceData);
        pzFace(region, color, faceData);
        return this;
    }

    public BlockModel sideXZFaces(Region region, IColor color){
        return sideXZFaces(region, color, (byte) 0);
    }

    public BlockModel sideXZFaces(Region region, byte faceData){
        return sideXZFaces(region, Color.WHITE, faceData);
    }

    public BlockModel sideXYFaces(Region region, IColor color){
        nxFace(region, color);
        pxFace(region, color);
        nyFace(region, color);
        pyFace(region, color);
        return this;
    }

    public BlockModel sideZYFaces(Region region, IColor color){
        nzFace(region, color);
        pzFace(region, color);
        nyFace(region, color);
        pyFace(region, color);
        return this;
    }

    public BlockModel xFaces(Region region, IColor color){
        nxFace(region, color);
        pxFace(region, color);
        return this;
    }

    public BlockModel yFaces(Region region, IColor color){
        nyFace(region, color);
        pyFace(region, color);
        return this;
    }

    public BlockModel zFaces(Region region, IColor color){
        nzFace(region, color);
        pzFace(region, color);
        return this;
    }


    public BlockModel nxFace(Region region){
        return nxFace(region, Color.WHITE);
    }

    public BlockModel pxFace(Region region){
        return pxFace(region, Color.WHITE);
    }

    public BlockModel nyFace(Region region){
        return nyFace(region, Color.WHITE);
    }

    public BlockModel pyFace(Region region){
        return pyFace(region, Color.WHITE);
    }

    public BlockModel nzFace(Region region){
        return nzFace(region, Color.WHITE);
    }

    public BlockModel pzFace(Region region){
        return pzFace(region, Color.WHITE);
    }

    public BlockModel allFaces(Region region){
        return allFaces(region, Color.WHITE);
    }

    public BlockModel sideXZFaces(Region region){
        return sideXZFaces(region, Color.WHITE);
    }

    public BlockModel sideXYFaces(Region region){
        return sideXYFaces(region, Color.WHITE);
    }

    public BlockModel sideZYFaces(Region region){
        return sideZYFaces(region, Color.WHITE);
    }

    public BlockModel xFaces(Region region){
        return xFaces(region, Color.WHITE);
    }

    public BlockModel yFaces(Region region){
        return yFaces(region, Color.WHITE);
    }

    public BlockModel zFaces(Region region){
        return zFaces(region, Color.WHITE);
    }


    public IColor pickFaceColor(Face face, BiomeProperties biome){
        final IColor color;
        if(face.isGrassColoring())
            color = biome.getGrassColor();
        else if(face.isWaterColoring())
            color = biome.getWaterColor();
        else
            color = Color.WHITE;

        return color;
    }


    public void build(ChunkBuilder builder, BlockProperties block, int lx, int y, int lz){
        if(meshType == ChunkMeshType.CUSTOM){

            // Custom faces
            final float light = (float) builder.chunk.getLight(lx, y, lz) / MAX_LIGHT_LEVEL;
            for(Face face: faces){
                final IColor color = pickFaceColor(face, builder.currentBiome.getBiome());
                face.putFloats(builder.customMesh.getVerticesList(),  lx, y, lz,  color,  light, light, light, light);
            }

        }else{
            // Solid faces
            buildSolidFaces(builder, block, lx, y, lz);
        }
    }

    private void buildSolidFaces(ChunkBuilder builder, BlockProperties block, int lx, int y, int lz){
        if(builder.isGenSolidFace(lx, y, lz, -1,  0,  0, block))
            for(Face face: nxFaces) buildNxFace(builder, face, lx, y, lz);
        if(builder.isGenSolidFace(lx, y, lz, +1,  0,  0, block))
            for(Face face: pxFaces) buildPxFace(builder, face, lx, y, lz);
        if(builder.isGenSolidFace(lx, y, lz,  0, -1,  0, block))
            for(Face face: nyFaces) buildNyFace(builder, face, lx, y, lz);
        if(builder.isGenSolidFace(lx, y, lz,  0, +1,  0, block))
            for(Face face: pyFaces) buildPyFace(builder, face, lx, y, lz);
        if(builder.isGenSolidFace(lx, y, lz,  0,  0, -1, block))
            for(Face face: nzFaces) buildNzFace(builder, face, lx, y, lz);
        if(builder.isGenSolidFace(lx, y, lz,  0,  0, +1, block))
            for(Face face: pzFaces) buildPzFace(builder, face, lx, y, lz);
    }

    private void buildNxFace(ChunkBuilder builder, Face face, int x, int y, int z){
        final float light1 = builder.getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y+1, z+1,  x-1, y+1, z  ) / MAX_LIGHT_LEVEL;
        final float light2 = builder.getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y-1, z+1,  x-1, y-1, z  ) / MAX_LIGHT_LEVEL;
        final float light3 = builder.getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y-1, z-1,  x-1, y-1, z  ) / MAX_LIGHT_LEVEL;
        final float light4 = builder.getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y+1, z-1,  x-1, y+1, z  ) / MAX_LIGHT_LEVEL;

        final float ao1 = builder.getAO(x-1, y+1, z,  x-1, y, z+1,  x-1, y+1, z+1,  x-1, y  , z  );
        final float ao2 = builder.getAO(x-1, y-1, z,  x-1, y, z+1,  x-1, y-1, z+1,  x-1, y  , z  );
        final float ao3 = builder.getAO(x-1, y-1, z,  x-1, y, z-1,  x-1, y-1, z-1,  x-1, y  , z  );
        final float ao4 = builder.getAO(x-1, y+1, z,  x-1, y, z-1,  x-1, y+1, z-1,  x-1, y  , z  );

        final float shadow = 0.8F;

        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        final float brightness4 = shadow * ao4 * light4;

        final IColor color = pickFaceColor(face, builder.currentBiome.getBiome());

        final ChunkMeshPackedCullingOn mesh = meshType == ChunkMeshType.TRANSLUCENT ? builder.translucentMesh : builder.solidMesh;
        if(brightness2 + brightness4 > brightness1 + brightness3)
            face.putIntsPackedFlipped(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
        else
            face.putIntsPacked(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
    }

    private void buildPxFace(ChunkBuilder builder, Face face, int x, int y, int z){
        final float light1 = builder.getLight(x+1, y, z,  x+1, y, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / MAX_LIGHT_LEVEL;
        final float light2 = builder.getLight(x+1, y, z,  x+1, y, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / MAX_LIGHT_LEVEL;
        final float light3 = builder.getLight(x+1, y, z,  x+1, y, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / MAX_LIGHT_LEVEL;
        final float light4 = builder.getLight(x+1, y, z,  x+1, y, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / MAX_LIGHT_LEVEL;

        final float ao1 = builder.getAO(x+1, y+1, z,  x+1, y, z-1,  x+1, y+1, z-1,  x+1, y  , z  );
        final float ao2 = builder.getAO(x+1, y-1, z,  x+1, y, z-1,  x+1, y-1, z-1,  x+1, y  , z  );
        final float ao3 = builder.getAO(x+1, y-1, z,  x+1, y, z+1,  x+1, y-1, z+1,  x+1, y  , z  );
        final float ao4 = builder.getAO(x+1, y+1, z,  x+1, y, z+1,  x+1, y+1, z+1,  x+1, y  , z  );

        final float shadow = 0.8F;

        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        final float brightness4 = shadow * ao4 * light4;

        final IColor color = pickFaceColor(face, builder.currentBiome.getBiome());

        final ChunkMeshPackedCullingOn mesh = meshType == ChunkMeshType.TRANSLUCENT ? builder.translucentMesh : builder.solidMesh;
        if(brightness2 + brightness4 > brightness1 + brightness3)
            face.putIntsPackedFlipped(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
        else
            face.putIntsPacked(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
    }

    private void buildNyFace(ChunkBuilder builder, Face face, int x, int y, int z){
        final float light1 = builder.getLight(x, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / MAX_LIGHT_LEVEL;
        final float light2 = builder.getLight(x, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / MAX_LIGHT_LEVEL;
        final float light3 = builder.getLight(x, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1,  x-1, y-1, z) / MAX_LIGHT_LEVEL;
        final float light4 = builder.getLight(x, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y-1, z) / MAX_LIGHT_LEVEL;

        final float ao1 = builder.getAO(x+1, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1,  x  , y-1, z  );
        final float ao2 = builder.getAO(x+1, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1,  x  , y-1, z  );
        final float ao3 = builder.getAO(x-1, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1,  x  , y-1, z  );
        final float ao4 = builder.getAO(x-1, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1,  x  , y-1, z  );

        final float shadow = 0.6F;

        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        final float brightness4 = shadow * ao4 * light4;

        final IColor color = pickFaceColor(face, builder.currentBiome.getBiome());

        final ChunkMeshPackedCullingOn mesh = meshType == ChunkMeshType.TRANSLUCENT ? builder.translucentMesh : builder.solidMesh;
        if(brightness2 + brightness4 > brightness1 + brightness3)
            face.putIntsPackedFlipped(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
        else
            face.putIntsPacked(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
    }

    private void buildPyFace(ChunkBuilder builder, Face face, int x, int y, int z){
        final float light1 = builder.getLight(x, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / MAX_LIGHT_LEVEL;
        final float light2 = builder.getLight(x, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / MAX_LIGHT_LEVEL;
        final float light3 = builder.getLight(x, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y+1, z) / MAX_LIGHT_LEVEL;
        final float light4 = builder.getLight(x, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1,  x-1, y+1, z) / MAX_LIGHT_LEVEL;

        final float ao1 = builder.getAO(x+1, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1,  x  , y+1, z  );
        final float ao2 = builder.getAO(x+1, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1,  x  , y+1, z  );
        final float ao3 = builder.getAO(x-1, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1,  x  , y+1, z  );
        final float ao4 = builder.getAO(x-1, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1,  x  , y+1, z  );

        final float shadow = 1;

        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        final float brightness4 = shadow * ao4 * light4;

        final IColor color = pickFaceColor(face, builder.currentBiome.getBiome());

        final ChunkMeshPackedCullingOn mesh = meshType == ChunkMeshType.TRANSLUCENT ? builder.translucentMesh : builder.solidMesh;
        if(brightness2 + brightness4 > brightness1 + brightness3)
            face.putIntsPackedFlipped(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
        else
            face.putIntsPacked(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
    }

    private void buildNzFace(ChunkBuilder builder, Face face, int x, int y, int z){
        final float light1 = builder.getLight(x, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1,  x, y+1, z-1) / MAX_LIGHT_LEVEL;
        final float light2 = builder.getLight(x, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1,  x, y-1, z-1) / MAX_LIGHT_LEVEL;
        final float light3 = builder.getLight(x, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1,  x, y-1, z-1) / MAX_LIGHT_LEVEL;
        final float light4 = builder.getLight(x, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1,  x, y+1, z-1) / MAX_LIGHT_LEVEL;

        final float ao1 = builder.getAO(x-1, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1,  x  , y  , z-1);
        final float ao2 = builder.getAO(x-1, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1,  x  , y  , z-1);
        final float ao3 = builder.getAO(x+1, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1,  x  , y  , z-1);
        final float ao4 = builder.getAO(x+1, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1,  x  , y  , z-1);

        final float shadow = 0.7F;

        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        final float brightness4 = shadow * ao4 * light4;

        final IColor color = pickFaceColor(face, builder.currentBiome.getBiome());

        final ChunkMeshPackedCullingOn mesh = meshType == ChunkMeshType.TRANSLUCENT ? builder.translucentMesh : builder.solidMesh;
        if(brightness2 + brightness4 > brightness1 + brightness3)
            face.putIntsPackedFlipped(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
        else
            face.putIntsPacked(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
    }

    private void buildPzFace(ChunkBuilder builder, Face face, int x, int y, int z){
        final float light1 = builder.getLight(x, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y, z+1) / MAX_LIGHT_LEVEL;
        final float light2 = builder.getLight(x, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y, z+1) / MAX_LIGHT_LEVEL;
        final float light3 = builder.getLight(x, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y, z+1) / MAX_LIGHT_LEVEL;
        final float light4 = builder.getLight(x, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y, z+1) / MAX_LIGHT_LEVEL;

        final float ao1 = builder.getAO(x+1, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1,  x  , y  , z+1);
        final float ao2 = builder.getAO(x+1, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1,  x  , y  , z+1);
        final float ao3 = builder.getAO(x-1, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1,  x  , y  , z+1);
        final float ao4 = builder.getAO(x-1, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1,  x  , y  , z+1);

        final float shadow = 0.7F;

        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        final float brightness4 = shadow * ao4 * light4;

        final IColor color = pickFaceColor(face, builder.currentBiome.getBiome());

        final ChunkMeshPackedCullingOn mesh = meshType == ChunkMeshType.TRANSLUCENT ? builder.translucentMesh : builder.solidMesh;
        if(brightness2 + brightness4 > brightness1 + brightness3)
            face.putIntsPackedFlipped(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
        else
            face.putIntsPacked(mesh.getVerticesList(),  x, y, z,  color,  brightness1, brightness2, brightness3, brightness4);
    }


    public BlockModel rotated(BlockRotation rotation){
        final BlockModel model = new BlockModel(meshType);

        for(Face face: faces)
            model.face(face.rotated(rotation));

        for(Face face: nxFaces){
            final Vec3i normal = Direction.NEGATIVE_X.getNormal().copy().mul(rotation.getMatrix());
            model.face(Direction.fromNormal(normal), face.rotated(rotation));
        }
        for(Face face: pxFaces){
            final Vec3i normal = Direction.POSITIVE_X.getNormal().copy().mul(rotation.getMatrix());
            model.face(Direction.fromNormal(normal), face.rotated(rotation));
        }

        for(Face face: nyFaces){
            final Vec3i normal = Direction.NEGATIVE_Y.getNormal().copy().mul(rotation.getMatrix());
            model.face(Direction.fromNormal(normal), face.rotated(rotation));
        }
        for(Face face: pyFaces){
            final Vec3i normal = Direction.POSITIVE_Y.getNormal().copy().mul(rotation.getMatrix());
            model.face(Direction.fromNormal(normal), face.rotated(rotation));
        }

        for(Face face: nzFaces){
            final Vec3i normal = Direction.NEGATIVE_Z.getNormal().copy().mul(rotation.getMatrix());
            model.face(Direction.fromNormal(normal), face.rotated(rotation));
        }
        for(Face face: pzFaces){
            final Vec3i normal = Direction.POSITIVE_Z.getNormal().copy().mul(rotation.getMatrix());
            model.face(Direction.fromNormal(normal), face.rotated(rotation));
        }

        return model;
    }

}
