package com.Graphic;

import com.Core.toDelete;
import org.lwjgl.opengl.GL20;

import java.io.*;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

/**
 * Created by Sergey on 5/17/2017.
 */
public class Shader implements toDelete {
    private final int _id;

    public static Shader Create_vertex_shader(CharSequence source) {
        return Create_shader(GL_VERTEX_SHADER, source);
    }

    public static Shader Create_fragment_shader(CharSequence source) {
        return Create_shader(GL_FRAGMENT_SHADER, source);
    }

    public static Shader Create_shader(int type, CharSequence source) {
        Shader shader = new Shader(type);
        shader.source(source);
        shader.compile();
        return shader;
    }

    public static Shader Load_vertex_shader(String path) {
        return Load_shader(GL_VERTEX_SHADER, path);
    }

    public static Shader Load_fragment_shader(String path) {
        return Load_shader(GL_FRAGMENT_SHADER, path);
    }

    public static Shader Load_shader(int type, String path) {
        StringBuilder builder = new StringBuilder();
        try (InputStream input_stream = new FileInputStream("res/" + path)) {
            InputStreamReader stream_reader = new InputStreamReader(input_stream);
            BufferedReader buffered_reader = new BufferedReader(stream_reader);
            String line = buffered_reader.readLine();
            while (line != null) {
                builder.append(line).append("\n");
                line = buffered_reader.readLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load shader file" + System.lineSeparator() + ex.getMessage());
        }
        CharSequence source = builder.toString();
        return Create_shader(type, source);
    }

    protected Shader(int type) {
        _id = GL20.glCreateShader(type);
    }

    public void source(CharSequence source) {
        GL20.glShaderSource(_id, source);
    }

    public void compile() {
        GL20.glCompileShader(_id);
        check_status();
    }

    private void check_status() {
        int status = GL20.glGetShaderi(_id, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(GL20.glGetShaderInfoLog(_id));
        }
    }

    public int get_id() {
        return _id;
    }

    @Override
    public void Delete() {
       GL20.glDeleteShader(_id);
    }
}
