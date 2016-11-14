package com.pcg.roguelike.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pcg.roguelike.Roguelike;

public class DesktopLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1500;
        config.height = 900;

        config.title = "Roguelike";
        new LwjglApplication(new Roguelike(), config);
    }
}
