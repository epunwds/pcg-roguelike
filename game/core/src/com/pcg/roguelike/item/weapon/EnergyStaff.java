
package com.pcg.roguelike.item.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pcg.roguelike.item.Item;
import com.pcg.roguelike.projectiles.BlueEnergyProjectile;
import com.pcg.roguelike.projectiles.Projectile;

/**
 *
 * @author cr0s
 */
public class EnergyStaff extends Weapon {    
    private static final int SHOOTING_DELAY = 20;
    
    public EnergyStaff() {
        super("Energy Staff", new BlueEnergyProjectile());
        this.setSprite(Weapon.itemSprites[0]);
    }

    @Override
    public int getShootingDelay() {
        return SHOOTING_DELAY;
    }
    
}
