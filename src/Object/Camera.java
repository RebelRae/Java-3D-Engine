package src.Object;

import src.Primitive.Dimension;
import src.Primitive.Matrix4;

public class Camera extends SceneObject {
    private float fov;
    private float tangent;
    private float nearPlane;
    private float farPlane;
    private Dimension dimension;
    private Matrix4 projectionMatrix;

    public Camera() {
        this.fov = 60.0f;
        this.nearPlane = 0.1f;
        this.farPlane = 1000.0f;
        this.tangent = 1.0f / (float) Math.tan((Double) (this.fov * 0.5f / 180.0f * Math.PI));
        this.dimension = new Dimension(640, 360);
        this.projectionMatrix = new Matrix4();
        RecalculateProjectionMatrix();
    }

    private void RecalculateProjectionMatrix() {
        this.projectionMatrix.matrix[0][0] = this.dimension.AspectRatio() * this.tangent;
        this.projectionMatrix.matrix[1][1] = this.tangent;
        this.projectionMatrix.matrix[2][2] = this.farPlane / (this.farPlane - this.nearPlane);
        this.projectionMatrix.matrix[3][2] = (-this.farPlane * this.nearPlane) / (this.farPlane - this.nearPlane);
        this.projectionMatrix.matrix[2][3] = 1.0f;
        this.projectionMatrix.matrix[3][3] = 0.0f;
    }

    public float getTangent() { return tangent; }
    public void setTangent(float tangent) { this.tangent = tangent; }
    public float getNearPlane() { return nearPlane; }
    public void setNearPlane(float nearPlane) { this.nearPlane = nearPlane; }
    public float getFarPlane() { return farPlane; }
    public void setFarPlane(float farPlane) { this.farPlane = farPlane; }
    public Dimension getDimension() { return dimension; }
    public void setDimension(Dimension dimension) { this.dimension = dimension; }
    public Matrix4 getProjectionMatrix() { return projectionMatrix; }
}
