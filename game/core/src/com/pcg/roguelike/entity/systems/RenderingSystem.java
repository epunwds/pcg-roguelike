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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.dynamic.MovementComponent;
import com.pcg.roguelike.entity.components.visual.SpriteComponent;
import com.pcg.roguelike.entity.components.visual.TwoSpritesComponent;

public class RenderingSystem extends IteratingSystem {

    private final SpriteBatch batch;
    private Array<Entity> renderQueue;
    private final Comparator<Entity> comparator;
    private final OrthographicCamera cam;

    private final ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<TwoSpritesComponent> tm = ComponentMapper.getFor(TwoSpritesComponent.class);

    public RenderingSystem(SpriteBatch batch, OrthographicCamera cam) {
        super(Family.one(SpriteComponent.class, TwoSpritesComponent.class).get());

        renderQueue = new Array<>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                int zA = (sm.has(entityA)) ? sm.get(entityA).zOrder : tm.get(entityA).zOrder;
                int zB = (sm.has(entityB)) ? sm.get(entityB).zOrder : tm.get(entityB).zOrder;

                return (int) Math.signum(zA - zB);
            }
        };

        this.batch = batch;
        this.cam = cam;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            Sprite s = null;

            if (sm.has(entity)) {
                SpriteComponent tex = sm.get(entity);

                if (tex.s == null) {
                    continue;
                }

                s = tex.s;
            } else if (tm.has(entity)) {
                if (mm.has(entity)) {
                    float x = mm.get(entity).movement.x;

                    s = x < 0 ? tm.get(entity).left : tm.get(entity).right;
                }
            }

            if (s == null) {
                continue;
            }

            Body b = bm.get(entity).body;

            if (b == null) {
                continue;
            }

            s.setPosition(b.getPosition().x - s.getWidth() / 2, b.getPosition().y - s.getHeight() / 2);
            s.setRotation(b.getAngle() * MathUtils.radiansToDegrees);

            s.draw(batch);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
