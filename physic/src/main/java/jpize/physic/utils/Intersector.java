package jpize.physic.utils;

import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.Ray3f;
import jpize.physic.axisaligned.box.AABoxBody;

public class Intersector{

    public static boolean isOverlapping1D(float min1, float max1, float min2, float max2){
        return min1 <= max2 && max1 >= min2;
    }

    public static boolean isOverlapping2D(float min1X, float min1Y, float max1X, float max1Y,
                                          float min2X, float min2Y, float max2X, float max2Y){
        return isOverlapping1D(min1X, max1X, min2X, max2X) &&
                isOverlapping1D(min1Y, max1Y, min2Y, max2Y);
    }

    public static boolean isOverlapping3D(float min1X, float min1Y, float min1Z, float max1X, float max1Y, float max1Z,
                                          float min2X, float min2Y, float min2Z, float max2X, float max2Y, float max2Z){
        return isOverlapping2D(min1X, min1Y, max1X, max1Y, min2X, min2Y, max2X, max2Y) &&
                isOverlapping1D(min1Z, max1Z, min2Z, max2Z);
    }


    public static float getRayIntersectionAabb(Ray3f ray, AABoxBody aabb){
        final float x1 = (aabb.getMin().x - ray.origin().x) / ray.dir().x;
        final float x2 = (aabb.getMax().x - ray.origin().x) / ray.dir().x;

        final float y1 = (aabb.getMin().y - ray.origin().y) / ray.dir().y;
        final float y2 = (aabb.getMax().y - ray.origin().y) / ray.dir().y;

        final float z1 = (aabb.getMin().z - ray.origin().z) / ray.dir().z;
        final float z2 = (aabb.getMax().z - ray.origin().z) / ray.dir().z;

        final float max = Maths.max(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        final float min = Maths.min(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

        return (min < 0 || max > min) ? -1 : max;
    }

    public static boolean isRayIntersectAabb(Ray3f ray, AABoxBody aabb){
        return getRayIntersectionAabb(ray, aabb) != -1;
    }

    public static float getRayIntersectionTriangle(Ray3f ray,
                                                   float x1, float y1, float z1,
                                                   float x2, float y2, float z2,
                                                   float x3, float y3, float z3){
        // edge1 = v2 - v1
        final float edge1x = x2 - x1;
        final float edge1y = y2 - y1;
        final float edge1z = z2 - z1;

        // edge2 = v3 - v1
        final float edge2x = x3 - x1;
        final float edge2y = y3 - y1;
        final float edge2z = z3 - z1;

        // h = cross(direction, edge2)
        final float hx = ray.dir().y * edge2z - ray.dir().z * edge2y;
        final float hy = ray.dir().z * edge2x - ray.dir().x * edge2z;
        final float hz = ray.dir().x * edge2y - ray.dir().y * edge2x;

        // a = 1 / dot(h, edge1)
        final float a = 1 / (hx * edge1x + hy * edge1y + hz * edge1z);

        // s = origin - v1
        final float sx = ray.origin().x - x1;
        final float sy = ray.origin().y - y1;
        final float sz = ray.origin().z - z1;

        // u = a * dot(h, s)
        final float u = a * (hx * sx + hy * sy + hz * sz);
        if(u < 0 || u > 1)
            return -1;

        // q = cross(s, edge1)
        final float qx = sy * edge1z - sz * edge1y;
        final float qy = sz * edge1x - sx * edge1z;
        final float qz = sx * edge1y - sy * edge1x;

        // a * dot(q, direction)
        final float v = a * (qx * ray.dir().x + qy * ray.dir().y + qz * ray.dir().z);
        if(v < 0 || u + v > 1)
            return -1;

        // a * dot(q, edge2) * len(direction)
        return a * (qx * edge2x + qy * edge2y + qz * edge2z) * ray.len();
    }

    public static boolean isRayIntersectTriangle(Ray3f ray,
                                                 float x1, float y1, float z1,
                                                 float x2, float y2, float z2,
                                                 float x3, float y3, float z3){
        return getRayIntersectionTriangle(ray, x1, y1, z1, x2, y2, z2, x3, y3, z3) != -1;
    }

    public static float getRayIntersectionTriangle(Ray3f ray, Vec3f v1, Vec3f v2, Vec3f v3){
        return getRayIntersectionTriangle(ray, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z);
    }

    public static boolean isRayIntersectTriangle(Ray3f ray, Vec3f v1, Vec3f v2, Vec3f v3){
        return getRayIntersectionTriangle(ray, v1, v2, v3) != -1;
    }


    public static float getRayIntersectionQuad(Ray3f ray,
                                               float x1, float y1, float z1,
                                               float x2, float y2, float z2,
                                               float x3, float y3, float z3,
                                               float x4, float y4, float z4){
        float result = getRayIntersectionTriangle(ray, x1, y1, z1,  x2, y2, z2,  x3, y3, z3);
        if(result != -1)
            return result;
        return getRayIntersectionTriangle(ray, x3, y3, z3,  x4, y4, z4,  x1, y1, z1);
    }

    public static boolean isRayIntersectQuad(Ray3f ray,
                                             float x1, float y1, float z1,
                                             float x2, float y2, float z2,
                                             float x3, float y3, float z3,
                                             float x4, float y4, float z4){
        return getRayIntersectionQuad(ray, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4) != -1;
    }

    public static float getRayIntersectionQuad(Ray3f ray, Vec3f v1, Vec3f v2, Vec3f v3, Vec3f v4){
        return getRayIntersectionQuad(ray, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z, v4.x, v4.y, v4.z);
    }

    public static boolean isRayIntersectQuad(Ray3f ray, Vec3f v1, Vec3f v2, Vec3f v3, Vec3f v4){
        return getRayIntersectionQuad(ray, v1, v2, v3, v4) != -1;
    }


    public static float getRayIntersectionMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride, int positionAttributeOffset){
        for(int i = 0; i < indices.length;){
            int offset1 = indices[i++] * stride + positionAttributeOffset;
            int offset2 = indices[i++] * stride + positionAttributeOffset;
            int offset3 = indices[i++] * stride + positionAttributeOffset;

            final Vec3f v1 = new Vec3f(vertices[offset1++], vertices[offset1++], vertices[offset1]).mulMat4(mat);
            final Vec3f v2 = new Vec3f(vertices[offset2++] ,vertices[offset2++] ,vertices[offset2]).mulMat4(mat);
            final Vec3f v3 = new Vec3f(vertices[offset3++] ,vertices[offset3++] ,vertices[offset3]).mulMat4(mat);

            final float result = getRayIntersectionTriangle(ray, v1, v2, v3);
            if(result != -1)
                return result;
        }
        return -1;
    }

    public static float getRayIntersectionMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride){
        return getRayIntersectionMesh(ray, mat, vertices, indices, stride, 0);
    }

