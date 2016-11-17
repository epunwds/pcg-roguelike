package com.pcg.roguelike.world;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.pcg.roguelike.entity.components.PositionComponent;

/**
 * Created by BugDeveloper on 16.11.2016.
 */
public class WorldRenderer {

    private TiledMapRenderer mapRenderer;

    private GameWorld gameWorld;

    public WorldRenderer(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        mapRenderer = new OrthogonalTiledMapRenderer(gameWorld.getMap());
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
