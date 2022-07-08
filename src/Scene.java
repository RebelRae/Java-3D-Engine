package src;

import java.util.ArrayList;
import java.util.Collections;

import src.Helper.Colors;
import src.Object.Camera;
import src.Object.Light;
import src.Object.LineObject;
import src.Object.SceneObject;
import src.Primitive.Line;
import src.Primitive.Matrix4;
import src.Primitive.Mesh;
import src.Primitive.Triangle;
import src.Primitive.Vector3;
import java.awt.Color;
import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.BasicStroke;

public class Scene {
    private ArrayList<SceneObject> sceneObjects;
    public Camera activeCamera;
    public Light sceneLight;

    private boolean showWireframe = false;
    private LineObject grid;

    public Scene() {
        this.sceneObjects = new ArrayList<>();
        this.activeCamera = new Camera();
        this.sceneObjects.add(activeCamera);
        this.sceneLight = new Light();
        this.sceneLight.setRotation(new Vector3(0.0f, 0.0f, -1.0f));
        grid = new LineObject();
        grid.setPosition(new Vector3(0.0f, 1.0f, 20.0f));
        grid.setRotation(new Vector3(0.0f, 1.0f, 0.0f));
        for (float x = -10; x < 11; x++) {
            Line line = new Line(
                new Vector3(x + 0.0f, 0.0f, 10.0f),
                new Vector3(x + 0.0f, 0.0f, -10.0f)
            );
            line.color = x == 0? Color.GREEN : Color.GRAY;
            grid.lines.add(line);
        }
        for (float z = -10; z < 11; z++) {
            Line line = new Line(
                new Vector3(10.0f, 0.0f, z + 0.0f),
                new Vector3(-10.0f, 0.0f, z + 0.0f)
            );
            line.color = z == 0? Color.RED : Color.GRAY;
            grid.lines.add(line);
        }
        Line line = new Line(
            new Vector3(0.0f, 10.0f, 0.0f),
            new Vector3(0.0f, -10.0f, 0.0f)
        );
        line.color = Color.BLUE;
        grid.lines.add(line);
        sceneObjects.add(grid);
    }

