package com.Shaders;

/**
 * Created by Sergey on 5/16/2017.
 */
public class StaticShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/com/Shaders/vertex_shader.txt";
    private static final String FRAGMENT_FILE = "src/com/Shaders/fragment_shader.txt";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bind_attributes() {
        bind_attribute(0, "position");
    }
}
