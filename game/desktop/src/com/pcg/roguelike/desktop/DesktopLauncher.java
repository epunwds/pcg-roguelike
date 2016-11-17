package com.pcg.roguelike.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pcg.roguelike.Roguelike;

public class DesktopLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Roguelike";
        config.width = 640;
        config.height = 480;
        new LwjglApplication(new Roguelike(), config);
    }
}
