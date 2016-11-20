package com.pcg.roguelike.entity.components;
import com.badlogic.ashley.core.Component;

/**
 *
 * @author Cr0s
 */
public class CollidableComponent implements Component {

    public boolean collideWithTiles, collideWithEntities;

    public CollidableComponent(boolean collideWithTiles, boolean collideWithEntities) {
        this.collideWithTiles = collideWithTiles;
        this.collideWithEntities = collideWithEntities;
    }
}
