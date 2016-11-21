package com.pcg.roguelike.entity.components.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author cr0s
 */
public class DirectionComponent implements Component {

    public Direction dir;
    
    public DirectionComponent(Direction dir) {
        this.dir = dir;
    }   

    public static enum Direction {
        RIGHT(0, "RIGHT"), 
        DOWN(1, "DOWN"),
        UP(2, "UP"),
        LEFT(3, "LEFT");
        
        private int dir;
        private String sDir;
        
        Direction(int dir, String sDir) {
            this.dir = dir;
            this.sDir = sDir;
        }

        public int getDirectionNum() {
            return dir;
        }

        @Override
        public String toString() {
            return "Direction (" + this.sDir + ")";
        }
    }
}
