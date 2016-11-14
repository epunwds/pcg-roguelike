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
import com.pcg.roguelike.entity.components.BobComponent;
import com.pcg.roguelike.entity.components.CollidableComponent;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.PositionComponent;
import com.pcg.roguelike.world.World;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<CollidableComponent> cm = ComponentMapper.getFor(CollidableComponent.class);
    private final ComponentMapper<BobComponent> bm = ComponentMapper.getFor(BobComponent.class);

    private final World world;

    public MovementSystem(World world) {
        super(Family.all(PositionComponent.class, MovementComponent.class).get());

        this.world = world;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        MovementComponent movement = mm.get(entity);
        BobComponent bc = bm.get(entity);
        
        float newX = position.x + movement.velocityX * deltaTime;
        float newY = position.y + movement.velocityY * deltaTime;
        
        CollidableComponent collidable = cm.get(entity);
        boolean canMoveX = true, canMoveY = true;

        if (collidable != null && collidable.collideWithTiles) {     
            int tileX = (int) Math.floor((newX + bc.bob.width / 2) / World.CELL_SIZE);
            int tileY = (int) Math.floor((newY + bc.bob.height / 2) / World.CELL_SIZE);

            if (!world.isCellPassable(tileX, tileY)) {
                canMoveX = false;
                canMoveY = false;
            }
            
            System.out.println("old: " + newX + "; " + newY + " | x: " + canMoveX + " y: " + canMoveY);
        }

        if (canMoveX) {
            position.x = newX;
        }

        if (canMoveY) {
            position.y = newY;
        }
    }
}