    public static float getRayIntersectionMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices){
        return getRayIntersectionMesh(ray, mat, vertices, indices, 3);
    }

    public static boolean isRayIntersectMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride, int positionAttributeOffset){
        return getRayIntersectionMesh(ray, mat, vertices, indices, stride, positionAttributeOffset) != -1;
    }

    public static boolean isRayIntersectMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride){
        return isRayIntersectMesh(ray, mat, vertices, indices, stride, 0);
    }

    public static boolean isRayIntersectMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices){
        return isRayIntersectMesh(ray, mat, vertices, indices, 3);
    }


    public static float getRayIntersectionQuadMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride, int positionAttributeOffset){
        for(int i = 0; i < indices.length;){
            int offset1 = indices[i++] * stride + positionAttributeOffset;
            int offset2 = indices[i++] * stride + positionAttributeOffset;
            int offset3 = indices[i++] * stride + positionAttributeOffset;
            int offset4 = indices[i++] * stride + positionAttributeOffset;

            final Vec3f v1 = new Vec3f(vertices[offset1++], vertices[offset1++], vertices[offset1]).mulMat4(mat);
            final Vec3f v2 = new Vec3f(vertices[offset2++] ,vertices[offset2++] ,vertices[offset2]).mulMat4(mat);
            final Vec3f v3 = new Vec3f(vertices[offset3++] ,vertices[offset3++] ,vertices[offset3]).mulMat4(mat);
            final Vec3f v4 = new Vec3f(vertices[offset4++] ,vertices[offset4++] ,vertices[offset4]).mulMat4(mat);

            final float result = getRayIntersectionQuad(ray, v1, v2, v3, v4);
            if(result != -1)
                return result;
        }
        return -1;
    }

    public static float getRayIntersectionQuadMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride){
        return getRayIntersectionQuadMesh(ray, mat, vertices, indices, stride, 0);
    }

    public static float getRayIntersectionQuadMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices){
        return getRayIntersectionQuadMesh(ray, mat, vertices, indices, 3);
    }

    public static boolean isRayIntersectQuadMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride, int positionAttributeOffset){
        return getRayIntersectionQuadMesh(ray, mat, vertices, indices, stride, positionAttributeOffset) != -1;
    }

    public static boolean isRayIntersectQuadMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices, int stride){
        return isRayIntersectQuadMesh(ray, mat, vertices, indices, stride, 0);
    }

    public static boolean isRayIntersectQuadMesh(Ray3f ray, Matrix4f mat, float[] vertices, int[] indices){
        return isRayIntersectQuadMesh(ray, mat, vertices, indices, 3);
    }

}
