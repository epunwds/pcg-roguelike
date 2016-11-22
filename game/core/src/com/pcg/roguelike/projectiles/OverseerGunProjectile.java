package com.pcg.roguelike.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author cr0s
 */
public class OverseerGunProjectile extends Projectile {

    private float speed = 500.0f;
    private int damage = 80;
    private int lifetime = 60;
    
    public OverseerGunProjectile() {
    }
    
    public OverseerGunProjectile(int damage) {
        this.damage = damage;
    }

    @Override
    public Sprite getSprite() {
        return Projectile.projectileSprites[4];
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getLifetimeTicks() {
        return lifetime;
    }
}
