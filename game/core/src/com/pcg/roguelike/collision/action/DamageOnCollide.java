package com.pcg.roguelike.collision.action;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.pcg.roguelike.collision.ICollisionAction;
import com.pcg.roguelike.entity.components.data.DDComponent;
import com.pcg.roguelike.entity.components.data.HealthComponent;

/**
 *
 * @author cr0s
 */
public class DamageOnCollide implements ICollisionAction {

    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private final ComponentMapper<DDComponent> dm = ComponentMapper.getFor(DDComponent.class);

    @Override
    public void onCollide(Entity a, Entity b) {
        /* Attacker has damage and victim has HP */
        if (dm.has(a) && hm.has(b)) {
            DDComponent ddc = dm.get(a);
            HealthComponent hc = hm.get(b);
            
            /* Reduce HP by damage */
            hc.hp = Math.max(0, hc.hp - ddc.damage);
        }
    }
}
