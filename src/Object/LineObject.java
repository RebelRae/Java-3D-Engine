package src.Object;

import java.util.ArrayList;

import src.Primitive.Line;

public class LineObject extends SceneObject {
    public ArrayList<Line> lines = new ArrayList<>();
    public float yRotSpeed, xRotSpeed, zRotSpeed;
    public LineObject() {
        this.xRotSpeed = 0.0f;
        this.yRotSpeed = 0.0f;
        this.zRotSpeed = 0.0f;
    }
}
