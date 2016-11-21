package com.pcg.roguelike.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author cr0s
 */
public class MovementComponent implements Component {

    public Vector2 movement;
    public boolean isMoving;
    public boolean canRotate;
    
    public MovementComponent(Vector2 movement) {
        this.movement = movement;
    }   

    public MovementComponent(Vector2 movement, boolean isMoving) {
        this.movement = movement;
        this.isMoving = isMoving;
    }

    public MovementComponent(Vector2 movement, boolean isMoving, boolean canRotate) {
        this.movement = movement;
        this.isMoving = isMoving;
        this.canRotate = canRotate;
    }
}
