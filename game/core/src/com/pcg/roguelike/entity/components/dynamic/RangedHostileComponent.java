package com.pcg.roguelike.entity.components.dynamic;
import com.badlogic.ashley.core.Component;

/**
 *
 * @author Cr0s
 */
public class RangedHostileComponent implements Component {
    public int sightRange, shootingRange;

    public RangedHostileComponent(int sightRange, int shootingRande) {
        this.sightRange = sightRange;
        this.shootingRange = shootingRande;
    }
}