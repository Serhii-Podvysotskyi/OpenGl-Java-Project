package com.Graphic;

import com.Core.toDelete;

import org.joml.*;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;

/**
 * Created by Sergey on 5/17/2017.
 */
public class ShaderProgram implements toDelete {
    private final int _id;

    public ShaderProgram() {
        _id = GL20.glCreateProgram();
    }

    public void attach_shader(Shader shader) {
        GL20.glAttachShader(_id, shader.get_id());
    }

    public void bind_fragment_data_location(int number, CharSequence name) {
        GL30.glBindFragDataLocation(_id, number, name);
    }

    public int get_attribute_location(CharSequence name) {
        return GL20.glGetAttribLocation(_id, name);
    }

    public void enable_vertex_attribute(int location) {
        GL20.glEnableVertexAttribArray(location);
    }

    public void disable_vertex_attribute(int location) {
        GL20.glDisableVertexAttribArray(location);
    }

    public void point_vertex_attribute(int location, int size, int stride, int offset) {
        GL20.glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
    }

    public void point_vertex_attribute(int location, int size, int stride) {
        point_vertex_attribute(location, size, stride, 0);
    }

    public void point_vertex_attribute(int location, int size) {
        point_vertex_attribute(location, size, 0, 0);
    }

    public int get_uniform_location(CharSequence name) {
        return GL20.glGetUniformLocation(_id, name);
    }

    public void set_uniform(int location, int value) {
        GL20.glUniform1i(location, value);
    }

    public void set_uniform(CharSequence name, int value) {
        set_uniform(get_uniform_location(name), value);
    }

    public void set_uniform(int location, Vector2f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(2);
            buffer.put(value.x);
            buffer.put(value.y);
            buffer.flip();
            GL20.glUniform2fv(location, buffer);
        }
    }

    public void set_uniform(CharSequence name, Vector2f value) {
        set_uniform(get_uniform_location(name), value);
    }

    public void set_uniform(int location, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(3);
            buffer.put(value.x);
            buffer.put(value.y);
            buffer.put(value.z);
            buffer.flip();
            GL20.glUniform3fv(location, buffer);
        }
    }

    public void set_uniform(CharSequence name, Vector3f value) {
        set_uniform(get_uniform_location(name), value);
    }

    public void set_uniform(int location, Vector4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            buffer.put(value.x);
            buffer.put(value.y);
            buffer.put(value.z);
            buffer.put(value.w);
            buffer.flip();
            GL20.glUniform4fv(location, buffer);
        }
    }

    public void set_uniform(CharSequence name, Vector4f value) {
        set_uniform(get_uniform_location(name), value);
    }

    public void set_uniform(int location, Matrix3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(3 * 3);
            buffer.put(value.m00()).put(value.m10()).put(value.m20());
            buffer.put(value.m01()).put(value.m11()).put(value.m21());
            buffer.put(value.m02()).put(value.m12()).put(value.m22());
            buffer.flip();
            GL20.glUniformMatrix3fv(location, false, buffer);
        }
    }

    public void set_uniform(CharSequence name, Matrix3f value) {
        set_uniform(get_uniform_location(name), value);
    }

    public void set_uniform(int location, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(3 * 3);
            buffer.put(value.m00()).put(value.m10()).put(value.m20()).put(value.m30());
            buffer.put(value.m01()).put(value.m11()).put(value.m21()).put(value.m31());
            buffer.put(value.m02()).put(value.m12()).put(value.m22()).put(value.m32());
            buffer.put(value.m03()).put(value.m13()).put(value.m23()).put(value.m33());
            buffer.flip();
            GL20.glUniformMatrix4fv(location, false, buffer);
        }
    }

    public void set_uniform(CharSequence name, Matrix4f value) {
        set_uniform(get_uniform_location(name), value);
    }


    public void use() {
        GL20.glUseProgram(_id);
    }

    public void link() {
        GL20.glLinkProgram(_id);
        check_status();
    }

    private void check_status() {
        int status = GL20.glGetProgrami(_id, GL_LINK_STATUS);
        if(status != GL_TRUE) {
            throw new RuntimeException(GL20.glGetProgramInfoLog(_id));
        }
    }

    @Override
    public void Delete() {
        GL20.glDeleteProgram(_id);
    }
}
