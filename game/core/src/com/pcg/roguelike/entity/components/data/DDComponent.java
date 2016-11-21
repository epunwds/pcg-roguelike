package com.pcg.roguelike.entity.components.data;
import com.badlogic.ashley.core.Component;

/**
 * Damage dealer component.
 * @author Cr0s
 */
public class DDComponent implements Component {
    public int damage;

    public DDComponent(int damage) {
        this.damage = damage;
    }
}