package com.Text;

/**
 * Created by Sergey on 5/17/2017.
 */
public class Glyph {
    private final int _width;
    private final int _height;
    private final int _x;
    private final int _y;
    private final float _advance;

    public Glyph(int width, int height, int x, int y, float advance) {
        _width = width;
        _height = height;
        _x = x;
        _y = y;
        _advance = advance;
    }

    public int get_width() {
        return _width;
    }

    public int get_height() {
        return _height;
    }

    public int get_x() {
        return _x;
    }

    public int get_y() {
        return _y;
    }

    public float get_advance() {
        return _advance;
    }
}
