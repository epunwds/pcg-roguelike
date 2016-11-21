package com.pcg.roguelike.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author cr0s
 */
public class SwordSwingProjectile extends Projectile {

    private static final float SPEED = 500.0f;
    private static final int DAMAGE = 80;
    private static final int LIFETIME = 20;
    
    public SwordSwingProjectile() {
    }

    @Override
    public Sprite getSprite() {
        return Projectile.projectileSprites[2];
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
