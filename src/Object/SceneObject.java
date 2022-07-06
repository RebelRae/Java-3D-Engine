package src.Object;

import java.util.Random;

import src.Primitive.Mesh;
import src.Primitive.Vector3;

public class SceneObject {
    public final String ID;

    private Vector3 origin;
    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;
    protected Mesh mesh = null;

    public SceneObject() {
        this.origin = new Vector3();
        this.position = new Vector3();
        this.rotation = new Vector3();
        this.ID = generateID(32);
    }
    public SceneObject(Vector3 position) {
        this.origin = new Vector3();
        this.position = position;
        this.rotation = new Vector3();
        this.ID = generateID(32);
    }
    public SceneObject(Vector3 position, Vector3 rotation) {
        this.origin = new Vector3();
        this.position = position;
        this.rotation = rotation;
        this.ID = generateID(32);
    }

    private String generateID(int length) {
        String plug = "";
        Random r = new Random();
        for (int i = 0; i < length/8; i++) {
            plug += Integer.toHexString(r.nextInt()).toUpperCase();
            if(i < length/8-1) plug += "-";
        }
        return plug;
    }
    
    public Vector3 getOrigin() { return origin; }
    public void setOrigin(Vector3 origin) { this.origin = origin; }
    public Vector3 getPosition() { return position; }
    public void setPosition(Vector3 position) { this.position = position; }
    public Vector3 getRotation() { return rotation; }
    public void setRotation(Vector3 rotation) { this.rotation = rotation; }
    public Vector3 getScale() { return scale; }
    public void setScale(Vector3 scale) { this.scale = scale; }
    public Mesh Mesh() { return this.mesh; }
    public void Mesh(Mesh mesh) { this.mesh = mesh; }
}
