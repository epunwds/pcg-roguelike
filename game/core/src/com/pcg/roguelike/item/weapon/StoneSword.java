
package com.pcg.roguelike.item.weapon;

import com.pcg.roguelike.projectiles.SwordSwingProjectile;

/**
 *
 * @author cr0s
 */
public class StoneSword extends Weapon {    
    private static final int SHOOTING_DELAY = 25;
    
    public StoneSword() {
        super("Stone Sword", new SwordSwingProjectile());
        this.setSprite(Weapon.itemSprites[2]);
    }

    @Override
    public int getShootingDelay() {
        return SHOOTING_DELAY;
    }
    
}
