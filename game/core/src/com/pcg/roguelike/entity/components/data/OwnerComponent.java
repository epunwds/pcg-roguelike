package com.pcg.roguelike.entity.components.data;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 *
 * @author Cr0s
 */
public class OwnerComponent implements Component {
	public Entity owner;

    public OwnerComponent(Entity owner) {
        this.owner = owner;
    }
}