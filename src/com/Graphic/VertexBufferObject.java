package com.Graphic;

import com.Core.toDelete;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Sergey on 5/17/2017.
 */
public class VertexBufferObject implements toDelete {
    private final int _id;

    public VertexBufferObject() {
        _id = GL15.glGenBuffers();
    }

    public void upload_data(int target, FloatBuffer data, int usage) {
        GL15.glBufferData(target, data, usage);
    }

    public void upload_data(int target, IntBuffer data, int usage) {
        GL15.glBufferData(target, data, usage);
    }

    public void upload_data(int target, long size, int usage) {
        GL15.glBufferData(target, size, usage);
    }

    public int get_id() {
        return  _id;
    }

    public void bind(int target) {
        GL15.glBindBuffer(target, _id);
    }

    @Override
    public void Delete() {
        GL15.glDeleteBuffers(_id);
    }
}
