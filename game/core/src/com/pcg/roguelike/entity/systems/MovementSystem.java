/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pcg.roguelike.entity.systems;

/**
 *
 * @author cr0s
 */
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.PositionComponent;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public MovementSystem() {
        super(Family.all(PositionComponent.class, MovementComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        MovementComponent movement = mm.get(entity);

        position.x += movement.velocityX * deltaTime;
        position.y += movement.velocityY * deltaTime;
    }
}
