package com.pcg.roguelike.world.placer;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by BugDeveloper on 22.11.2016.
 */
public class StateLight extends box2dLight.PointLight {

    public StateLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y) {
        super(rayHandler, rays, color, distance, x, y);
    }

    protected void updateBody() {
        if (body == null || staticLight) return;

        final Vector2 vec = new Vector2(body.getPosition().x / 32, body.getPosition().y / 32);
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
