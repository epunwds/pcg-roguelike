package com.pcg.roguelike.item;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pcg.roguelike.entity.components.visual.SpriteComponent;

public class ItemEntityFactory {
    public static Entity getEntityForItem(Item item) {
        Entity e = new Entity();
        
        e.add(new SpriteComponent(item.getSprite(), 0));
        
        return e;
    }
}
