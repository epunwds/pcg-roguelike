package com.pcg.roguelike.entity.systems;


import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.pcg.roguelike.world.GameWorld;

/**
 * Created by BugDeveloper on 22.11.2016.
 */
public class LightSystem extends box2dLight.PointLight {

    public LightSystem(RayHandler rayHandler, int rays, Color color, float distance, float x, float y) {
        super(rayHandler, rays, color, distance, x, y);
        
        this.setContactFilter(GameWorld.CATEGORY_LIGHT, (short) 0, GameWorld.MASK_LIGHT);
    }

    @Override
    protected void updateBody() {
        if (body == null || staticLight) return;

        final Vector2 vec = new Vector2(body.getPosition().x / GameWorld.TILE_SIZE, body.getPosition().y / GameWorld.TILE_SIZE);
        float angle = body.getAngle();
        
        final float cos = MathUtils.cos(angle);
        final float sin = MathUtils.sin(angle);
        
        final float dX = bodyOffsetX * cos - bodyOffsetY * sin;
        final float dY = bodyOffsetX * sin + bodyOffsetY * cos;
        
        start.x = vec.x + dX;
        start.y = vec.y + dY;
        
        setDirection(bodyAngleOffset + angle * MathUtils.radiansToDegrees);
    }

}

