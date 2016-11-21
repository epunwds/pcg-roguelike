package com.pcg.roguelike.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.physics.box2d.Body;
import com.pcg.roguelike.entity.components.BodyComponent;
import com.pcg.roguelike.world.GameWorld;

/**
 *
 * @author cr0s
 */
public class PhysicsSystem extends EntitySystem implements EntityListener {

    private static final float TIMESTEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 8, POSITION_ITERATIONS = 3;
    private GameWorld world;

    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    public PhysicsSystem(GameWorld world) {
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        world.getBox2dWorld().step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    @Override
    public void entityAdded(Entity entity) {
        BodyComponent bc = bm.get(entity);

        Body body = world.getBox2dWorld().createBody(bc.bodyDef);
        body.createFixture(bc.fixture);

        body.setUserData(entity);
        
        bc.body = body;
        bc.fixture.shape.dispose();
        
        System.out.println("Added entity: " + bc.body);
    }

    @Override
    public void entityRemoved(Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
