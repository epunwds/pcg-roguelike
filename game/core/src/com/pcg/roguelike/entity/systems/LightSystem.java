package com.pcg.roguelike.entity.systems;


import box2dLight.RayHandler;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.pcg.roguelike.entity.components.dynamic.MovementComponent;
import com.pcg.roguelike.world.GameWorld;

/**
 * Created by BugDeveloper on 22.11.2016.
 */
public class LightSystem extends box2dLight.PointLight {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    
    public LightSystem(RayHandler rayHandler, int rays, Color color, float distance, float x, float y) {
        super(rayHandler, rays, color, distance, x, y);
        
        this.setContactFilter(GameWorld.CATEGORY_LIGHT, (short) 0, GameWorld.MASK_LIGHT);
    }

    @Override
    protected void updateBody() {
        if (body == null || staticLight) return;

        Entity e = (Entity) body.getUserData();
        if (e == null)
            return;
        
        Vector2 movement = (mm.get(e) != null) ? mm.get(e).movement : null;
        if (movement == null)
            return;
        
        final Vector2 vec = new Vector2(body.getPosition().x / GameWorld.TILE_SIZE, body.getPosition().y / GameWorld.TILE_SIZE);

        float angle = movement.angle();
        
        start.x = vec.x;
        start.y = vec.y;
        
        setDirection(angle);
    }

}

