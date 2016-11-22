package com.pcg.roguelike.collision.action;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.pcg.roguelike.collision.ICollisionAction;
import com.pcg.roguelike.entity.components.data.BossComponent;
import com.pcg.roguelike.entity.components.data.DDComponent;
import com.pcg.roguelike.entity.components.data.HealthComponent;
import com.pcg.roguelike.entity.components.physics.BodyComponent;
import com.pcg.roguelike.entity.components.player.PlayerComponent;
import com.pcg.roguelike.screens.GameOver;
import com.pcg.roguelike.screens.GameWin;
import com.pcg.roguelike.screens.MenuSelectClass;
import com.pcg.roguelike.world.GameWorld;

/**
 *
 * @author cr0s
 */
public class DamageOnCollide implements ICollisionAction {

    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private final ComponentMapper<DDComponent> dm = ComponentMapper.getFor(DDComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private final ComponentMapper<BossComponent> bom = ComponentMapper.getFor(BossComponent.class);
    
    @Override
    public void onCollide(Entity a, Entity b) {
        Entity attacker = dm.has(a) ? a : (dm.has(b)) ? b : null;
        Entity victim = hm.has(a) ? a : (hm.has(b)) ? b : null;

        if (attacker == null || victim == null) {
            return;
        }

        /* Attacker has damage and victim has HP */
        DDComponent ddc = dm.get(attacker);
        HealthComponent hc = hm.get(victim);

        System.out.println("Reducing hp by " + ddc.damage + " | left: " + hc.hp);

        /* Reduce HP by damage */
        hc.hp = Math.max(0, hc.hp - ddc.damage);
        
        Body body = bm.get(victim).body;
        if (body != null) {
            Vector2 pos = new Vector2(body.getPosition().x / GameWorld.TILE_SIZE, body.getPosition().y / GameWorld.TILE_SIZE);
            GameWorld.instance.spawnText("-" + ddc.damage, pos, Color.RED);
        }
        
        /* Player dead */
        if (hc.hp == 0 && pm.has(victim)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
        }
        
        /* Boss dead */
        if (hc.hp == 0 && bom.has(victim)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameWin());
        }
    }
}
