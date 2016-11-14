package com.pcg.roguelike.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.pcg.roguelike.world.generator.BSPGenerator;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.pcg.roguelike.entity.systems.MovementSystem;
import com.pcg.roguelike.entity.systems.RenderingSystem;

/**
 *
 * @author Cr0s
 */
public class World {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    
    private TiledMap map;
    private PooledEngine engine;    
    private TiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;
    
        
    public World(OrthographicCamera camera) {     
        engine = new PooledEngine();
        engine.addSystem(new RenderingSystem(camera));
        engine.addSystem(new MovementSystem(this));
        
        shapeRenderer = new ShapeRenderer();      
    }
    
    public void create() {
        //new SimpleGenerator().generateLevel(this);
        BSPGenerator.generateLevel(this);
        mapRenderer = new OrthogonalTiledMapRenderer(map);        
    }
    
    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }
    
    public boolean isCellPassable(Vector2 point) {
        return isCellPassable((int) point.x, (int) point.y);
    }
    
    public boolean isCellPassable(int x, int y) {
        TiledMapTileLayer floor = (TiledMapTileLayer) map.getLayers().get(0);
        Cell cell = floor.getCell(x, y);
        
        return cell.getTile().getProperties().containsKey("passable");
    }

    public void update() {
        engine.update(Gdx.graphics.getDeltaTime());
    }
}
