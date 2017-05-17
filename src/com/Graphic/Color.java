package com.Graphic;

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Created by Sergey on 5/17/2017.
 */
public class Color {
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED   = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE  = new Color(0, 0, 1);

    private float _r;
    private float _g;
    private float _b;
    private float _a;

    public Color() {
        this(0, 0, 0);
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 1);
    }

    public Color(float r, float g, float b, float a) {
    }

    public Color(int r, int g, int b, int a) {
    }

    public float get_r() {
        return _r;
    }

    public float get_g() {
        return _g;
    }

    public float get_b() {
        return _b;
    }

    public float get_a() {
        return _a;
    }

    public void set_r(float r) {
        if (r < 0f) _r = 0f;
        else if (r > 1f) _r = 1f;
        else _r = r;
    }

    public void set_g(float g) {
        if (g < 0f) _g = 0f;
        else if (g > 1f) _g = 1f;
        else _g = g;
    }

    public void set_b(float b) {
        if (b < 0f) _b = 0f;
        else if (b > 1f) _b = 1f;
        else _b = b;
    }

    public void set_a(float a) {
        if (a < 0f) _a = 0f;
        else if (a > 1f) _a = 1f;
        else _a = a;
    }

    public void set_r(int r) {
        set_r(r / 255f);
    }

    public void set_g(int g) {
        set_g(g / 255f);
    }

    public void set_b(int b) {
        set_b(b / 255f);
    }

    public void set_a(int a) {
        set_a(a /255f);
    }

    public Vector3f to_vector3f() {
        return new Vector3f(_r, _g, _b);
    }

    public Vector4f to_vector4f() {
        return new Vector4f(_r, _g, _b, _a);
    }
}
