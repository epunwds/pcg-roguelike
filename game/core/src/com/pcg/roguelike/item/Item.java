package com.pcg.roguelike.item;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Item {
    private String name;
    private Sprite sprite;
    
    public Item(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
