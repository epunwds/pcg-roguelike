package com.pcg.roguelike.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pcg.roguelike.entity.components.BodyComponent;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.SpriteComponent;
import com.pcg.roguelike.world.GameWorld;

/**
 * Created by BugDeveloper on 17.11.2016.
 */
public class Play implements Screen {
    

    private BitmapFont font;
    private SpriteBatch batch2;
    private GameWorld gameWorld;
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new Input());

        gameWorld = new GameWorld();
        gameWorld.create();

        font = new BitmapFont();
        batch2 = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameWorld.render(delta);

        batch2.begin();
        font.setColor(Color.WHITE);
        font.draw(batch2, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch2.end();  
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameWorld.getBox2dWorld().dispose();
    }

    public class Input extends InputAdapter {

        private final Vector2 movement = new Vector2(0, 0);
        
        @Override
        public boolean keyDown(int keycode) {


            switch (keycode) {
                case Keys.W:
                    movement.y = 1;
                    break;

                case Keys.A:
                    movement.x = -1;
                    break;

                case Keys.S:
                    movement.y = -1;
                    break;

                case Keys.D:
                    movement.x = 1;
                    break;
            }
            
            /* Movement has occurred */
            gameWorld.movePlayer(movement);
            
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Keys.W:
                case Keys.S:
                    movement.y = 0;
                    break;

                case Keys.A:
                case Keys.D:
                    movement.x = 0;
                    break;
            }
            
            gameWorld.movePlayer(movement);
            
            return true;
        }
    }
}