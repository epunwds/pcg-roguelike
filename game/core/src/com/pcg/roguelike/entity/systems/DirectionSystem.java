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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.pcg.roguelike.entity.components.BodyComponent;
import com.pcg.roguelike.entity.components.DirectionComponent;
import com.pcg.roguelike.entity.components.DirectionComponent.Direction;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.ShootingComponent;
import com.pcg.roguelike.entity.components.SpeedComponent;

public class DirectionSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<ShootingComponent> sm = ComponentMapper.getFor(ShootingComponent.class);
    private ComponentMapper<DirectionComponent> dirMapper = ComponentMapper.getFor(DirectionComponent.class);

    public DirectionSystem() {
        super(Family.all(DirectionComponent.class, MovementComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        MovementComponent mc = mm.get(entity);
        ShootingComponent sc = sm.get(entity);
        DirectionComponent dc = dirMapper.get(entity);

        Direction dir = getDirection(mc.movement, (sc != null) ? sc.target : null);
        dc.dir = dir;
    }

    private Direction getDirection(Vector2 movement, Vector3 target) {
        int dx, dy;

        int angle = 0;

        if (target != null) {
            Vector2 tv2 = new Vector2(target.x, target.y);

            angle = (int) tv2.angle();
        } else {
            angle = (int) movement.angle();
        }

        angle = (angle + 45) % 360;
        
        if (angle >= 0 && angle < 90) {
            return Direction.RIGHT;
        } else if (angle >= 90 && angle < 180) {
            return Direction.UP;
        } else if (angle >= 180 && angle < 270) {
            return Direction.LEFT;
        } else if (angle >= 270) {
            return Direction.DOWN;
        }
        
        return Direction.DOWN;
    }
}
