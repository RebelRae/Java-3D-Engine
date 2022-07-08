/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src;

import java.util.Properties;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import src.Helper.Maths;
import src.Object.Cube;
import src.Object.SceneObject;
import src.Primitive.Mesh;
import src.Primitive.Vector3;

public class Engine extends Canvas implements Runnable {
    // ======================== Static Variables ======================== //
    private static final int v_MAJOR = 0, v_MINOR = 2, v_PATCH = 1;
    private static final String TITLE = "Grafix3d Engine";
    private static final int FPS = 10; // TODO : 30
    private static final int UPS = 120;
    
    private static final int WIDTH = 640;
    private static final int HEIGHT = 360;

    private static double TIME;
    private static double DELTA_UPDATE;
    private static double DELTA_FRAME;

    // ======================= Software Variables ======================= //
    private BufferedImage BG_IMAGE;
    private int[] BACKGROUND;
    private boolean RUNNING = false;

    public static String OBJ_STRING = "No file loaded";

    private static Scene scene = new Scene();
    private InputHandler inputHandler;
    
    public Engine() {
        Debug.output = false;
        BG_IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        BACKGROUND = ((DataBufferInt)BG_IMAGE.getRaster().getDataBuffer()).getData();
        inputHandler = new InputHandler();
        addKeyListener(inputHandler);
        addFocusListener(inputHandler);
        addMouseListener(inputHandler);
        addMouseMotionListener(inputHandler);
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
    public void input() {
        if(inputHandler.key[KeyEvent.VK_CONTROL]) {
            if(inputHandler.key[KeyEvent.VK_N]) MenuBar.NewFile();
        }
    }
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
        // graphics.setColor(Color.GREEN);
        // graphics.setFont(font.deriveFont(10.0f));
        // graphics.drawString("Object: " + OBJ_STRING, 10, 30);
        // Scene
        scene.render(DELTA_UPDATE, graphics);

        graphics.dispose();
        bs.show();
    }
    public void drawBackgroundPixels() {
        Color top = Color.BLACK;
        Color bottom = Color.WHITE;
        float[] range = {0.0f, (float)WIDTH*HEIGHT};
        for (int i = 0; i < WIDTH*HEIGHT; i++) {
            float fade = Maths.Remap(range, Maths.rangeFloor, i);
            int r = Maths.Lerp(top.getRed(), bottom.getRed(), fade * 0.7f);
            int g = Maths.Lerp(top.getGreen(), bottom.getGreen(), fade * 0.7f);
            int b = Maths.Lerp(top.getBlue(), bottom.getBlue(), fade);
            BACKGROUND[i] = r << 16 | g << 8 | b;
        }
    }

    public static void main(String[] args) {
        Properties props = System.getProperties();
        props.setProperty("apple.awt.application.appearance", "system");
        // Preload
        Cube cube = new Cube();
        cube.Mesh().xRotSpeed = 0.4f;
        cube.Mesh().zRotSpeed = -0.7f;
        cube.setPosition(new Vector3(2.0f, -0.1045f, 4.0f));
        cube.Mesh().setColor(Color.CYAN);
        scene.AddObject(cube);

        // Init
        Debug.success(String.format("Launching %s Version: %d.%d.%d", TITLE, v_MAJOR, v_MINOR, v_PATCH), true);
        Debug.success("Width: " + WIDTH + " Height: " + HEIGHT, true);
        JFrame frame = new JFrame();
        Engine engine = new Engine();
        MenuBar menuBar = new MenuBar();

        frame.add(engine);
        frame.setJMenuBar(menuBar);
        frame.setTitle(String.format("%s - %d.%d.%d", TITLE, v_MAJOR, v_MINOR, v_PATCH));
        frame.pack();

        // BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
        // frame.getContentPane().setCursor(blank);

        frame.setResizable(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        engine.start();
    }

    public static void LoadFile(String filePath) {
        Mesh mesh = Mesh.FromFile(filePath);
        mesh.xRotSpeed = 0.25f;
        mesh.yRotSpeed = 0.0f;
        mesh.zRotSpeed = 0.5f;
        SceneObject meshObject = new SceneObject();
        meshObject.Mesh(mesh);
        meshObject.setPosition(new Vector3(0.0f, 3.0f, 35.0f));
        scene.AddObject(meshObject);
    }
}
