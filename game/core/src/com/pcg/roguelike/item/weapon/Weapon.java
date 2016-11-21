package com.pcg.roguelike.item.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pcg.roguelike.item.Item;
import com.pcg.roguelike.projectiles.Projectile;


public class Weapon extends Item {
    static final int NUM_WEAPONS = 4;
    protected static Sprite[] itemSprites;
    
    static {
        loadSprites();
    }
    
    private Projectile projectile;
    
    public Weapon(String name, Projectile projectile) {
        super(name);
        
        this.projectile = projectile;
    }

    public Projectile getProjectile() {
        return projectile;
    }
    
    protected static void loadSprites() {
        itemSprites = new Sprite[Weapon.NUM_WEAPONS];

        Texture tex = new Texture(Gdx.files.internal("weapon_items.png"));
        TextureRegion[][] split = TextureRegion.split(tex, 8, 8);

        for (int i = 0; i < itemSprites.length; i++) {
            Sprite s = new Sprite(split[0][i]);
            s.setSize(16, 16);
            s.setOrigin(s.getWidth() / 2, s.getHeight() / 2);
            itemSprites[i] = s;
        }
    }    
}
