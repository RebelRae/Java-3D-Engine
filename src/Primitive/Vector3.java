/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primitive;

public class Vector3 extends Vector2 {
    public float z;

    public Vector3() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void Add(Vector3 vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
    }
    public static Vector3 Add(Vector3 a, Vector3 b) {
        return new Vector3(a.x+b.x, a.y+b.y, a.z+b.z);
    }
    public void Subtract(Vector3 vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
    }
    public static Vector3 Subtract(Vector3 a, Vector3 b) {
        return new Vector3(a.x-b.x, a.y-b.y, a.z-b.z);
    }
    public static Vector3 Cross(Vector3 a, Vector3 b) {
        float x = a.y * b.z - a.z * b.y;
        float y = a.z * b.x - a.x * b.z;
        float z = a.x * b.y - a.y * b.x;
        return new Vector3(x, y, z);
    }
    public void Normalize() {
        float length = (float) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }
    public static Vector3 Normalize(Vector3 vec) {
        double length = Math.sqrt(vec.x*vec.x + vec.y*vec.y + vec.z*vec.z);
        vec.x /= length;
        vec.y /= length;
        vec.z /= length;
        return new Vector3(vec.x, vec.y, vec.z);
    }
    public float DotProduct(Vector3 vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }
    public Vector3 Clone() {
        return new Vector3(this.x, this.y, this.z);
    }
    public static Vector3 Multiply(Vector3 a, Vector3 b) {
        return new Vector3(a.x*b.x, a.y*b.y, a.z*b.z);
    }
    public static Vector3 Multiply(Vector3 a, float b) {
        return new Vector3(a.x*b, a.y*b, a.z*b);
    }
    public static Matrix4 PointMatrix4(Vector3 position, Vector3 target, Vector3 up) {
        // New forward
        Vector3 newForward = Subtract(target, position);
        newForward.Normalize();
        // New up
        Vector3 temp = Multiply(newForward, up.DotProduct(newForward));
        Vector3 newUp = Subtract(up, temp);
        newUp.Normalize();
        // New right
        Vector3 newRight = Cross(newUp, newForward);
        // Translation
        Matrix4 m = new Matrix4();
        m.matrix[0][0] = newRight.x; m.matrix[0][1] = newRight.y; m.matrix[0][2] = newRight.z; m.matrix[0][3] = 0.0f;
        m.matrix[1][0] = newUp.x; m.matrix[1][1] = newUp.y; m.matrix[1][2] = newUp.z; m.matrix[1][3] = 0.0f;
        m.matrix[2][0] = newForward.x; m.matrix[2][1] = newForward.y; m.matrix[2][2] = newForward.z; m.matrix[2][3] = 0.0f;
        m.matrix[3][0] = position.x; m.matrix[3][1] = position.y; m.matrix[3][2] = position.z; m.matrix[3][3] = 1.0f;
        return m;
    }
    public static Matrix4 LookAt(Vector3 position, Vector3 target, Vector3 up) {
        Matrix4 n = PointMatrix4(position, target, up);
        Matrix4 m = new Matrix4();
        m.matrix[0][0] = n.matrix[0][0]; m.matrix[0][1] = n.matrix[1][0]; m.matrix[0][2] = n.matrix[2][0]; m.matrix[0][3] = 0.0f;
        m.matrix[1][0] = n.matrix[0][1]; m.matrix[1][1] = n.matrix[1][1]; m.matrix[1][2] = n.matrix[2][1]; m.matrix[1][3] = 0.0f;
        m.matrix[2][0] = n.matrix[0][2]; m.matrix[2][1] = n.matrix[1][2]; m.matrix[2][2] = n.matrix[2][2]; m.matrix[2][3] = 0.0f;
        m.matrix[3][0] = position.x; m.matrix[3][1] = position.y; m.matrix[3][2] = position.z; m.matrix[3][3] = 1.0f;
        return m;
    }
}
