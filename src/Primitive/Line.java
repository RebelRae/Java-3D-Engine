/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primitive;

import java.awt.Color;

public class Line implements Comparable<Line> {
    public Vector3 head;
    public Vector3 tail;
    public Color color = Color.PINK;
    public Line() {
        this.head = new Vector3();
        this.tail = new Vector3(0.0f, 1.0f, 0.0f);
    }
    public Line(Vector3 head, Vector3 tail) {
        this.head = head;
        this.tail = tail;
    }
    public float zMid() {
        return (this.head.z + this.tail.z) / 2.0f;
    }
    @Override
    public int compareTo(Line l) {
        if(this.zMid() < l.zMid()) return 1;
        if(this.zMid() > l.zMid()) return -1;
        return 0;
    }
}
