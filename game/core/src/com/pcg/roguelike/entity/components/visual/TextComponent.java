package com.pcg.roguelike.entity.components.visual;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Cr0s
 */
public class TextComponent implements Component {

    public String text;
    public Color color;

    public TextComponent(String text, Color color) {
        this.text = text;
        this.color = color;
    }
}
