package com.pcg.roguelike.world.placer;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.pcg.roguelike.entity.enemy.BigMob;
import com.pcg.roguelike.entity.enemy.SmallMob;
import com.pcg.roguelike.world.GameWorld;
import com.pcg.roguelike.world.generator.BSPGenerator.BSPTree;
import java.util.Random;

/**
 *
 * @author Cr0s
 */
public class BossPlacer {

    public static void placeBoss(GameWorld world, Vector2 playerPos, BSPTree tree) {
        BSPTree room = selectFarestRoom(playerPos, tree);
        BossPlacer.placeBoss(world, room);
    }

    private static BSPTree selectFarestRoom(Vector2 playerPos, BSPTree tree) {
        /* Recursion step: randomly dig into tree */
        if (tree.getLeftChild() != null && tree.getRightChild() != null) {
            float distA = getDistanceToPlayer(tree.getRightChild(), playerPos);
            float distB = getDistanceToPlayer(tree.getLeftChild(), playerPos);

            if (distA >= distB) {
                System.out.println("dist: " + distA);
                return tree.getRightChild();
            } else {
                System.out.println("dist: " + distB);
                return tree.getLeftChild();
            }

        } else if (tree.getLeftChild() != null) {
            return selectFarestRoom(playerPos, tree.getLeftChild());
        } else if (tree.getRightChild() != null) {
            return selectFarestRoom(playerPos, tree.getRightChild());
        }

        /* Finals step: return farest room */
        return tree;
    }

    public static float getDistanceToPlayer(BSPTree tree, Vector2 playerPos) {
        int x = tree.getStartX() + 1 + (tree.getWidth() / 2 - 1);
        int y = tree.getStartY() + 1 + (tree.getHeight() / 2 - 1);

        Vector2 roomPos = new Vector2(x, y);

        return roomPos.dst(playerPos);
    }

    private static void placeBoss(GameWorld world, BSPTree node) {
        node.setBossRoom(true);

        // Select center
        int x = node.getStartX() + 1 + (node.getWidth() / 2 - 1);
        int y = node.getStartY() + 1 + (node.getHeight() / 2 - 1);

        Entity e = BigMob.createMob(1, world, new Vector2((x + 0.5f) * GameWorld.TILE_SIZE, (y + 0.5f) * GameWorld.TILE_SIZE));
        if (e == null) {
            return;
        }
        
        world.addEntity(e);
    }
}
