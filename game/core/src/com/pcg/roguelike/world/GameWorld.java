package com.pcg.roguelike.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pcg.roguelike.world.generator.BSPGenerator;

import java.util.Random;

/**
 * Created by BugDeveloper on 16.11.2016.
 */
public class GameWorld {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int TILE_SIZE = 32;
    private BSPGenerator gen;
    private TiledMap map;
    private BodyDef bodyDef;
    private PolygonShape rectShape;
    private FixtureDef fixtureDef;
    private World world;

    public TiledMap getMap() {
        return map;
    }

    public GameWorld(World world) {
        this.world = world;
        gen = new BSPGenerator(WIDTH, HEIGHT, 5, 3, new Random());

        //variables for reusing
        bodyDef = new BodyDef();
        rectShape = new PolygonShape();
        rectShape.setAsBox(TILE_SIZE / 2, TILE_SIZE / 2);
        fixtureDef = new FixtureDef();

        // wall body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;

        //wall fixture definition
        fixtureDef.shape = rectShape;
        fixtureDef.friction = .2f;
        fixtureDef.restitution = 0;
    }

    public void create() {
        int[][] mapBlueprint = gen.generateMap();

        /* Tiles textures */
        Texture tiles = new Texture(Gdx.files.internal("tiles.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, TILE_SIZE, TILE_SIZE);

        /* Creating TiledMap and filling it with tiles corresponding to the blueprint */
        TiledMap tiledMap = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(WIDTH, HEIGHT, TILE_SIZE, TILE_SIZE);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                switch (mapBlueprint[x][y]) {
                    //wall
                    case 0: {
                        StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[0][2]);
                        tile.setId(0);
                        cell.setTile(tile);

                        bodyDef.position.set((x + 0.5f) * TILE_SIZE, (y + 0.5f) * TILE_SIZE);
                        world.createBody(bodyDef).createFixture(fixtureDef);
                    }
                    break;

                    //ground
                    case 1: {
                        StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[0][0]);
                        tile.setId(1);
                        cell.setTile(tile);
                    }
                    break;
                }
                layer.setCell(x, y, cell);
            }
        }
        
        rectShape.dispose();
        tiledMap.getLayers().add(layer);
        map = tiledMap;
    }

}
