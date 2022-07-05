/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Helper;

import src.Primative.Vector2;
import src.Primative.Vector3;

public class Maths {
    public static int Lerp(int a, int b, float t) {
        float fA = (float) a;
        float fB = (float) b;
        return Math.round(fA + (fB - fA) * t);
    }
    public static float Lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
    public static double Lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }
    public static Vector2 Lerp(Vector2 a, Vector2 b, float t) {
        return new Vector2(
            (a.x + (b.x - a.x) * t),
            (a.y + (b.y - a.y) * t)
        );
    }
    public static Vector3 Lerp(Vector3 a, Vector3 b, float t) {
        return new Vector3(
            (a.x + (b.x - a.x) * t),
            (a.y + (b.y - a.y) * t),
            (a.z + (b.z - a.z) * t)
        );
    }
    public static float Remap(float[] rangeA, float[] rangeB, float value) {
        return rangeB[0] + ((rangeB[1] - rangeB[0]) / (rangeA[1] - rangeA[0])) * (value - rangeA[0]);
    }
    public static float Clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
