package com.pcg.roguelike.entity.components.data;
import com.badlogic.ashley.core.Component;

/**
 *
 * @author Cr0s
 */
public class LifetimeComponent implements Component {
    public int lifetimeTicks;

    public LifetimeComponent(int lifetimeTicks) {
        this.lifetimeTicks = lifetimeTicks;
    }
}