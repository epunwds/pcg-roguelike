package com.pcg.roguelike.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.pcg.roguelike.entity.components.data.LifetimeComponent;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.data.RemovedComponent;
import com.pcg.roguelike.world.GameWorld;

public class LifetimeSystem extends IteratingSystem {
    private final ComponentMapper<LifetimeComponent> lm = ComponentMapper.getFor(LifetimeComponent.class);

    public LifetimeSystem() {
        super(Family.all(LifetimeComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        /* Entity scheduled for removal */
        if (lm.has(entity)) {
            LifetimeComponent lc = lm.get(entity);
            
            if (lc.lifetimeTicks-- <= 0) {
                /* Remove expired entity */
                entity.add(new RemovedComponent());
            }
        }
    }
}
