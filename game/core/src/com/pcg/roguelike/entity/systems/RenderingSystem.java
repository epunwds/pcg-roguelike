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
import com.badlogic.gdx.utils.Array;
import com.pcg.roguelike.entity.components.SpriteComponent;

public class RenderingSystem extends IteratingSystem {
    private final SpriteBatch batch;
    private Array<Entity> renderQueue;
    private final Comparator<Entity> comparator;
    private final OrthographicCamera cam;
    private ComponentMapper<SpriteComponent> sm;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera cam) {
        super(Family.all(SpriteComponent.class).get());

        sm = ComponentMapper.getFor(SpriteComponent.class);
        renderQueue = new Array<>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int) Math.signum(sm.get(entityB).zOrder
                        - sm.get(entityA).zOrder);
            }
        };

        this.batch = batch;
        this.cam = cam;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            SpriteComponent tex = sm.get(entity);

            if (tex.s == null) {
                continue;
            }

            sm.get(entity).s.draw(batch);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
