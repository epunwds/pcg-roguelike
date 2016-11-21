package com.pcg.roguelike.projectiles;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.pcg.roguelike.collision.action.DamageOnCollide;
import com.pcg.roguelike.collision.action.DisappearOnCollide;
import com.pcg.roguelike.entity.components.data.LifetimeComponent;
import com.pcg.roguelike.entity.components.data.OwnerComponent;
import com.pcg.roguelike.entity.components.dynamic.CollisionActionsComponent;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.dynamic.MovementComponent;
import com.pcg.roguelike.entity.components.player.PlayerComponent;
import com.pcg.roguelike.entity.components.dynamic.SpeedComponent;
import com.pcg.roguelike.entity.components.visual.SpriteComponent;
import com.pcg.roguelike.item.weapon.Weapon;
import com.pcg.roguelike.world.GameWorld;

/**
 *
 * @author cr0s
 */
public abstract class Projectile {
    static final int NUM_PROJECTILES = 3;
    protected static Sprite[] projectileSprites;
    
    private final ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    
    static {
        loadSprites();
    }    
    
    public Projectile() {
    }
    
    public Entity createEntity(Entity owner, Vector2 position, Vector2 target) {
        Entity e = new Entity();
        
        e.add(new OwnerComponent(owner));
        e.add(new MovementComponent(target, true, true));
        
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;
        
        //polygon
        PolygonShape rectShape = new PolygonShape();
        rectShape.setAsBox(getSprite().getWidth() / 2, getSprite().getHeight() / 2);
        
        //fixture
        fixtureDef.shape = rectShape;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 1.0f;
        
        fixtureDef.isSensor = true;
          
        fixtureDef.filter.categoryBits = (pm.get(owner) != null) ? GameWorld.CATEGORY_PROJECTILE_PLAYER : GameWorld.CATEGORY_PROJECTILE_ENEMY;
        fixtureDef.filter.maskBits = (pm.get(owner) != null) ? GameWorld.MASK_PROJECTILE_PLAYER : GameWorld.MASK_PROJECTILE_ENEMY;
        
        e.add(new BodyComponent(null, bodyDef, fixtureDef));
        e.add(new SpeedComponent(getSpeed()));
        e.add(new SpriteComponent(getSprite(), 1));
        e.add(new LifetimeComponent(getLifetimeTicks()));
        e.add(new CollisionActionsComponent(new DamageOnCollide(), new DisappearOnCollide()));
        
        return e;
    }
    
    protected static void loadSprites() {
        projectileSprites = new Sprite[Projectile.NUM_PROJECTILES];

        Texture tex = new Texture(Gdx.files.internal("projectiles.png"));
        TextureRegion[][] split = TextureRegion.split(tex, 8, 8);

        for (int i = 0; i < projectileSprites.length; i++) {
            Sprite s = new Sprite(split[i][0]);
            s.setSize(16, 16);
            s.setOrigin(s.getWidth() / 2, s.getHeight() / 2);
            projectileSprites[i] = s;
        }
    }       
    
    public abstract Sprite getSprite();
    public abstract float getSpeed();
    public abstract int getDamage();
    public abstract int getLifetimeTicks();
}
