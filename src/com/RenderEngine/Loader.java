package com.RenderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 5/16/2017.
 */
public class Loader {
    // List of created vertex attribute objects
    private List<Integer> _vertex_attribute_objects = new ArrayList<Integer>();
    // List of created vertex buffer objects
    private List<Integer> _vertex_buffer_objects = new ArrayList<Integer>();

    public RawModel load_to_vao(float[] positions, int[] indices) {
        int vertex_attribute_object_id = create_vertex_attribute_object(); // Create vao
        bind_indices_buffer(indices);
        store_data_in_attribute_list(0, positions); // Bind data
        unbind_vao(); // Unbind vao
        // Create new Raw Model
        return new RawModel(vertex_attribute_object_id, indices.length);
    }

    public void Realize() {
        for (int id : _vertex_attribute_objects) {
            GL30.glDeleteVertexArrays(id);
        }
        for (int id : _vertex_buffer_objects) {
            GL15.glDeleteBuffers(id);
        }
    }

    private int create_vertex_attribute_object() {
        int vertex_attribute_object_id = GL30.glGenVertexArrays(); // Create vao
        _vertex_attribute_objects.add(vertex_attribute_object_id); // Add vao to resources list
        GL30.glBindVertexArray(vertex_attribute_object_id); // Bind vao
        return  vertex_attribute_object_id;
    }

    private void store_data_in_attribute_list(int attribute_number, float[] data) {
        int vertex_buffer_object_id = GL15.glGenBuffers(); // Create vbo
        _vertex_buffer_objects.add(vertex_buffer_object_id); // Add vbo to resources list
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_buffer_object_id); // Bind array buffer
        FloatBuffer buffer = store_data_in_buffer(data); // Convert data to buffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Put data to OpenGl buffer
        GL20.glVertexAttribPointer(attribute_number, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // Unbind vbo
    }

    private void unbind_vao() {
        GL30.glBindVertexArray(0); // Unbind vao
    }

    private void bind_indices_buffer(int[] indices) {
        int vertex_buffer_object_id = GL15.glGenBuffers(); // Create vbo
        _vertex_buffer_objects.add(vertex_buffer_object_id); // Add vbo to resources list
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vertex_buffer_object_id); // Bind element array buffer
        IntBuffer buffer = store_data_in_buffer(indices); // Convert data to buffer
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Put data to OpenGl buffer

    }

    public static IntBuffer store_data_in_buffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length); // Create `Integer` buffer
        buffer.put(data); // Put data to buffer
        buffer.flip(); // Flip buffer
        return buffer;
    }

    public static FloatBuffer store_data_in_buffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); // Create `float` buffer
        buffer.put(data); // Put data to buffer
        buffer.flip(); // Flip buffer
        return buffer;
    }
}

