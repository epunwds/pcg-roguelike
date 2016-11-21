package com.pcg.roguelike.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author cr0s
 */
public class BlueEnergyProjectile extends Projectile {

    private static final float SPEED = 1000.0f;
    private static final int DAMAGE = 35;
    private static final int LIFETIME = 50;
    
    public BlueEnergyProjectile() {
    }

    @Override
    public Sprite getSprite() {
        return Projectile.projectileSprites[1];
    }

    @Override
    public float getSpeed() {
        return SPEED;
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }

    @Override
    public int getLifetimeTicks() {
        return LIFETIME;
    }
}
