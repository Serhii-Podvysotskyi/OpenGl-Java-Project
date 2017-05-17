package com.Shaders;

/**
 * Created by Sergey on 5/17/2017.
 */
public class TextureShader extends ShaderProgram {
    private static final String VERTEX_FILE = "shaders/texture";
    private static final String FRAGMENT_FILE = "shaders/texture";

    public TextureShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bind_attributes() {
        bind_attribute(0, "position");
        bind_attribute(1, "texture");
    }
}
