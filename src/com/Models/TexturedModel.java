package com.Models;

import com.Textures.ModelTexture;

/**
 * Created by Sergey on 5/16/2017.
 */
public class TexturedModel {
    private RawModel _raw_model; // Raw model component
    private ModelTexture _texture; // Texture component

    public TexturedModel(RawModel raw_model, ModelTexture texture) {
        _raw_model = raw_model;
        _texture = texture;
    }

    // Get Raw model component
    public RawModel get_raw_model() {
        return _raw_model;
    }

    // Get texture component
    public ModelTexture get_texture() {
        return _texture;
    }

    // Get texture id
    public int get_texture_id() {
        return _texture.get_texture_id();
    }
}
