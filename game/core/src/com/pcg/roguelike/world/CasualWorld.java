package com.pcg.roguelike.world;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.pcg.roguelike.world.generator.BSPGenerator;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pcg.roguelike.entity.components.BobComponent;
import com.pcg.roguelike.entity.components.CollidableComponent;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.PlayerComponent;
import com.pcg.roguelike.entity.components.PositionComponent;
import com.pcg.roguelike.entity.components.VisualComponent;
import com.pcg.roguelike.entity.systems.MovementSystem;
import com.pcg.roguelike.entity.systems.RenderingSystem;

import java.util.Random;

/**
 *
 * @author Cr0s
 */
public class CasualWorld {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int CELL_SIZE = 32;
    
    private TiledMap map;
    private PooledEngine engine;
    private TiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;
    private BSPGenerator gen;
    
    Entity player;

    public CasualWorld(OrthographicCamera camera) {
        engine = new PooledEngine();
        engine.addSystem(new RenderingSystem(camera));
        engine.addSystem(new MovementSystem(this));
        
        player = new Entity();
        player.add(new PositionComponent(5 * CELL_SIZE, 5 * CELL_SIZE));
        player.add(new MovementComponent(0f, 0f));
        player.add(new PlayerComponent());
        
        Texture tiles;

        tiles = new Texture(Gdx.files.internal("tiles.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);        
        
        player.add(new VisualComponent(splitTiles[1][1]));
        player.add(new CollidableComponent(true, true));
        player.add(new BobComponent(new Rectangle(0, 0, 32, 32)));

        engine.addEntity(player);
        
        shapeRenderer = new ShapeRenderer();
        gen = new BSPGenerator(CasualWorld.WIDTH, CasualWorld.HEIGHT, 5, 3, new Random());
    }

    public void generateLevel(CasualWorld casualWorld) {

        TiledMap tiledMap = new TiledMap();

        int[][] m = gen.generateMap();

        Texture tiles;

        tiles = new Texture(Gdx.files.internal("tiles.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
        MapLayers layers = tiledMap.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(CasualWorld.WIDTH, CasualWorld.HEIGHT, 32, 32);

        for (int x = 0; x < CasualWorld.WIDTH; x++) {
            for (int y = 0; y < CasualWorld.HEIGHT; y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                switch (m[x][y]) {
                    case 0: {
                        StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[0][2]);
                        tile.setId(0);
                        cell.setTile(tile);
                    }
                    break;

                    case 1: {
                        StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[0][0]);
                        tile.setId(1);
                        tile.getProperties().put("passable", null);

                        cell.setTile(tile);
                    }
                    break;
                }

                layer.setCell(x, y, cell);
            }
        }

        layers.add(layer);
        casualWorld.setMap(tiledMap);
    }

    public void create() {
        //new SimpleGenerator().generateLevel(this);
        generateLevel(this);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }
    
    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }
    
    public boolean isCellPassable(Vector2 point) {
        return isCellPassable((int) (point.x / CELL_SIZE), (int) (point.y / CELL_SIZE));
    }
    
    public boolean isCellPassable(float x, float y) {
        return isCellPassable((int) (x / CELL_SIZE), (int) (y / CELL_SIZE));
    }
    
    public boolean isCellPassable(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
            return false;
        
        TiledMapTileLayer floor = (TiledMapTileLayer) map.getLayers().get(0);
        Cell cell = floor.getCell(x, y);
        
        return cell.getTile().getProperties().containsKey("passable");
    }

    public void update() {
        engine.update(Gdx.graphics.getDeltaTime());
    }

    public Entity getPlayer() {
        return player;
    }
}
