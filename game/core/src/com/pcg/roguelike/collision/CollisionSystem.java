package com.pcg.roguelike.collision;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pcg.roguelike.entity.components.dynamic.CollisionActionsComponent;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.world.GameWorld;

/**
 *
 * @author cr0s
 */
public class CollisionSystem implements ContactListener {

    private final ComponentMapper<CollisionActionsComponent> cm = ComponentMapper.getFor(CollisionActionsComponent.class);

    private GameWorld world;

    public CollisionSystem(GameWorld world) {
        this.world = world;
    }

    @Override
    public void beginContact(Contact cntct) {
        Fixture fixa = cntct.getFixtureA();
        Fixture fixb = cntct.getFixtureB();

        Body a = fixa.getBody();
        Body b = fixb.getBody();

        Entity ea = (Entity) a.getUserData();
        Entity eb = (Entity) b.getUserData();

        /* Do two-way collision */
        doCollisionAction(ea, eb);
        doCollisionAction(eb, ea);
    }

    private void doCollisionAction(Entity ea, Entity eb) {
        if (cm.has(ea)) {
            for (ICollisionAction action : cm.get(ea).actions) {
                System.out.println("Collision: " + action + " | " + ea + " | " + eb);
                action.onCollide(ea, eb);
            }
        }
    }

    @Override
    public void endContact(Contact cntct) {

    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {

    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {

    }

}
