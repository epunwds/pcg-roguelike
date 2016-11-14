package com.pcg.roguelike.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.pcg.roguelike.world.generator.BSPGenerator;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

/**
 *
 * @author Cr0s
 */
public class World {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    
    private TiledMap map;
        
    public World() {        
    }
    
    public void create() {
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
