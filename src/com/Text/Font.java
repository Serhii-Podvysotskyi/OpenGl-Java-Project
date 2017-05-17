package com.Text;

import com.Graphic.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static java.awt.Font.TRUETYPE_FONT;

/**
 * Created by Sergey on 5/17/2017.
 */
public class Font {
    private final Map<Character, Glyph> _glyphs;

    private final Texture _texture;

    private int _height;

    public Font() {
        this(new java.awt.Font(MONOSPACED, PLAIN, 16), true);
    }

    public Font(boolean anti_alias) {
        this(new java.awt.Font(MONOSPACED, PLAIN, 16), anti_alias);
    }

    public Font(int size) {
        this(new java.awt.Font(MONOSPACED, PLAIN, size), true);
    }

    public Font(int size, boolean anti_alias) {
        this(new java.awt.Font(MONOSPACED, PLAIN, size), anti_alias);
    }

    public Font(InputStream input_stream, int size) throws FontFormatException, IOException {
        this(input_stream, size, true);
    }

    public Font(InputStream input_stream, int size, boolean antiAlias) throws FontFormatException, IOException {
        this(java.awt.Font.createFont(TRUETYPE_FONT, input_stream).deriveFont(PLAIN, size), antiAlias);
    }

    public Font(java.awt.Font font) {
        this(font, true);
    }

    public Font(java.awt.Font font, boolean anti_alias) {
        _glyphs = new HashMap<>();
        _texture = create_font_texture(font, anti_alias);
    }

    private Texture create_font_texture(java.awt.Font font, boolean anti_alias) {
        int width = 0;
        int height = 0;
        for (int i = 32; i < 256; i++) {
            if (i == 127) continue;
            char c = (char)i;
            BufferedImage image = create_char_image(font, c, anti_alias);
            if (image == null) continue;
            width += image.getWidth();
            height = Math.max(height, image.getHeight());
        }
        _height = height;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int x = 0;
        //TODO
    }
}
