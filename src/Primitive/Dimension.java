package src.Primitive;

public class Dimension {
    private int width;
    private int height;
    
    public Dimension() {
        this.width = 0;
        this.height = 0;
    }
    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public float AspectRatio() { return (float) this.height / this.width; }
}
