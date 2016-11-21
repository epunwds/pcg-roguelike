package com.pcg.roguelike.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pcg.roguelike.item.weapon.Weapon;

/**
 *
 * @author Cr0s
 */
public class WeaponComponent implements Component {

    public Weapon weapon;

    public WeaponComponent(Weapon weapon) {
        this.weapon = weapon;
    }
}
