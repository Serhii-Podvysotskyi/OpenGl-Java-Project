package com.Engine;

import com.Models.TexturedModel;
import com.RenderEngine.DisplayManager;
import com.RenderEngine.Loader;
import com.Models.RawModel;
import com.RenderEngine.Renderer;

import com.Shaders.TextureShader;
import com.Textures.ModelTexture;
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
            //ShaderProgram shader = new StaticShader();
            ShaderProgram shader = new TextureShader();
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
            float[] texture_coords = {
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0
            };
            //RawModel model = loader.load_model(vertices, indices); // Create RAW model
            RawModel model = loader.load_model(vertices, texture_coords, indices); // Create Raw model
            ModelTexture texture = new ModelTexture(loader.load_texture_2("textures/img1"));
            TexturedModel textured_model = new TexturedModel(model, texture);
            // Run game loop
            while (!glfwWindowShouldClose(DisplayManager.get_window_handle())) {
                renderer.render(); // Init renderer
                shader.start();
                renderer.render(textured_model); // Render model
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