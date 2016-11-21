package com.pcg.roguelike.entity.components;

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
    public int shootDelayTicks;
    
    public ShootingComponent(Vector3 target, int shootDelayTicks) {
        this.target = target;
        this.shootDelayTicks = shootDelayTicks;
    }

    public Vector2 getTarget2() {
        return new Vector2(target.x, target.y);
    }
}
