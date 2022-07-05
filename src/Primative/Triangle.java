/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primative;

public class Triangle {
    public Vector3[] vertices;

    public Triangle() {
        this.vertices = new Vector3[3];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vector3();
        }
    }
    public Triangle(Vector3 v1, Vector3 v2, Vector3 v3) {
        this.vertices = new Vector3[3];
        vertices[0] = v1;
        vertices[1] = v2;
        vertices[2] = v3;
    }
}
