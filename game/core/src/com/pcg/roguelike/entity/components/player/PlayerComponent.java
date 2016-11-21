package com.pcg.roguelike.entity.components.player;
import com.badlogic.ashley.core.Component;

/**
 *
 * @author Cr0s
 */
public class PlayerComponent implements Component {
    public int playerClass;

    public PlayerComponent(int playerClass) {
        this.playerClass = playerClass;
    }
}