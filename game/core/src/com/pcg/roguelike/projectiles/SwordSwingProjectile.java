package com.pcg.roguelike.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author cr0s
 */
public class SwordSwingProjectile extends Projectile {

    private float speed = 500.0f;
    private int damage = 80;
    private int lifetime = 20;
    
    public SwordSwingProjectile() {
    }
    
    public SwordSwingProjectile(int damage) {
        this.damage = damage;
    }

    @Override
    public Sprite getSprite() {
        return Projectile.projectileSprites[2];
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
