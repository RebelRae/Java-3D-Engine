/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primitive;

public class Matrix4 {
    public float[][] matrix = new float[4][4];
    public Matrix4() {
        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++)
                matrix[y][x] = 0.0f;
    }
    public Vector3 MultiplyMatrixVector(Vector3 i) {
        Vector3 output = new Vector3();
        output.x = i.x * matrix[0][0] + i.y * matrix[1][0] + i.z * matrix[2][0] + matrix[3][0];
        output.y = i.x * matrix[0][1] + i.y * matrix[1][1] + i.z * matrix[2][1] + matrix[3][1];
        output.z = i.x * matrix[0][2] + i.y * matrix[1][2] + i.z * matrix[2][2] + matrix[3][2];
        float w = i.x * matrix[0][3] + i.y * matrix[1][3] + i.z * matrix[2][3] + matrix[3][3];
        if(w != 0.0f) {
            output.x /= w;
            output.y /= w;
            output.z /= w;
        }
        return output;
    }
    public static Matrix4 RotateMatrixX(float x) {
        Matrix4 xRotationMatrix = new Matrix4();
        xRotationMatrix.matrix[0][0] = 1.0f;
        xRotationMatrix.matrix[1][1] = (float) Math.cos(x);
        xRotationMatrix.matrix[1][2] = (float) Math.sin(x);
        xRotationMatrix.matrix[2][1] = (float) -Math.sin(x);
        xRotationMatrix.matrix[2][2] = (float) Math.cos(x);
        return xRotationMatrix;
    }
    public static Matrix4 RotateMatrixY(float y) {
        Matrix4 yRotationMatrix = new Matrix4();
        yRotationMatrix.matrix[0][0] = (float) Math.cos(y);
        yRotationMatrix.matrix[1][1] = 1.0f;
        yRotationMatrix.matrix[0][2] = (float) -Math.sin(y);
        yRotationMatrix.matrix[2][0] = (float) Math.sin(y);
        yRotationMatrix.matrix[2][2] = (float) Math.cos(y);
        return yRotationMatrix;
    }
    public static Matrix4 RotateMatrixZ(float z) {
        Matrix4 zRotationMatrix = new Matrix4();
        zRotationMatrix.matrix[0][0] = (float) Math.cos(z);
        zRotationMatrix.matrix[0][1] = (float) Math.sin(z);
        zRotationMatrix.matrix[1][0] = (float) -Math.sin(z);
        zRotationMatrix.matrix[1][1] = (float) Math.cos(z);
        zRotationMatrix.matrix[2][2] = 1.0f;
        return zRotationMatrix;
    }
}
