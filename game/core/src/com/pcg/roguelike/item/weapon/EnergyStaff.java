
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
    public EnergyStaff() {
        super("Energy Staff", new BlueEnergyProjectile());
        this.setSprite(Weapon.itemSprites[0]);
    }
    
}
