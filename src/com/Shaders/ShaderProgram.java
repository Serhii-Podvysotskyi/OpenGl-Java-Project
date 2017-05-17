package com.Shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Sergey on 5/16/2017.
 */
public abstract class ShaderProgram {
    public enum status {
        DEFAULT,
        SUCCESS,
        VERTEX_SHADER_ERROR,
        FRAGMENT_SHADER_ERROR,
        BINDING_ATTRIBUTES_ERROR
    };

    private int _program_id = 0;
    private int _vertex_shader_id = 0;
    private int _fragment_shader_id = 0;
    private status _status = status.DEFAULT;

    public ShaderProgram(String vertex_file, String fragment_file) {
        try {
            _vertex_shader_id = load_shader(vertex_file, GL20.GL_VERTEX_SHADER);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            _status = status.VERTEX_SHADER_ERROR;
        }
        try {
            _fragment_shader_id = load_shader(fragment_file, GL20.GL_FRAGMENT_SHADER);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            _status = status.FRAGMENT_SHADER_ERROR;
        }
        if (_status == status.DEFAULT) {
            _program_id = GL20.glCreateProgram();
            GL20.glAttachShader(_program_id, _vertex_shader_id);
            GL20.glAttachShader(_program_id, _fragment_shader_id);
            GL20.glLinkProgram(_program_id);
            GL20.glValidateProgram(_program_id);
            try {
                bind_attributes();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                _status = status.BINDING_ATTRIBUTES_ERROR;
            }
            if (_status == status.DEFAULT) {
                _status = status.SUCCESS;
            }
        }
    }

    public void start() {
        if (_status == status.SUCCESS) {
            GL20.glUseProgram(_program_id);
        }
    }

    public void stop() {
        if (_status == status.SUCCESS) {
            GL20.glUseProgram(0);
        }
    }

    public void Realize() {
        GL20.glUseProgram(0);
        if (_program_id != 0) {
            GL20.glDetachShader(_program_id, _vertex_shader_id);
            GL20.glDetachShader(_program_id, _fragment_shader_id);
        }
        if (_vertex_shader_id != 0) {
            GL20.glDeleteShader(_vertex_shader_id);
        }
        if (_fragment_shader_id != 0) {
            GL20.glDeleteShader(_fragment_shader_id);
        }
        if (_program_id != 0) {
            GL20.glDeleteProgram(_program_id);
        }
    }

    protected abstract void bind_attributes();

    protected void bind_attribute(int attribute, String variable_name) {
        GL20.glBindAttribLocation(_program_id, attribute, variable_name);
    }

    private static int load_shader(String file, int type) throws Exception {
        switch (type) {
            case GL20.GL_VERTEX_SHADER:
                file += ".vs";
                break;
            case GL20.GL_FRAGMENT_SHADER:
                file += ".fs";
                break;
        }
        StringBuilder shader_source = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/" + file));
            String line = reader.readLine();
            while (line != null) {
                shader_source.append(line).append("\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception ex) {
            throw new Exception("Couldn't load shader file: " + file);
        }
        int shader_id = GL20.glCreateShader(type);
        GL20.glShaderSource(shader_id, shader_source);
        GL20.glCompileShader(shader_id);
        if (GL20.glGetShaderi(shader_id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new Exception("Couldn't compile shader: " + file);
        }
        return shader_id;
    }

    public status get_status() {
        return _status;
    }

    protected int get_vertex_shader_id() {
        return _vertex_shader_id;
    }

    protected int get_fragment_shader_id() {
        return _fragment_shader_id;
    }

    protected int get_program_id() {
        return _program_id;
    }
}
