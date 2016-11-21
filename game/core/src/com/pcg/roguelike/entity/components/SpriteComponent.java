package com.pcg.roguelike.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Cr0s
 */
public class SpriteComponent implements Component {

    public Sprite s;
    public int zOrder;
    
    public SpriteComponent(Sprite s, int zOrder) {
        this.s = s;
        this.zOrder = zOrder;
    }
    
}
