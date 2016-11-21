package com.pcg.roguelike.entity.systems;

/**
 *
 * @author cr0s
 */
import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.pcg.roguelike.entity.components.ShootingComponent;
import com.pcg.roguelike.entity.components.SpriteComponent;

public class ShootingSystem extends IteratingSystem {
    
    private ComponentMapper<ShootingComponent> sm;

    public ShootingSystem(SpriteBatch batch, OrthographicCamera cam) {
        super(Family.all(ShootingComponent.class).get());

        sm = ComponentMapper.getFor(ShootingComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ShootingComponent sc = sm.get(entity);
        
        if (sc == null)
            return;
        
        if (sc.target == null)
            return;
        
        
    }
}
