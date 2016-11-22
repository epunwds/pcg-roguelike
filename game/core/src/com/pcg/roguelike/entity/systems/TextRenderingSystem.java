package com.pcg.roguelike.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.pcg.roguelike.entity.components.data.LifetimeComponent;
import com.pcg.roguelike.entity.components.data.HealthComponent;
import com.pcg.roguelike.entity.components.data.RemovedComponent;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.visual.TextComponent;
import com.pcg.roguelike.world.GameWorld;

public class TextRenderingSystem extends IteratingSystem {

    private final ComponentMapper<TextComponent> tm = ComponentMapper.getFor(TextComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    private SpriteBatch batch;
    private OrthographicCamera cam;

    private BitmapFont font = new BitmapFont();

    private Array<Entity> renderQueue = new Array<>();

    public TextRenderingSystem(SpriteBatch batch, OrthographicCamera cam) {
        super(Family.all(TextComponent.class, BodyComponent.class).get());

        this.batch = batch;
        this.cam = cam;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        
        for (Entity e : this.renderQueue) {
            BodyComponent bc = bm.get(e);
            if (bc.body == null) {
                return;
            }

            /* Entity scheduled for removal */
            if (tm.has(e)) {
                TextComponent tc = tm.get(e);

                font.setColor(tc.color);
                font.draw(batch, tc.text, bc.body.getPosition().x, bc.body.getPosition().y);
            }
        }
        
        batch.end();
        
        this.renderQueue.clear();
    }

}
