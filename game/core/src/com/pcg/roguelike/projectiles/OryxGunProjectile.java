package com.pcg.roguelike.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author cr0s
 */
public class OryxGunProjectile extends Projectile {

    private float SPEED = 1000.0f;
    private int damage = 35;
    private int LIFETIME = 60;
    
    public OryxGunProjectile() {
    }

    public OryxGunProjectile(int damage) {
        this.damage = damage;
    }    
    
    @Override
    public Sprite getSprite() {
        return Projectile.projectileSprites[3];
    }

    @Override
    public float getSpeed() {
        return SPEED;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getLifetimeTicks() {
        return LIFETIME;
    }
}
