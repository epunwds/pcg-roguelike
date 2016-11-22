package com.pcg.roguelike.world.placer;


import com.badlogic.gdx.math.Vector2;
import com.pcg.roguelike.world.generator.BSPGenerator.BSPTree;
import java.util.Random;


/**
 *
 * @author Cr0s
 */
public class PlayerPlacer {
    public static Vector2 placePlayer(Random rnd, BSPTree tree) {
        BSPTree room = selectRoom(rnd, tree);
        return selectPointInRoom(rnd, room);
    }
    
    private static BSPTree selectRoom(Random rnd, BSPTree tree) {
        /* Recursion step: randomly dig into tree */
        if (tree.getLeftChild() != null && tree.getRightChild() != null) {
            return selectRoom(rnd, (rnd.nextBoolean()) ? tree.getRightChild() : tree.getRightChild());
        } else if (tree.getLeftChild() != null) {
            return selectRoom(rnd, tree.getLeftChild());
        } else if (tree.getRightChild() != null) {
            return selectRoom(rnd, tree.getRightChild());
        }
        
        /* Finals step: return room without child nodes */
        return tree;
    }
    
    private static Vector2 selectPointInRoom(Random rnd, BSPTree node) {
        //int x = node.getStartX() + 1 + rnd.nextInt(node.getWidth() - 1);
        //int y = node.getStartY() + 1 + rnd.nextInt(node.getHeight() - 1);
        
        // Select center
        int x = node.getStartX() + 1 + (node.getWidth() / 2 - 1);
        int y = node.getStartY() + 1 + (node.getHeight() / 2 - 1);
        
        return new Vector2(x, y);
    }
}
