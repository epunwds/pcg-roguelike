package com.pcg.roguelike.world;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.pcg.roguelike.world.generator.BSPGenerator;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.pcg.roguelike.entity.components.PositionComponent;
import com.pcg.roguelike.entity.systems.MovementSystem;
import com.pcg.roguelike.entity.systems.RenderingSystem;

/**
 *
 * @author Cr0s
 */
public class WorldRenderer {

    private TiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;

    private World world;

    public WorldRenderer(World world) {
        this.world = world;
        mapRenderer = new OrthogonalTiledMapRenderer(world.getMap());
        shapeRenderer = new ShapeRenderer();
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        mapRenderer.setView(camera);
        mapRenderer.render();

        world.update();
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(camera.combined);
        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                if (world.isCellPassable(x, y)) {
                    shapeRenderer.setColor(Color.GREEN);
                } else {
                    shapeRenderer.setColor(Color.RED);
                }

                shapeRenderer.rect(x * 32 + 5, y * 32 + 5, 27, 27);
            }
        }
        
        ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
        PositionComponent position = pm.get(world.getPlayer());
        shapeRenderer.circle(position.x, position.y, 5);

        shapeRenderer.end();
    }
}
