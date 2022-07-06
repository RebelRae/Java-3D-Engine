package src.Helper;

import java.awt.Color;

public class Colors {
    public static Color Illuminate(Color color, float lum) {
        int luminance = (int) Maths.Remap(Maths.rangeNorm, Maths.range255, lum);
        luminance = luminance < 0? 0 : luminance > 255? 255: luminance;
        int r = color.getRed() * luminance / 255;
        int g = color.getGreen() * luminance / 255;
        int b = color.getBlue() * luminance / 255;
        return new Color(r, g, b);
    }
}
