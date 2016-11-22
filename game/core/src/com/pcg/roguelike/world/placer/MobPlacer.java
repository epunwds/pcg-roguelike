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
public class MobPlacer {

    public static void placeMobs(Random rnd, GameWorld world, BSPTree tree, int hardnessLevel) {
        traverseRooms(rnd, world, tree, hardnessLevel);
    }

    private static void traverseRooms(Random rnd, GameWorld world, BSPTree tree, int hardnessLevel) {
        if (tree.getLeftChild() != null && tree.getRightChild() != null) {
            traverseRooms(rnd, world, tree.getRightChild(), hardnessLevel);
            traverseRooms(rnd, world, tree.getLeftChild(), hardnessLevel);
        } else if (tree.getLeftChild() != null) {
            traverseRooms(rnd, world, tree.getLeftChild(), hardnessLevel);
        } else if (tree.getRightChild() != null) {
            traverseRooms(rnd, world, tree.getRightChild(), hardnessLevel);
        }

        placeMobsInRoom(rnd, world, tree, hardnessLevel);
    }

    private static void placeMobsInRoom(Random rnd, GameWorld world, BSPTree tree, int hardnessLevel) {
        /* Don't put mobs in spawn room */
        if (tree.isSpawnRoom()) {
            return;
        }

        for (int i = 0; i < hardnessLevel; i++) {
            int num = 1 + rnd.nextInt(2);

            for (int j = 0; j < num; j++) {
                int x = tree.getStartX() + 1 + (tree.getWidth() / 2 - 1);
                int y = tree.getStartY() + 1 + (tree.getHeight() / 2 - 1);

                Entity e = null;

                e = SmallMob.createMob(rnd.nextInt(SmallMob.NUM_MOBS), world, new Vector2((x + 0.5f) * GameWorld.TILE_SIZE, (y + 0.5f) * GameWorld.TILE_SIZE));
 
                if (e == null) {
                    continue;
                }

                world.addEntity(e);
            }
        }
    }
}
