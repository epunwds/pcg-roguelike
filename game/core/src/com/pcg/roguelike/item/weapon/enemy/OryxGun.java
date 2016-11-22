
package com.pcg.roguelike.item.weapon.enemy;

import com.pcg.roguelike.item.weapon.*;
import com.pcg.roguelike.projectiles.SwordSwingProjectile;

/**
 *
 * @author cr0s
 */
public class SmallSword extends Weapon {    
    private static final int SHOOTING_DELAY = 55;
    private static final int DAMAGE = 10;
    
    public SmallSword() {
        super("Small Sword", new SwordSwingProjectile(DAMAGE));
        this.setSprite(Weapon.itemSprites[2]);
    }

    @Override
    public int getShootingDelay() {
        return SHOOTING_DELAY;
    }
    
}
