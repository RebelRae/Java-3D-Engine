/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primative;

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
}
