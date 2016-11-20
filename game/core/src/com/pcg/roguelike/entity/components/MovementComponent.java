package com.pcg.roguelike.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author cr0s
 */
public class MovementComponent implements Component {

    public Vector2 movement;
    
    public MovementComponent(Vector2 movement) {
        this.movement = movement;
    }   
}
