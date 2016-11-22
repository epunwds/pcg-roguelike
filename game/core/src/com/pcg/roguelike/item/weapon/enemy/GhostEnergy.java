
package com.pcg.roguelike.item.weapon.enemy;

import com.pcg.roguelike.item.weapon.*;
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
public class GhostEnergy extends Weapon {    
    private static final int SHOOTING_DELAY = 25;
    private static final int DAMAGE = 15;
    
    public GhostEnergy() {
        super("Ghost Energy", new BlueEnergyProjectile(DAMAGE));
        this.setSprite(Weapon.itemSprites[0]);
    }

    @Override
    public int getShootingDelay() {
        return SHOOTING_DELAY;
    }
    
}
