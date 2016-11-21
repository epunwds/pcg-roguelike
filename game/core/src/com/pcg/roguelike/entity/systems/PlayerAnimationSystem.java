package com.pcg.roguelike.entity.systems;

/**
 *
 * @author cr0s
 */
import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.pcg.roguelike.entity.components.DirectionComponent;
import com.pcg.roguelike.entity.components.DirectionComponent.Direction;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.PlayerComponent;
import com.pcg.roguelike.entity.components.ShootingComponent;
import com.pcg.roguelike.entity.components.SpriteComponent;

public class PlayerAnimationSystem extends IteratingSystem {

    private ComponentMapper<ShootingComponent> shootingMapper;
    private ComponentMapper<SpriteComponent> spriteMapper;
    private ComponentMapper<DirectionComponent> dirMapper;
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    
    private Sprite[][] playerSprites;
    private Sprite[] playerShooting;

    private int walkingIndex;
    private int shootingIndex;
    
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
        
        MovementComponent mc = mm.get(entity);
        boolean isMoving = mc.isMoving;
        
        if (!isMoving) {
            walkingIndex = shootingIndex = 0;
            ticks = 0 ;
        } else {
            if (ticks++ >= FRAME_PERIOD) {
                ticks = 0;
                
                walkingIndex = (walkingIndex + 1) % playerSprites[0].length;
            }
        }
        
        //System.out.println("moving: " + isMoving + " | ticks: " + ticks + " | idx: " + walkingIndex);
        
        ShootingComponent shootingc = shootingMapper.get(entity);        
        if (shootingc != null && shootingc.isShooting) {
            /* Shooting animation frame lasts half of a delay between shots */
            if (shootingc.shotTicks <= shootingc.shootDelayTicks / 2) {
                sc.s = playerShooting[dir];
                return;
            } else {
                walkingIndex = 0;
            }
        }
        
        /* Walking animation */
        sc.s = playerSprites[dir][walkingIndex];
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
