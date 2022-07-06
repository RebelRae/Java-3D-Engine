package src.Object;

import java.awt.Color;

public class Light extends SceneObject {
    public static enum LightType {
        POLARIZED,
        POINT,
        SURFACE
    }
    private float intensity = 10.0f;
    private LightType lightType = LightType.POLARIZED;
    private Color color = Color.WHITE;

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    public LightType getLightType() { return lightType; }
    public void setLightType(LightType lightType) { this.lightType = lightType; }
    public float getIntensity() { return intensity; }
    public void setIntensity(float intensity) { this.intensity = intensity; }
}
