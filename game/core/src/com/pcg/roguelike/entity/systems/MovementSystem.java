/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pcg.roguelike.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.pcg.roguelike.entity.components.BodyComponent;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.SpeedComponent;

public class MovementSystem extends IteratingSystem {
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<SpeedComponent> sm = ComponentMapper.getFor(SpeedComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    public MovementSystem() {
        super(Family.all(BodyComponent.class, MovementComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Body body = bm.get(entity).body;

        if (body == null) {
            return;
        }

        Vector2 movement = mm.get(entity).movement.cpy();
        
        float speed = 1;
        SpeedComponent sc = sm.get(entity);
        if (sc != null) {
            speed = sc.speed;
            movement.scl(speed);
        }
        
        if (mm.get(entity).isMoving)
            body.setLinearVelocity(movement);
        else
            body.setLinearVelocity(Vector2.Zero);
        
        if (mm.get(entity).canRotate) {
            body.setTransform(body.getPosition(), movement.angleRad());
        }
    }
}
