/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src.Primative;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import src.Debug;
import src.Engine;

import java.util.ArrayList;

public class Mesh {
    public ArrayList<Triangle> triangles;
    public float yRotSpeed, xRotSpeed, zRotSpeed;
    public Mesh() {
        this.triangles = new ArrayList<Triangle>();
    }
    public Mesh(ArrayList<Triangle> triangles) {
        this.triangles = triangles;
    }
    public static Mesh FromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner myReader = new Scanner(file);
            ArrayList<Vector3> vertices = new ArrayList<>();
            ArrayList<Triangle> triangles = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] subStrings = data.split("\\s+");
                if(subStrings[0].equals("o")) {
                    Engine.OBJ_STRING = subStrings[1];
                }
                if(subStrings[0].equals("v")) {
                    vertices.add(new Vector3(
                        Float.valueOf(subStrings[1]).floatValue(),
                        Float.valueOf(subStrings[2]).floatValue(),
                        Float.valueOf(subStrings[3]).floatValue()
                    ));
                }
                if(subStrings[0].equals("f")) {
                    triangles.add(new Triangle(
                        vertices.get(Integer.parseInt(subStrings[1])-1),
                        vertices.get(Integer.parseInt(subStrings[2])-1),
                        vertices.get(Integer.parseInt(subStrings[3])-1)
                    ));
                }
            }
            myReader.close();
            return new Mesh(triangles);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            Debug.error("File read error");
            System.exit(1);
        }
        return null;
    }
}
