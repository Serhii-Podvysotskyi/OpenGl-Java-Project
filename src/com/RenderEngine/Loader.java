package com.RenderEngine;

import com.Models.RawModel;
import com.sun.deploy.util.BufferUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 5/16/2017.
 */
public class Loader {
    // List of created vertex array objects
    private List<Integer> _vertex_array_objects = new ArrayList<Integer>();
    // List of created vertex buffer objects
    private List<Integer> _buffer_objects = new ArrayList<Integer>();
    // List of loaded textures
    private List<Integer> _texture_objects = new ArrayList<Integer>();

    public RawModel load_model(float[] positions, int[] indices) {
        int vertex_array_object_id = create_vertex_array_object(); // Create vao
        bind_element_array_buffer(indices); // bind indices
        store_data_in_attribute_list(0, positions, 3); // Bind data
        unbind_vertex_array(); // Unbind vao
        // Create new Raw Model
        return new RawModel(vertex_array_object_id, indices.length);
    }

    public RawModel load_model(float[] positions, float[] texture_coords, int[] indices) {
        int vertex_array_object_id = create_vertex_array_object(); // Create vao
        bind_element_array_buffer(indices); // bind indices
        store_data_in_attribute_list(0, positions, 3); // Create vertex attribute list
        store_data_in_attribute_list(1, texture_coords, 4); // Create texture coordinates attribute list
        unbind_vertex_array(); // Unbind vao
        // Create new Raw Model
        return new RawModel(vertex_array_object_id, indices.length);
    }

    public int load_texture(String file_name) throws Exception {
        int texture_id;
        BufferedImage image;
        try {
            InputStream input_stream = new FileInputStream("res/" + file_name + ".png");
            ByteArrayOutputStream output_stream = new ByteArrayOutputStream();
            int input_read = input_stream.read();
            while (input_read != -1) {
                output_stream.write(input_read);
                input_read = input_stream.read();
            }
            byte[] byte_array = output_stream.toByteArray();
            image = ImageIO.read(new ByteArrayInputStream(byte_array));
        } catch (Exception ex) {
            System.out.println("Error while loading file: res/" + file_name + ".png");
            return 0;
        }
        try {
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            ByteBuffer byte_buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    byte_buffer.put((byte) ((pixel >> 16) & 0xFF));
                    byte_buffer.put((byte) ((pixel >> 8) & 0xFF));
                    byte_buffer.put((byte) (pixel & 0xFF));
                    byte_buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            byte_buffer.flip();
            texture_id = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture_id);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byte_buffer);
        } catch (Exception ex) {
            System.out.println("Error while loading texture: " + file_name);
            return 0;
        }
        _texture_objects.add(texture_id);
        return texture_id;
    }

    public int load_texture_2(String file_name) throws Exception {
        int[] pixels = null;
        int width = 0, height = 0;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream("res/" + file_name + ".png"));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (Exception ex) {
            System.out.println("Error while loading file: res/" + file_name + ".png");
        }
        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);
            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        int texture_id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture_id);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data).flip();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        return texture_id;
    }

    public void Realize() {
        // Delete all vertex attribute objects
        for (int id : _vertex_array_objects) {
            GL30.glDeleteVertexArrays(id);
        }
        // Delete all buffer objects
        for (int id : _buffer_objects) {
            GL15.glDeleteBuffers(id);
        }
        // Delete all texture objects
        for (int id : _texture_objects) {
            GL11.glDeleteTextures(id);
        }
    }

    private int create_vertex_array_object() {
        int vertex_array_object_id = GL30.glGenVertexArrays(); // Create vao
        _vertex_array_objects.add(vertex_array_object_id); // Add vao to resources list
        GL30.glBindVertexArray(vertex_array_object_id); // Bind vao
        return vertex_array_object_id;
    }

    private void store_data_in_attribute_list(int attribute_number, float[] data, int size) {
        int buffer_object_id = GL15.glGenBuffers(); // Create new buffer
        _buffer_objects.add(buffer_object_id); // Add buffer to resources list
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer_object_id); // Bind array buffer
        FloatBuffer buffer = store_data_in_buffer(data); // Convert data to buffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Put data to OpenGl buffer
        GL20.glVertexAttribPointer(attribute_number, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // Unbind vbo
    }

    private void unbind_vertex_array() {
        GL30.glBindVertexArray(0); // Unbind vao
    }

    private void bind_element_array_buffer(int[] indices) {
        int buffer_object_id = GL15.glGenBuffers(); // Create vbo
        _buffer_objects.add(buffer_object_id); // Add vbo to resources list
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer_object_id); // Bind element array buffer
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

