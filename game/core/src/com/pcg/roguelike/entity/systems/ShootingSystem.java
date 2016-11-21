package com.pcg.roguelike.entity.systems;

/**
 *
 * @author cr0s
 */

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.dynamic.ShootingComponent;
import com.pcg.roguelike.entity.components.data.WeaponComponent;
import com.pcg.roguelike.item.weapon.Weapon;
import com.pcg.roguelike.world.GameWorld;
import static com.pcg.roguelike.world.GameWorld.PIXELS_TO_METERS;

public class ShootingSystem extends IteratingSystem {
    
    private ComponentMapper<ShootingComponent> sm = ComponentMapper.getFor(ShootingComponent.class);
    private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    
     public GameWorld world;
     
    public ShootingSystem(GameWorld world) {
        super(Family.all(ShootingComponent.class).get());
        
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ShootingComponent sc = sm.get(entity);
        
        if (sc == null)
            return;
        
        sc.isShooting = (sc.target != null);
        
        if (!sc.isShooting) {
            sc.shotTicks = 0;
            return;
        }
        
        /* First shot without a delay */
        if (sc.shotTicks++ == 0) {
            doShot(entity, new Vector2(sc.target.x * PIXELS_TO_METERS, sc.target.y * PIXELS_TO_METERS));
        } else if (sc.shotTicks++ >= sc.shootDelayTicks) { /* Next shots are with delay between */
            sc.shotTicks = 0;
        }
    }

    private void doShot(Entity entity, Vector2 target) {
        Weapon weapon = wm.get(entity).weapon;
        Body body = bm.get(entity).body;
        
        if (body != null) {
            Vector2 pos = body.getPosition();
            Entity e = weapon.getProjectile().createEntity(entity, pos, target);
            
            world.addEntity(e);
        }
    }
}
