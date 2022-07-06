package src.Object;

import java.util.ArrayList;

import src.Primitive.Mesh;
import src.Primitive.Triangle;
import src.Primitive.Vector3;

public class Cube extends SceneObject {
    public Cube() {
        Vector3 v1 = new Vector3(-0.5f, -0.5f, -0.5f);
        Vector3 v2 = new Vector3(-0.5f, -0.5f, 0.5f);
        Vector3 v3 = new Vector3(0.5f, -0.5f, 0.5f);
        Vector3 v4 = new Vector3(0.5f, -0.5f, -0.5f);
        Vector3 v5 = new Vector3(-0.5f, 0.5f, -0.5f);
        Vector3 v6 = new Vector3(-0.5f, 0.5f, 0.5f);
        Vector3 v7 = new Vector3(0.5f, 0.5f, 0.5f);
        Vector3 v8 = new Vector3(0.5f, 0.5f, -0.5f);
        ArrayList<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(v1, v5, v8));
        triangles.add(new Triangle(v1, v8, v4));
        triangles.add(new Triangle(v1, v2, v6));
        triangles.add(new Triangle(v1, v6, v5));
        triangles.add(new Triangle(v1, v4, v3));
        triangles.add(new Triangle(v1, v3, v2));
        triangles.add(new Triangle(v7, v3, v4));
        triangles.add(new Triangle(v7, v4, v8));
        triangles.add(new Triangle(v7, v8, v5));
        triangles.add(new Triangle(v7, v5, v6));
        triangles.add(new Triangle(v7, v6, v2));
        triangles.add(new Triangle(v7, v2, v3));
        this.mesh = new Mesh(triangles);
    }
}
