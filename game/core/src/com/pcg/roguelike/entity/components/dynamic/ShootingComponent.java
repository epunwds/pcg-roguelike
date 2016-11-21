package com.pcg.roguelike.entity.components.dynamic;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author cr0s
 */
public class ShootingComponent implements Component {
    public Vector3 target;
    public boolean isShooting;

    public int shotTicks;
    
    public ShootingComponent(Vector3 target) {
        this.target = target;
    }

    public Vector2 getTarget2() {
        return new Vector2(target.x, target.y);
    }
}
