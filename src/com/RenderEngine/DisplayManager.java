package com.RenderEngine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
/**
 * Created by Sergey on 5/16/2017.
 */
public class DisplayManager {
    private static long _window; // The window handle
    private static int _width = 1280; // Windows width
    private static int _height = 720; // Windows height

    private static boolean _size_changed = false;

    // Create Display method
    public static void create_display() throws Exception {
        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        // Create the window
        _window = glfwCreateWindow(1280, 720, "OpenGL application", NULL, NULL);
        if (_window == NULL) {
            throw new Exception("Failed to create the GLFW window");
        }
        // Setup resize callback
        glfwSetWindowSizeCallback(_window, (_window, width, height) -> {
            // Set new window size
            _width = width;
            _height = height;
            _size_changed = true;
        });
        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(_window, pWidth, pHeight);// Get the window size
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()); // Get the monitor resolution
            // Center the window
            glfwSetWindowPos(_window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }
        glfwMakeContextCurrent(_window); // Make the OpenGL context current
        glfwSwapInterval(1); // Enable v-sync
        glfwShowWindow(_window);// Make the window visible
    }

    // Update display method
    public static void update_display() {
        glfwSwapBuffers(_window); // swap the color buffers
        glfwPollEvents(); // Poll for window events.
        if (_size_changed) {
            GL11.glViewport(0, 0, _width, _height); // Set the viewport
            _size_changed = false;
        }
    }

    // Close Display method
    public static void close_display() {
        glfwFreeCallbacks(_window); // Free window callbacks
        glfwDestroyWindow(_window); // Destroy the window
        glfwTerminate(); // Terminate GLFW
        glfwSetErrorCallback(null).free(); // Free the error callback
        _window = NULL; // Clear window handlle
    }

    // Get window width
    public static int get_width() {
        return _width;
    }

    // Get window height
    public static int get_height() {
        return _height;
    }

    // Get Window handle
    public static long get_window_handle() throws Exception {
        if (_window == NULL) {
            throw new Exception("GLFW window wasn't created");
        }
        return _window;
    }
}
