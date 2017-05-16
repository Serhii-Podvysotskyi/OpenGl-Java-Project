package com.RenderEngine;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Sergey on 5/16/2017.
 */
public class Renderer {
    // Main render method
    public void render() {
        glClearColor(1.0f, 0.0f, 0.0f, 1.0f); // Set the clear color
        GL11.glViewport(0, 0, DisplayManager.get_width(), DisplayManager.get_height()); // Set the viewport
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer
    }

    // Model render method
    public void render(RawModel model) {
        GL30.glBindVertexArray(model.get_vertex_attribute_object_id()); // Bind vbo
        GL20.glEnableVertexAttribArray(0); // Enable vbo
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.get_vertex_count(), GL11.GL_UNSIGNED_INT, 0);// Draw triangles
        GL20.glDisableVertexAttribArray(0); // Disable vbo
        GL30.glBindVertexArray(0); // Unbind vector array
    }
}
