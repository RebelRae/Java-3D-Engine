/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;

import src.Primative.Matrix4;
import src.Primative.Mesh;
import src.Primative.Triangle;
import src.Primative.Vector3;
import src.Helper.Maths;

public class Engine extends Canvas implements Runnable {
    // ======================== Static Variables ======================== //
    private static final int v_MAJOR = 0, v_MINOR = 2, v_PATCH = 1;
    private static final String TITLE = "Grafix3d Engine";
    private static final int FPS = 30;
    private static final int UPS = 120;
    
    private static final int WIDTH = 640;
    private static final int HEIGHT = 360;
    private static final float ASPECT_RATIO = (float)HEIGHT / (float)WIDTH;

    private static final float F_NEAR = 0.1f;
    private static final float F_FAR = 1000.0f;
    private static final float FOV = 90.0f;
    private static final float F_TANGENT = 1.0f / (float) Math.tan((Double) (FOV * 0.5f / 180.0f * Math.PI));

    private static double TIME;
    private static double DELTA_UPDATE;
    private static double DELTA_FRAME;

    // ======================= Software Variables ======================= //
    private BufferedImage BG_IMAGE;
    private int[] BACKGROUND;
    private boolean RUNNING = false;

    public static String OBJ_STRING = "No file loaded";
    private static boolean showWireframe = false;
    private static Mesh mesh;
    private static Matrix4 projectionMatrix;
    private static Matrix4 xRotationMatrix;
    private static Matrix4 zRotationMatrix;
    private static Matrix4 yRotationMatrix;
    private static Vector3 mainCamera;
    private static Vector3 mainLight;
    
    public Engine() {
        Debug.output = false;
        BG_IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        BACKGROUND = ((DataBufferInt)BG_IMAGE.getRaster().getDataBuffer()).getData();
    }

