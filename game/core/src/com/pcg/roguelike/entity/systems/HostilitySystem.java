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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.dynamic.ShootingComponent;
import com.pcg.roguelike.entity.components.data.WeaponComponent;
import com.pcg.roguelike.entity.components.dynamic.MovementComponent;
import com.pcg.roguelike.entity.components.dynamic.RangedHostileComponent;
import com.pcg.roguelike.entity.components.dynamic.SpeedComponent;
import com.pcg.roguelike.item.weapon.Weapon;
import com.pcg.roguelike.world.GameWorld;

public class HostilitySystem extends IteratingSystem {

    private ComponentMapper<ShootingComponent> sm = ComponentMapper.getFor(ShootingComponent.class);
    private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
    private final ComponentMapper<SpeedComponent> scm = ComponentMapper.getFor(SpeedComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<RangedHostileComponent> rhm = ComponentMapper.getFor(RangedHostileComponent.class);
    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public GameWorld world;

    private boolean isDirectSight;

    public HostilitySystem(GameWorld world) {
        super(Family.all(ShootingComponent.class, SpeedComponent.class, WeaponComponent.class, RangedHostileComponent.class).get());

        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        float speed = scm.get(entity).speed;

        Vector2 playerPos = world.getPlayerPos();
        Vector2 ourPos = bm.get(entity).body.getPosition();

        RangedHostileComponent rhc = rhm.get(entity);
        int sightRange = rhc.sightRange;
        int shootingRange = rhc.shootingRange;

        float distance = playerPos.dst(ourPos);

        //System.out.println("Raycasting...");
        this.isDirectSight = false;
        if (distance > 1f) {
            world.getBox2dWorld().rayCast(new RayCastCallback() {

                @Override
                public float reportRayFixture(Fixture fxtr, Vector2 vctr, Vector2 vctr1, float f) {
                    // System.out.println("Seen fixture: " + fxtr.getFilterData().categoryBits);

                    if (fxtr.getFilterData().categoryBits == GameWorld.CATEGORY_PLAYER) {
                        HostilitySystem.this.isDirectSight = true;

                        return 0; /* Stop on player */

                    } else {
                        HostilitySystem.this.isDirectSight = false;
                    }

                    /* Stop on walls */
                    if (fxtr.getFilterData().categoryBits == GameWorld.CATEGORY_WALL) {
                        return 0;
                    }

                    return -1; /* Filter anything else */

                }

            }, ourPos, playerPos);
        }
        
        Vector2 target = playerPos.sub(ourPos);

        if (distance < shootingRange && isDirectSight) {
            sm.get(entity).target = new Vector3(target.x, target.y, 0);
            sm.get(entity).isShooting = true;

            stop(entity);
        } else {
            sm.get(entity).target = null;
            sm.get(entity).isShooting = false;

            /* Move closer */
            if (distance < sightRange && isDirectSight) {
                Vector2 movement = target.cpy().setLength(speed);
                MovementComponent mc = mm.get(entity);

                mc.canRotate = false;
                mc.isMoving = true;
                mc.movement = movement;
            } else {
                stop(entity);
            }
        }
    }

    private void stop(Entity entity) {
        MovementComponent mc = mm.get(entity);

        mc.canRotate = false;
        mc.isMoving = false;
        mc.movement = Vector2.Zero;
    }
}
