package com.Test;

import com.Engine.GameEngine;

/**
 * Created by Sergey on 5/16/2017.
 */
public class MainGameLoop {
    public static void main(String[] args) {
        if (GameEngine.init_game()) {
            GameEngine.run();
        }
    }
}
