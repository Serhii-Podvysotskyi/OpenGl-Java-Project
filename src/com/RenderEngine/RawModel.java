package com.RenderEngine;

/**
 * Created by Sergey on 5/16/2017.
 */
public class RawModel {
    private int _vertex_attribute_object_id;
    private int _vertex_count;

    public RawModel(int vertex_attribute_object_id, int vertex_count) {
        _vertex_attribute_object_id = vertex_attribute_object_id;
        _vertex_count = vertex_count;
    }

    public int get_vertex_attribute_object_id() {
        return _vertex_attribute_object_id;
    }

    public int get_vertex_count() {
        return _vertex_count;
    }
}
