package com.Engine;

import com.RenderEngine.DisplayManager;
import com.RenderEngine.Loader;
import com.RenderEngine.RawModel;
import com.RenderEngine.Renderer;

import com.Shaders.StaticShader;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Created by Sergey on 5/16/2017.
 */
public class GameEngine {
    // Game initialization method
    public static boolean init_game() {
        GLFWErrorCallback.createPrint(System.err).set(); // Setup an error callback
        if (!glfwInit()) { // Initialize GLFW
            return false;
        }
        try {
            DisplayManager.create_display(); // Create Display
            // Setup a key callback
            glfwSetKeyCallback(DisplayManager.get_window_handle(), (_window, key, scancode, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    // TODO
                    glfwSetWindowShouldClose(_window, true);
                }
            });
            GL.createCapabilities(); // Create OpenGL Capabilities
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); // Print error message
            return false;
        }
        return true;
    }

    // Main game loop method
    public static void run() {
        try {
            DisplayManager.get_window_handle(); // Is window handler exists
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); // Print error message
            return;
        }
        try {
            // GAME : Code
            Loader loader = new Loader();
            Renderer renderer = new Renderer();
            StaticShader shader = new StaticShader();
            float[] vertices = {
                    -0.5f, 0.5f, 0f,
                    -0.5f, -0.5f, 0f,
                    0.5f, -0.5f, 0f,
                    0.5f, 0.5f, 0f
            };
            int[] indices = {
                    0, 1, 3,
                    3, 1, 2
            };
            RawModel model = loader.load_to_vao(vertices, indices); // Create RAW model
            // Run game loop
            while (!glfwWindowShouldClose(DisplayManager.get_window_handle())) {
                renderer.render(); // Init renderer
                shader.start();
                renderer.render(model); // Render model
                shader.stop();
                DisplayManager.update_display(); // Update display
            }
            shader.Realize();
            loader.Realize();
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); // Print error message
        } finally {
            DisplayManager.close_display(); // Destroy display
        }
    }
}