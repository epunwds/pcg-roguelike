package com.pcg.roguelike.entity.components.dynamic;

import com.badlogic.ashley.core.Component;

/**
 *
 * @author cr0s
 */
public class SpeedComponent implements Component {

    public float speed;

    public SpeedComponent(float speed) {
        this.speed = speed;
    }
}
