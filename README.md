# **Java-3D-Engine**

### A basic 3D renderiing engine from scratch
---
## **Complete:**
### * Buffered scene background
### * Camera plug
### * Matrix transformers
### * Scene lighting
### * Maths helpers
### * Primative geometry types
### * Triangulated .obj file loader
---
## **Coming Next:**
### * Scene helpers
### * Controls
### * Basic mesh types
### * Menus
### * Textures
---
## **Coming Eventually:**
### * Normal tools
### * Vector tools
### * Basic Editor
### * Animation tools
### * Advanced menu system
### * HUD
### * Rigging
### * Scripting
---
## _`Helper.Maths` functions_
## Lerp
`Lerp()` overloads for `int`, `float`, `double`, `Vector2`, and `Vector3` types. Returns an interpolated value between `a` and `b` based on factor `t`

```
18 |    public static float Lerp(float a, float b, float t) {
19 |        return a + (b - a) * t;
20 |    }
```
> Do NOT use the `int Lerp(a, b, t)` function unless you're positive you want truncated values for particular cases where an int is necessary (like in the `drawBackgroundPixels()` function)
## Remap
`Remap(a[2], b[2], val)` maps a range of values `a[min, max]` into the range `b[min, max]` and returns a value `val` from `a` as it translates to `b`
```
37 |    public static float Remap(float[] rangeA, float[] rangeB, float value) {
38 |        return rangeB[0] + ((rangeB[1] - rangeB[0]) / (rangeA[1] - rangeA[0])) * (value - rangeA[0]);
39 |    }
```
so using the hard ranges from a normalized value `[-1.0, ..., 1.0]` and an 8 bit color value `[0, ..., 255]`, the luminance value is set by the normalized dot product value `lum`
```
private float[] rangeNorm = {-1.0f, 1.0f};
private float[] range255 = {0.0f, 255.0f};
int luminance = (int) Maths.Remap(rangeNorm, range255, lum);
```
## Clamp
`Clamp()` is a destructive method to clamp a `value` between a `min` and `max` value with no scaling or transform
```
40 |    public static float Clamp(float value, float min, float max) {
41 |        return Math.max(min, Math.min(max, value));
42 |    }
```
---
## _`Vector2` class_
A base class that stores 2 `float` values. Defaults to (0.0f, 0.0f)
```
10 |    public float x;
11 |    public float y;
```
---
## _`Vector3` class_
A primative class that stores 3 `float` values. Extends `Vector2`. Includes a number of useful methods. Defaults to (0.0f, 0.0f, 0.0f)
```
10 |    public float x;
11 |    public float y;
13 |    public float z;
```
## Add
`Add(Vector3 vec)` overload directly adds the values of `vec` to the instance that calls it.
```
24 |    public void Add(Vector3 a) {
25 |        this.x += a.x;
26 |        this.y += a.y;
27 |        this.z += a.z;
28 |    }
```
`Add(a, b)` static overload adds the values of `a` and `b` and returns a new `Vector3` instance.
```
29 |    public static Vector3 Add(Vector3 a, Vector3 b) {
30 |        return new Vector3(a.x+b.x, a.y+b.y, a.z+b.z);
31 |    }
```
## Subtract
`Subtract(Vector3 vec)` overload directly subtracts the values of `vec` from the instance that calls it.
```
32 |    public void Subtract(Vector3 vec) {
33 |        this.x -= vec.x;
34 |        this.y -= vec.y;
35 |        this.z -= vec.z;
36 |    }
```
`Subtract(a, b)` static overload subtracts the values of `b` **from** `a` and returns a new `Vector3` instance.
```
37 |    public static Vector3 Subtract(Vector3 a, Vector3 b) {
38 |        return new Vector3(a.x-b.x, a.y-b.y, a.z-b.z);
39 |    }
```
## Cross
`Cross(a, b)`
```
```
## DotProduct
`DotProduct(Vector3 vec)`
```
```
## Normalize
`Normalize()` overload uses hypotenuse length by which to normalize vector values to the range `[-1.0, ..., 1.0]`
```
    public void Normalize() {
        float length = (float) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }
```
## Clone
`Clone()` returns a new `Vector3` instance with identical values to the caller.
```
```
---
## _Load Mesh From File_
```
1| Mesh.FromFile("./res/objects/Pen_bushing.obj");
```