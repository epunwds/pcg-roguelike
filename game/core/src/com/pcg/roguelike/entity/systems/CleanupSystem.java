package com.pcg.roguelike.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.data.RemovedComponent;
import com.pcg.roguelike.world.GameWorld;

public class CleanupSystem extends IteratingSystem {
    private final ComponentMapper<RemovedComponent> rm = ComponentMapper.getFor(RemovedComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    private GameWorld world;
    
    public CleanupSystem(GameWorld world) {
        super(Family.all(BodyComponent.class, RemovedComponent.class).get());
        
        this.world = world;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Body body = bm.get(entity).body;

        if (body == null) {
            return;
        }

        /* Entity scheduled for removal */
        if (rm.has(entity)) {
            world.removeEntity(entity);
        }
    }
}
