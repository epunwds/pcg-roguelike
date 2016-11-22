package com.pcg.roguelike.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.pcg.roguelike.entity.components.data.LifetimeComponent;
import com.pcg.roguelike.entity.components.data.HealthComponent;
import com.pcg.roguelike.entity.components.data.RemovedComponent;

public class HealthSystem extends IteratingSystem {
    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public HealthSystem() {
        super(Family.all(HealthComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        /* Entity scheduled for removal */
        if (hm.has(entity)) {
            HealthComponent hc = hm.get(entity);
            
            if (hc.hp <= 0) {
                /* Remove expired entity */
                entity.add(new RemovedComponent());
            }
        }
    }
}
