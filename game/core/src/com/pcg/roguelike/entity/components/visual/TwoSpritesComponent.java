package com.pcg.roguelike.entity.components.visual;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author cr0s
 */
public class TwoSpritesComponent implements Component {
    public Sprite left, right;
    public int zOrder;

    public TwoSpritesComponent(Sprite left, Sprite right, int zOrder) {
        this.left = left;
        this.right = right;
        this.zOrder = zOrder;
    }
}
