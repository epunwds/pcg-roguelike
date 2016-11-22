package com.pcg.roguelike.entity.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.pcg.roguelike.entity.components.data.HealthComponent;
import com.pcg.roguelike.entity.components.data.WeaponComponent;
import com.pcg.roguelike.entity.components.dynamic.RangedHostileComponent;
import com.pcg.roguelike.entity.components.dynamic.MovementComponent;
import com.pcg.roguelike.entity.components.dynamic.ShootingComponent;
import com.pcg.roguelike.entity.components.dynamic.SpeedComponent;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.visual.SpriteComponent;
import com.pcg.roguelike.item.weapon.Weapon;
import com.pcg.roguelike.item.weapon.enemy.GhostEnergy;
import com.pcg.roguelike.item.weapon.enemy.SmallSword;
import com.pcg.roguelike.projectiles.Projectile;
import com.pcg.roguelike.world.GameWorld;

/**
 *
 * @author cr0s
 */
public class SmallMob {
    public static int NUM_MOBS = 2;
    
    static Sprite[] sprites;
    
    private static final SmallMob[] mobs = {
        new SmallMob("Dwarf Warrior", 0, 35, 500, 2, 5, new SmallSword()),
        new SmallMob("Blue Ghost", 1, 35, 250, 3, 5, new GhostEnergy()),
    };
    
    static {
        loadSprites();
    }

    private String name;
    private int spriteNum;
    private int hp;
    private int speed;
    private int shootingRange;
    private int sightRange;
    private Weapon weapon;

    public SmallMob(String name, int spriteNum, int hp, int speed, int shootingRange, int sightRange, Weapon weapon) {
        this.name = name;
        this.spriteNum = spriteNum;
        this.hp = hp;
        this.speed = speed;
        this.shootingRange = shootingRange;
        this.sightRange = sightRange;
        this.weapon = weapon;
    }
    
    private static void loadSprites() {
        sprites = new Sprite[SmallMob.NUM_MOBS];

        Texture tex = new Texture(Gdx.files.internal("enemies.png"));
        TextureRegion[][] split = TextureRegion.split(tex, 8, 8);

        sprites[0] = new Sprite(split[4][3]);
        sprites[1] = new Sprite(split[7][7]);
        
        /* Scale sprites */
        for (Sprite sprite : sprites) {
            sprite.setSize(16, 16);
        }
    }
    
    public static Entity createMob(int mobIndex, GameWorld world, Vector2 position) {
        SmallMob mob = mobs[mobIndex];
        Sprite sprite = sprites[mob.spriteNum];
        
        Entity e = new Entity();
        
        e.add(new MovementComponent(new Vector2()));
        e.add(new HealthComponent(mob.hp));
        e.add(new ShootingComponent(null));
        e.add(new WeaponComponent(mob.weapon));
        e.add(new SpeedComponent(mob.speed));
        
        e.add(new RangedHostileComponent(mob.sightRange * GameWorld.TILE_SIZE, mob.shootingRange * GameWorld.TILE_SIZE));
        
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;
        
        //polygon
        PolygonShape rectShape = new PolygonShape();
        rectShape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        
        //fixture
        fixtureDef.shape = rectShape;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 1.0f;
          
        fixtureDef.filter.categoryBits = GameWorld.CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = GameWorld.MASK_MONSTER;
        
        e.add(new BodyComponent(null, bodyDef, fixtureDef));        
        e.add(new SpriteComponent(sprite, 1));
        
        System.out.println("Created mob: " + mob.name);
        
        return e;
    }
}