    public void render(double DELTA_UPDATE, Graphics graphics) {
        // Drawables
        ArrayList<Line> drawLines = new ArrayList<>();
        ArrayList<Triangle> drawTriangles = new ArrayList<>();
        for (SceneObject object : sceneObjects) {
            if(object.getClass().equals(LineObject.class)) {
                LineObject o = (LineObject) object;
                // Rotate X
                Matrix4 xRotationMatrix = Matrix4.RotateMatrixX(o.getRotation().x);
                // Rotate Y
                Matrix4 yRotationMatrix = Matrix4.RotateMatrixY((float) DELTA_UPDATE * o.getRotation().y);
                // Rotate Z
                Matrix4 zRotationMatrix = Matrix4.RotateMatrixZ(o.getRotation().z);
                for (int i = 0; i < o.lines.size(); i++) {
                    Line line = new Line(o.lines.get(i).head, o.lines.get(i).tail);
                    line.color = o.lines.get(i).color;
                    // Rotate X
                    line.head = xRotationMatrix.MultiplyMatrixVector(line.head);
                    line.tail = xRotationMatrix.MultiplyMatrixVector(line.tail);
                    // Rotate Y
                    line.head = yRotationMatrix.MultiplyMatrixVector(line.head);
                    line.tail = yRotationMatrix.MultiplyMatrixVector(line.tail);
                    // Rotate Z
                    line.head = zRotationMatrix.MultiplyMatrixVector(line.head);
                    line.tail = zRotationMatrix.MultiplyMatrixVector(line.tail);
                    // Translation
                    line.head = Vector3.Add(line.head, o.getPosition());
                    line.tail = Vector3.Add(line.tail, o.getPosition());
                    // Projection
                    line.head = this.activeCamera.getProjectionMatrix().MultiplyMatrixVector(line.head);
                    line.tail = this.activeCamera.getProjectionMatrix().MultiplyMatrixVector(line.tail);
                    // Scaling and Normalizing
                    line.head.x += 1.0f; line.head.y += 1.0f;
                    line.tail.x += 1.0f; line.tail.y += 1.0f;
                    line.head.x *= 0.5f * this.activeCamera.getDimension().getWidth(); line.head.y *= 0.5f * this.activeCamera.getDimension().getHeight();
                    line.tail.x *= 0.5f * this.activeCamera.getDimension().getWidth(); line.tail.y *= 0.5f * this.activeCamera.getDimension().getHeight();
                    // Pack raster list
                    drawLines.add(line);
                }
            }
            if(object.Mesh() != null) {
                Mesh mesh = object.Mesh();
                // Rotate X
                Matrix4 xRotationMatrix = Matrix4.RotateMatrixX((float)DELTA_UPDATE * mesh.xRotSpeed);
                // Rotate Y
                Matrix4 yRotationMatrix = Matrix4.RotateMatrixY((float)DELTA_UPDATE * mesh.yRotSpeed);
                // Rotate Z
                Matrix4 zRotationMatrix = Matrix4.RotateMatrixZ((float)DELTA_UPDATE * mesh.zRotSpeed);
                // Draw Mesh
                for (int i = 0; i < mesh.triangles.size(); i++) {
                    // Clone
                    Triangle triangle = new Triangle();
                    triangle.vertices[0] = mesh.triangles.get(i).vertices[0];
                    triangle.vertices[1] = mesh.triangles.get(i).vertices[1];
                    triangle.vertices[2] = mesh.triangles.get(i).vertices[2];
                    // Rotate X
                    triangle.vertices[0] = xRotationMatrix.MultiplyMatrixVector(triangle.vertices[0]);
                    triangle.vertices[1] = xRotationMatrix.MultiplyMatrixVector(triangle.vertices[1]);
                    triangle.vertices[2] = xRotationMatrix.MultiplyMatrixVector(triangle.vertices[2]);
                    // Rotate Y
                    triangle.vertices[0] = yRotationMatrix.MultiplyMatrixVector(triangle.vertices[0]);
                    triangle.vertices[1] = yRotationMatrix.MultiplyMatrixVector(triangle.vertices[1]);
                    triangle.vertices[2] = yRotationMatrix.MultiplyMatrixVector(triangle.vertices[2]);
                    // Rotate Z
                    triangle.vertices[0] = zRotationMatrix.MultiplyMatrixVector(triangle.vertices[0]);
                    triangle.vertices[1] = zRotationMatrix.MultiplyMatrixVector(triangle.vertices[1]);
                    triangle.vertices[2] = zRotationMatrix.MultiplyMatrixVector(triangle.vertices[2]);
                    // Translation
                    triangle.vertices[0] = Vector3.Add(triangle.vertices[0], object.getPosition());
                    triangle.vertices[1] = Vector3.Add(triangle.vertices[1], object.getPosition());
                    triangle.vertices[2] = Vector3.Add(triangle.vertices[2], object.getPosition());
                    // Calculate Normals
                    Vector3 lineA = Vector3.Subtract(triangle.vertices[1], triangle.vertices[0]);
                    Vector3 lineB = Vector3.Subtract(triangle.vertices[2], triangle.vertices[0]);
                    Vector3 normal = Vector3.Cross(lineA, lineB);
                    Vector3 n = Vector3.Normalize(normal);
                    // Backface Culling from camera
                    if(n.DotProduct(Vector3.Subtract(triangle.vertices[0], this.activeCamera.getPosition())) < 0.0f) { // TODO : Maybe rotation
                        // Illuminate
                        Vector3 lightDirection = this.sceneLight.getRotation().Clone();
                        lightDirection.Normalize();
                        float dot = normal.DotProduct(lightDirection);
                        triangle.color = Colors.Illuminate(mesh.getColor(), dot);
                        // Projection
                        triangle.vertices[0] = this.activeCamera.getProjectionMatrix().MultiplyMatrixVector(triangle.vertices[0]);
                        triangle.vertices[1] = this.activeCamera.getProjectionMatrix().MultiplyMatrixVector(triangle.vertices[1]);
                        triangle.vertices[2] = this.activeCamera.getProjectionMatrix().MultiplyMatrixVector(triangle.vertices[2]);
                        // Scaling and Normalizing
                        triangle.vertices[0].x += 1.0f; triangle.vertices[0].y += 1.0f;
                        triangle.vertices[1].x += 1.0f; triangle.vertices[1].y += 1.0f;
                        triangle.vertices[2].x += 1.0f; triangle.vertices[2].y += 1.0f;
                        triangle.vertices[0].x *= 0.5f * this.activeCamera.getDimension().getWidth(); triangle.vertices[0].y *= 0.5f * this.activeCamera.getDimension().getHeight();
                        triangle.vertices[1].x *= 0.5f * this.activeCamera.getDimension().getWidth(); triangle.vertices[1].y *= 0.5f * this.activeCamera.getDimension().getHeight();
                        triangle.vertices[2].x *= 0.5f * this.activeCamera.getDimension().getWidth(); triangle.vertices[2].y *= 0.5f * this.activeCamera.getDimension().getHeight();
                        // Pack raster list
                        drawTriangles.add(triangle);
                    }
                }
            }
        }
        // Render
        Collections.sort(drawLines);
        for (Line line : drawLines) {
            // Draw Lines
            graphics.setColor(line.color);
            // Graphics2D g2 = (Graphics2D) graphics;
            // g2.setStroke(new BasicStroke(3));
            graphics.drawLine((int)line.head.x, (int)line.head.y, (int)line.tail.x, (int)line.tail.y);
        }
        Collections.sort(drawTriangles); // TODO : Switch to zBuffer
        for (Triangle triangle : drawTriangles) {
            // Draw Triangles
            graphics.setColor(triangle.color);
            graphics.fillPolygon(new int[] {
                (int)triangle.vertices[0].x, (int)triangle.vertices[1].x, (int)triangle.vertices[2].x
            }, new int[] {
                (int)triangle.vertices[0].y, (int)triangle.vertices[1].y, (int)triangle.vertices[2].y
            }, 3);
            // Draw Wireframe
            if(this.showWireframe) {
                graphics.setColor(Color.GREEN);
                graphics.drawPolygon(new int[] {
                    (int)triangle.vertices[0].x, (int)triangle.vertices[1].x, (int)triangle.vertices[2].x
                }, new int[] {
                    (int)triangle.vertices[0].y, (int)triangle.vertices[1].y, (int)triangle.vertices[2].y
                }, 3);
            }
        }
    }

    public ArrayList<SceneObject> Objects() { return this.sceneObjects; }
    public SceneObject Object(int index) { return this.sceneObjects.get(index); }
    public void AddObject(SceneObject object) { if(!sceneObjects.contains(object)) sceneObjects.add(object); }
    public void RemoveObject(SceneObject object) { sceneObjects.remove(object); }
    public void RemoveObject(int index) { sceneObjects.remove(index); }
}
