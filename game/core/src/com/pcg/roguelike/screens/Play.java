package com.pcg.roguelike.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pcg.roguelike.world.GameWorld;
import com.pcg.roguelike.world.WorldRenderer;

/**
 * Created by BugDeveloper on 17.11.2016.
 */
public class Play implements Screen {

    private static final float TIMESTEP = 1/60f;
    private static final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private BitmapFont font;
    private SpriteBatch batch;
    private GameWorld gameWorld;
    private World world;
    private WorldRenderer worldRenderer;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Body hero;
    private Vector2 movement = new Vector2();
    private int heroSpeed = 5000;

    @Override
    public void show() {
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera.update();

        Gdx.input.setInputProcessor(new Input());

        gameWorld = new GameWorld(world);
        gameWorld.create();

        worldRenderer = new WorldRenderer(gameWorld);

        font = new BitmapFont();
        batch = new SpriteBatch();

        //HERO
        //body
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5 * GameWorld.TILE_SIZE, 5 * GameWorld.TILE_SIZE);

        //polygon
        PolygonShape rectShape = new PolygonShape();
        rectShape.setAsBox(8, 8);

        //fixture
        fixtureDef.shape = rectShape;


        //creating
        hero = world.createBody(bodyDef);
        hero.createFixture(fixtureDef);

        rectShape.dispose();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(hero.getPosition().x, hero.getPosition().y, 0);
        camera.update();

        worldRenderer.render(camera, batch);

        debugRenderer.render(world, camera.combined);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();

        hero.applyForceToCenter(movement, true);
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

    }

    public class Input extends InputAdapter {

        @Override
        public boolean keyDown(int keycode) {

            switch (keycode) {
                case Keys.W:
                    movement.y = heroSpeed;
                    break;

                case Keys.A:
                    movement.x = -heroSpeed;
                    break;

                case Keys.S:
                    movement.y = -heroSpeed;
                    break;

                case Keys.D:
                    movement.x = heroSpeed;
                    break;
            }
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
            return true;
        }
    }
}
