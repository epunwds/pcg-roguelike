package com.pcg.roguelike.collision;

import com.badlogic.ashley.core.Entity;

/**
 *
 * @author cr0s
 */
public interface ICollisionAction {
    public void onCollide(Entity a, Entity b);
}
