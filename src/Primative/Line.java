/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primative;

public class Line {
    Vector3 head;
    Vector3 tail;
    public Line() {
        this.head = new Vector3();
        this.tail = new Vector3(0.0f, 1.0f, 0.0f);
    }
    public Line(Vector3 head, Vector3 tail) {
        this.head = head;
        this.tail = tail;
    }
}
