package com.pcg.roguelike.entity.systems;

/**
 *
 * @author cr0s
 */

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pcg.roguelike.entity.components.data.WeaponComponent;
import com.pcg.roguelike.entity.components.physics.DirectionComponent;
import com.pcg.roguelike.entity.components.dynamic.MovementComponent;
import com.pcg.roguelike.entity.components.player.PlayerComponent;
import com.pcg.roguelike.entity.components.dynamic.ShootingComponent;
import com.pcg.roguelike.entity.components.visual.SpriteComponent;

public class PlayerAnimationSystem extends IteratingSystem {

    private ComponentMapper<ShootingComponent> shootingMapper;
    private ComponentMapper<SpriteComponent> spriteMapper;
    private ComponentMapper<DirectionComponent> dirMapper;
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
    
    private Sprite[][] playerSprites;
    private Sprite[] playerShooting;

    private int walkingIndex;
    
    private long ticks;
    
    private final long FRAME_PERIOD = 5;
    
    public PlayerAnimationSystem() {
        super(Family.all(PlayerComponent.class).get());

        this.shootingMapper = ComponentMapper.getFor(ShootingComponent.class);
        this.spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        this.dirMapper = ComponentMapper.getFor(DirectionComponent.class);
        
        loadPlayerSprites();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sc = spriteMapper.get(entity);
        int dir = dirMapper.get(entity).dir.getDirectionNum();
        
        WeaponComponent wc = wm.get(entity);
        int shootingDelayTicks = wc.weapon.getShootingDelay();
        
        MovementComponent mc = mm.get(entity);
        boolean isMoving = mc.isMoving;
        
        PlayerComponent pc = pm.get(entity);
        int playerClass = pc.playerClass;
        
        if (!isMoving) {
            walkingIndex = 0;
            ticks = 0 ;
        } else {
            if (ticks++ >= FRAME_PERIOD) {
                ticks = 0;
                
                walkingIndex = (walkingIndex + 1) % playerSprites[0].length;
            }
        }
        
        ShootingComponent shootingc = shootingMapper.get(entity);        
        if (shootingc != null && shootingc.isShooting) {
            /* Shooting animation frame lasts half of a delay between shots */
            if (shootingc.shotTicks <= shootingDelayTicks / 2) {
                sc.s = playerShooting[4 * playerClass + dir];
                return;
            } else {
                walkingIndex = 0;
            }
        }
        
        /* Walking animation */
        sc.s = playerSprites[4 * playerClass + dir][walkingIndex];
    }
    
    private void loadPlayerSprites() {
        // Player walking textures        
        Texture tex = new Texture(Gdx.files.internal("player.png"));
        TextureRegion[][] splitPlayerWalking = TextureRegion.split(tex, 8, 8);

        this.playerSprites = new Sprite[8][];

        for (int i = 0; i < this.playerSprites.length; i++) {
            this.playerSprites[i] = new Sprite[3];

            for (int j = 0; j < this.playerSprites[0].length; j++) {
                Sprite s = new Sprite(splitPlayerWalking[i][j]);
                s.setSize(24, 24);
                s.setOrigin(s.getWidth() / 2, s.getHeight() / 2);

                //s.setScale(3);
                this.playerSprites[i][j] = s;
            }
        }

        // Player shooting textures
        this.playerShooting = new Sprite[8];

        Texture tex2 = new Texture(Gdx.files.internal("player_shooting.png"));
        TextureRegion[][] splitPlayerShooting = TextureRegion.split(tex2, 16, 8);

        for (int i = 0; i < this.playerSprites.length; i++) {
            Sprite s = new Sprite(splitPlayerShooting[i][0]);
            s.setSize(48, 24);
            s.setOrigin(s.getWidth() / 2, s.getHeight() / 2);


            this.playerShooting[i] = s;
        }
    }
}
