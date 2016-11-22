package com.pcg.roguelike.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author cr0s
 */
public class BlueEnergyProjectile extends Projectile {

    private float SPEED = 1000.0f;
    private int damage = 35;
    private int LIFETIME = 50;
    
    public BlueEnergyProjectile() {
    }

    public BlueEnergyProjectile(int damage) {
        this.damage = damage;
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
        return damage;
    }

    @Override
    public int getLifetimeTicks() {
        return LIFETIME;
    }
}
