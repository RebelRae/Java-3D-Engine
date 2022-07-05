/* ========================================================================== *
 | = TITLE : Java 3D Engine ================================================= |
 | = VERSION : 0.2.1 ======================================================== |
 | = AUTHOR : Rebel Rae Brown =============================================== |
 | = DATE : July 05, 2022 =================================================== |
 * ========================================================================== */
package src;

public class Debug {
    public static boolean output = true;

    public static void log(String string) {
        if(output) System.out.println(String.format("\033[37;1m LOG :\033[0m %s", string));
    }
    public static void error(String string) {
        if(output) System.out.println(String.format("\033[31;1m ERROR :\033[0m %s", string));
    }
    public static void info(String string) {
        if(output) System.out.println(String.format("\033[36;1m INFO :\033[0m %s", string));
    }
    public static void success(String string) {
        if(output) System.out.println(String.format("\033[32;1m SUCCESS :\033[0m %s", string));
    }
    public static void warn(String string) {
        if(output) System.out.println(String.format("\033[33;1m WARNING :\033[0m %s", string));
    }

    public static void log(String string, boolean force) {
        if(output || force) System.out.println(String.format("\033[37;1m LOG :\033[0m %s", string));
    }
    public static void error(String string, boolean force) {
        if(output || force) System.out.println(String.format("\033[31;1m ERROR :\033[0m %s", string));
    }
    public static void info(String string, boolean force) {
        if(output || force) System.out.println(String.format("\033[36;1m INFO :\033[0m %s", string));
    }
    public static void success(String string, boolean force) {
        if(output || force) System.out.println(String.format("\033[32;1m SUCCESS :\033[0m %s", string));
    }
    public static void warn(String string, boolean force) {
        if(output || force) System.out.println(String.format("\033[33;1m WARNING :\033[0m %s", string));
    }

    public static void log(int string) {
        if(output) System.out.println(String.format("\033[37;1m LOG :\033[0m %s", string));
    }
    public static void error(int string) {
        if(output) System.out.println(String.format("\033[31;1m ERROR :\033[0m %s", string));
    }
    public static void info(int string) {
        if(output) System.out.println(String.format("\033[36;1m INFO :\033[0m %s", string));
    }
    public static void success(int string) {
        if(output) System.out.println(String.format("\033[32;1m SUCCESS :\033[0m %s", string));
    }
    public static void warn(int string) {
        if(output) System.out.println(String.format("\033[33;1m WARNING :\033[0m %s", string));
    }

    public static void log(int string, boolean force) {
        if(output || force) System.out.println(String.format("\033[37;1m LOG :\033[0m %s", string));
    }
    public static void error(int string, boolean force) {
        if(output || force) System.out.println(String.format("\033[31;1m ERROR :\033[0m %s", string));
    }
    public static void info(int string, boolean force) {
        if(output || force) System.out.println(String.format("\033[36;1m INFO :\033[0m %s", string));
    }
    public static void success(int string, boolean force) {
        if(output || force) System.out.println(String.format("\033[32;1m SUCCESS :\033[0m %s", string));
    }
    public static void warn(int string, boolean force) {
        if(output || force) System.out.println(String.format("\033[33;1m WARNING :\033[0m %s", string));
    }
}
