package com.Graphic;

import com.Core.toDelete;

import org.lwjgl.opengl.GL30;

/**
 * Created by Sergey on 5/17/2017.
 */
public class VertexArrayObject implements toDelete {
    private final int _id;

    public VertexArrayObject() {
        _id = GL30.glGenVertexArrays();
    }

    public int get_id() {
        return _id;
    }

    public void bind() {
        GL30.glBindVertexArray(_id);
    }

    @Override
    public void Delete() {
        GL30.glDeleteVertexArrays(_id);
    }
}