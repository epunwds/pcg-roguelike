
package com.pcg.roguelike.item.weapon.enemy;

import com.pcg.roguelike.item.weapon.*;
import com.pcg.roguelike.projectiles.OverseerGunProjectile;

/**
 *
 * @author cr0s
 */
public class OverseerGun extends Weapon {    
    private static final int SHOOTING_DELAY = 10;
    private static final int DAMAGE = 15;
    
    public OverseerGun() {
        super("Ghost Energy", new OverseerGunProjectile(DAMAGE));
        this.setSprite(Weapon.itemSprites[1]);
    }

    @Override
    public int getShootingDelay() {
        return SHOOTING_DELAY;
    }
    
}
