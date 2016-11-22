package com.pcg.roguelike.entity.components.data;
import com.badlogic.ashley.core.Component;

/**
 *
 * @author Cr0s
 */
public class HealthComponent implements Component {
    public int hp;

    public HealthComponent(int hp) {
        this.hp = hp;
    }
}