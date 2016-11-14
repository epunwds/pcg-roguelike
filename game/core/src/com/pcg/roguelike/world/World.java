package com.pcg.roguelike.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.pcg.roguelike.map.Cell;
import com.pcg.roguelike.world.generator.BSPGenerator;
import com.pcg.roguelike.world.generator.SimpleGenerator;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.styled.TilesetType;
import squidpony.squidmath.RNG;

/**
 *
 * @author Cr0s
 */
public class World {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    
    private TiledMap map;
    
    public World() {
        //new SimpleGenerator().generateLevel(this);
        BSPGenerator.generateLevel(this);
    }
    
    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }
    
    
}
