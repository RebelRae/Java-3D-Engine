/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primitive;

import java.awt.Color;

public class Triangle implements Comparable<Triangle> {
    public Vector3[] vertices;
    public Color color;

    public Triangle() {
        this.vertices = new Vector3[3];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vector3();
        }
        this.color = Color.GRAY;
    }
    public Triangle(Vector3 v1, Vector3 v2, Vector3 v3) {
        this.vertices = new Vector3[3];
        vertices[0] = v1;
        vertices[1] = v2;
        vertices[2] = v3;
        this.color = Color.GRAY;
    }
    public float zMid() {
        return (this.vertices[0].z + this.vertices[1].z + this.vertices[2].z) / 3.0f;
    }
    @Override
    public int compareTo(Triangle t) {
        if(this.zMid() < t.zMid()) return 1;
        if(this.zMid() > t.zMid()) return -1;
        return 0;
    }
}
