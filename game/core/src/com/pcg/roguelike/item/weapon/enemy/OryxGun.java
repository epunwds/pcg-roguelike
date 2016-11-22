
package com.pcg.roguelike.item.weapon.enemy;

import com.pcg.roguelike.item.weapon.*;
import com.pcg.roguelike.projectiles.OryxGunProjectile;
import com.pcg.roguelike.projectiles.SwordSwingProjectile;

/**
 *
 * @author cr0s
 */
public class OryxGun extends Weapon {    
    private static final int SHOOTING_DELAY = 80;
    private static final int DAMAGE = 50;
    
    public OryxGun() {
        super("Oryx Gun", new OryxGunProjectile(DAMAGE));
        this.setSprite(Weapon.itemSprites[1]);
    }

    @Override
    public int getShootingDelay() {
        return SHOOTING_DELAY;
    }
    
}
