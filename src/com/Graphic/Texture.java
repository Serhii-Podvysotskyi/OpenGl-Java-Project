package com.Graphic;

import com.Core.toDelete;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_NEAREST;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

/**
 * Created by Sergey on 5/17/2017.
 */
public class Texture implements toDelete {
    private final int _id;
    private int _width;
    private int _height;

    public static Texture Create_texture(int width, int height, ByteBuffer data) {
        Texture texture = new Texture();
        texture.set_width(width);
        texture.set_height(height);
        texture.bind();
        texture.set_parameter(GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
        texture.set_parameter(GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
        texture.set_parameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.set_parameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        texture.upload_data(width, height, data);
        return texture;
    }

    public static Texture Load_texture(String path) {
        ByteBuffer data;
        int width;
        int height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.callocInt(1);
            stbi_set_flip_vertically_on_load(true);
            data = stbi_load("res/" + path, w, h, c, 4);
            if (data == null) {
                throw new RuntimeException("Fail to load texture file" + System.lineSeparator() + stbi_failure_reason());
            }
            width = w.get();
            height = w.get();
        }
        return Create_texture(width, height, data);
    }

    protected Texture() {
        _id = GL11.glGenTextures();
    }

    public void bind() {
        GL11.glBindTexture(GL_TEXTURE_2D, _id);
    }

    public void set_parameter(int name, int value) {
        GL11.glTexParameteri(GL_TEXTURE_2D, name, value);
    }

    public void upload_data(int width, int height, ByteBuffer data) {
        upload_data(GL_RGBA8, width, height, GL_RGBA, data);
    }

    private void upload_data(int internal_format, int width, int height, int format, ByteBuffer data) {
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, internal_format, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

    public int get_id() {
        return _id;
    }

    public void set_width(int width) {
        if (width > 0) {
            _width = width;
        }
    }

    public int get_width() {
        return _width;
    }

    public void set_height(int height) {
        if (height > 0) {
            _height = height;
        }
    }

    public int get_height() {
        return _height;
    }

    @Override
    public void Delete() {
        GL11.glDeleteTextures(_id);
    }
}
