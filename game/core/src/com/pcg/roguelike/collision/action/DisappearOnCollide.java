package com.pcg.roguelike.collision.action;

import com.badlogic.ashley.core.Entity;
import com.pcg.roguelike.collision.ICollisionAction;
import com.pcg.roguelike.entity.components.data.RemovedComponent;

/**
 *
 * @author cr0s
 */
public class DisappearOnCollide implements ICollisionAction {

    @Override
    public void onCollide(Entity a, Entity b) {
        /* Mark entity as removed */
        a.add(new RemovedComponent());
    }
}
