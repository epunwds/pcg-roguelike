package com.pcg.roguelike.entity.components.dynamic;

import com.badlogic.ashley.core.Component;
import com.pcg.roguelike.collision.ICollisionAction;

/**
 *
 * @author cr0s
 */
public class CollisionActionsComponent implements Component {

    public ICollisionAction[] actions;

    public CollisionActionsComponent(ICollisionAction... acts) {
        this.actions = acts;
    }
}