    private void start() {
        if(RUNNING) return;
        RUNNING = true;
        run();
    }
    // =========================== Game Loop ============================ //
    public void run() {
        long startTime = System.nanoTime();
        final double updateTime = 1000000000 / UPS;
        final double frameTime = 1000000000 / FPS;
        double deltaUpdate = 0;
        double deltaFrame = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while (RUNNING) {
            long currentTime = System.nanoTime();
            deltaUpdate += (currentTime - startTime) / updateTime;
            deltaFrame += (currentTime - startTime) / frameTime;
            startTime = currentTime;
            if (deltaUpdate >= 1) {
                input();
                tick();
                ticks++;
                deltaUpdate--;
                DELTA_UPDATE += 0.01;
            }
            if (deltaFrame >= 1) {
                render();
                frames++;
                deltaFrame--;
                DELTA_FRAME++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                Debug.info(String.format("UPS: %s, FPS: %s", ticks, frames));
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
    }
    // -------------------------- Update Input -------------------------- //
    public void input() {}
    // -------------------------- Update Logic -------------------------- //
    public void tick() {}
    // ---------------------------- Graphics ---------------------------- //
    static Font font;
    public void render() {
        // Background Draw
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        drawBackgroundPixels();
        Graphics graphics = bs.getDrawGraphics();
        graphics.drawImage(BG_IMAGE, 0, 0, WIDTH, HEIGHT, null);
        // HUD / Overlay
        graphics.setColor(Color.GREEN);
        graphics.setFont(font.deriveFont(10.0f));
        graphics.drawString("Object: " + OBJ_STRING, 10, 30);

        // Rotate X
        xRotationMatrix.matrix[0][0] = 1.0f;
        xRotationMatrix.matrix[1][1] = (float) Math.cos(DELTA_UPDATE * mesh.xRotSpeed);
        xRotationMatrix.matrix[1][2] = (float) Math.sin(DELTA_UPDATE * mesh.xRotSpeed);
        xRotationMatrix.matrix[2][1] = (float) -Math.sin(DELTA_UPDATE * mesh.xRotSpeed);
        xRotationMatrix.matrix[2][2] = (float) Math.cos(DELTA_UPDATE * mesh.xRotSpeed);
        // Rotate Y
        yRotationMatrix.matrix[0][0] = (float) Math.cos(DELTA_UPDATE * mesh.yRotSpeed);
        yRotationMatrix.matrix[1][1] = 1.0f;
        yRotationMatrix.matrix[0][2] = (float) -Math.sin(DELTA_UPDATE * mesh.yRotSpeed);
        yRotationMatrix.matrix[2][0] = (float) Math.sin(DELTA_UPDATE * mesh.yRotSpeed);
        yRotationMatrix.matrix[2][2] = (float) Math.cos(DELTA_UPDATE * mesh.yRotSpeed);
        // Rotate Z
        zRotationMatrix.matrix[0][0] = (float) Math.cos(DELTA_UPDATE * mesh.zRotSpeed);
        zRotationMatrix.matrix[0][1] = (float) Math.sin(DELTA_UPDATE * mesh.zRotSpeed);
        zRotationMatrix.matrix[1][0] = (float) -Math.sin(DELTA_UPDATE * mesh.zRotSpeed);
        zRotationMatrix.matrix[1][1] = (float) Math.cos(DELTA_UPDATE * mesh.zRotSpeed);
        zRotationMatrix.matrix[2][2] = 1.0f;

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
            Vector3 position = new Vector3(0.0f, 0.0f, 15.0f);
            triangle.vertices[0] = Vector3.Add(triangle.vertices[0], position);
            triangle.vertices[1] = Vector3.Add(triangle.vertices[1], position);
            triangle.vertices[2] = Vector3.Add(triangle.vertices[2], position);
            // Calculate Normals
            Vector3 lineA = Vector3.Subtract(triangle.vertices[1], triangle.vertices[0]);
            Vector3 lineB = Vector3.Subtract(triangle.vertices[2], triangle.vertices[0]);
            Vector3 normal = Vector3.Cross(lineA, lineB);
            Vector3 n = Vector3.Normalize(normal);
            // Backface Culling from camera
            if(n.DotProduct(Vector3.Subtract(triangle.vertices[0], mainCamera)) < 0.0f) {
                // Illuminate
                Vector3 lightDirection = mainLight.Clone();
                lightDirection.Normalize();
                float dot = normal.DotProduct(lightDirection);
                Color color = illuminate(Color.GRAY, dot);
                // Projection
                triangle.vertices[0] = projectionMatrix.MultiplyMatrixVector(triangle.vertices[0]);
                triangle.vertices[1] = projectionMatrix.MultiplyMatrixVector(triangle.vertices[1]);
                triangle.vertices[2] = projectionMatrix.MultiplyMatrixVector(triangle.vertices[2]);
                // Scaling and Normalizing
                triangle.vertices[0].x += 1.0f; triangle.vertices[0].y += 1.0f;
                triangle.vertices[1].x += 1.0f; triangle.vertices[1].y += 1.0f;
                triangle.vertices[2].x += 1.0f; triangle.vertices[2].y += 1.0f;
                triangle.vertices[0].x *= 0.5f * WIDTH; triangle.vertices[0].y *= 0.5f * HEIGHT;
                triangle.vertices[1].x *= 0.5f * WIDTH; triangle.vertices[1].y *= 0.5f * HEIGHT;
                triangle.vertices[2].x *= 0.5f * WIDTH; triangle.vertices[2].y *= 0.5f * HEIGHT;
                // Draw Triangles
                graphics.setColor(color);
                graphics.fillPolygon(new int[] {
                    (int)triangle.vertices[0].x, (int)triangle.vertices[1].x, (int)triangle.vertices[2].x
                }, new int[] {
                    (int)triangle.vertices[0].y, (int)triangle.vertices[1].y, (int)triangle.vertices[2].y
                }, 3);
                // Draw Wireframe
                if(showWireframe) {
                    graphics.setColor(Color.GREEN);
                    graphics.drawPolygon(new int[] {
                        (int)triangle.vertices[0].x, (int)triangle.vertices[1].x, (int)triangle.vertices[2].x
                    }, new int[] {
                        (int)triangle.vertices[0].y, (int)triangle.vertices[1].y, (int)triangle.vertices[2].y
                    }, 3);
                }
            }
        }
        // Render
        graphics.dispose();
        bs.show();
    }
    private float[] rangeNorm = {-1.0f, 1.0f};
    private float[] rangeFloor = {0.0f, 1.0f};
    private float[] range255 = {0.0f, 255.0f};
    private Color illuminate(Color color, float lum) {
        int luminance = (int) Maths.Remap(rangeNorm, range255, lum);
        luminance = luminance < 0? 0 : luminance > 255? 255: luminance;
        int r = color.getRed() * luminance / 255;
        int g = color.getGreen() * luminance / 255;
        int b = color.getBlue() * luminance / 255;
        return new Color(r, g, b);
    }
    public void drawBackgroundPixels() {
        Color top = Color.BLACK;
        Color bottom = Color.WHITE;
        float[] range = {0.0f, (float)WIDTH*HEIGHT};
        for (int i = 0; i < WIDTH*HEIGHT; i++) {
            float fade = Maths.Remap(range, rangeFloor, i);
            int r = Maths.Lerp(top.getRed(), bottom.getRed(), fade * 0.7f);
            int g = Maths.Lerp(top.getGreen(), bottom.getGreen(), fade * 0.7f);
            int b = Maths.Lerp(top.getBlue(), bottom.getBlue(), fade);
            BACKGROUND[i] = r << 16 | g << 8 | b;
        }
    }

    public static void main(String[] args) {
        // Preload
        mesh = Mesh.FromFile("./res/objects/Pen_bushing.obj");

        mesh.xRotSpeed = 0.5f;
        mesh.yRotSpeed = 0.0f;
        mesh.zRotSpeed = 1.0f;
        // Matrices
        projectionMatrix = new Matrix4();
        projectionMatrix.matrix[0][0] = ASPECT_RATIO * F_TANGENT;
        projectionMatrix.matrix[1][1] = F_TANGENT;
        projectionMatrix.matrix[2][2] = F_FAR / (F_FAR - F_NEAR);
        projectionMatrix.matrix[3][2] = (-F_FAR * F_NEAR) / (F_FAR - F_NEAR);
        projectionMatrix.matrix[2][3] = 1.0f;
        projectionMatrix.matrix[3][3] = 0.0f;
        xRotationMatrix = new Matrix4();
        yRotationMatrix = new Matrix4();
        zRotationMatrix = new Matrix4();
        mainCamera = new Vector3();
        mainLight = new Vector3(0.0f, 0.0f, -1.0f);

        String fName = "./../res/fonts/MarkerFelt.ttc";
        InputStream is = Engine.class.getResourceAsStream(fName);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            Debug.error(e.toString(), true);
            e.printStackTrace();
        } catch (IOException e) {
            Debug.error(e.toString(), true);
            e.printStackTrace();
        }
        // Init
        Debug.success(String.format("Launching %s Version: %d.%d.%d", TITLE, v_MAJOR, v_MINOR, v_PATCH), true);
        Debug.success("Width: " + WIDTH + " Height: " + HEIGHT, true);
        // BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
        Engine display = new Engine();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.setTitle(String.format("%s - %d.%d.%d", TITLE, v_MAJOR, v_MINOR, v_PATCH));
        frame.pack();
        // frame.getContentPane().setCursor(blank);
        frame.setResizable(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        display.start();
    }
}
