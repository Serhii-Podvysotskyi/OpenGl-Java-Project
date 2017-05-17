package com.Textures;

import com.RenderEngine.Loader;

/**
 * Created by Sergey on 5/16/2017.
 */
public class ModelTexture {
    private int _texture_id; // Texture id

    public ModelTexture(int texture_id) {
        _texture_id = texture_id;
    }

    // Get texture id
    public int get_texture_id() {
        return _texture_id;
    }
}
