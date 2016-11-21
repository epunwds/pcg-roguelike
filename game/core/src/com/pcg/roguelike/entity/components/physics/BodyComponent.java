package com.pcg.roguelike.entity.components.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author cr0s
 */
public class BodyComponent implements Component {
    public Body body;
    public BodyDef bodyDef;
    public FixtureDef fixture;

    public BodyComponent(Body body, BodyDef bodyDef, FixtureDef fixture) {
        this.body = body;
        this.bodyDef = bodyDef;
        this.fixture = fixture;
    }
    
    
}
