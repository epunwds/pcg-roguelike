package com.pcg.roguelike.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pcg.roguelike.world.GameWorld;

/**
 * Created by BugDeveloper on 17.11.2016.
 */
public class Play implements Screen {

    private BitmapFont font;
    private SpriteBatch batch2;
    private GameWorld gameWorld;

    private int playerClass;

    public Play(int playerClass) {
        this.playerClass = playerClass;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new Input());

        gameWorld = new GameWorld(playerClass);
        gameWorld.create();

        font = new BitmapFont();
        batch2 = new SpriteBatch();
    }

    private ShapeRenderer sr = new ShapeRenderer();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameWorld.render(delta);

        final int bossBarWidth = 180;

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int boxHeight = 24;
        int hp = gameWorld.getPlayerHp();
        int totalHp = gameWorld.getPlayerHpTotal();

        sr.setAutoShapeType(true);
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Filled);

        sr.setColor(Color.RED);
        sr.rect(0, 0, (hp / (float) (totalHp)) * width, boxHeight);
        sr.end();

        sr.begin();
        sr.set(ShapeType.Filled);

        if (gameWorld.isBossNearby()) {
            int bossHp = gameWorld.getBossHp();
            int bossTotal = gameWorld.getBossHpTotal();

            System.out.println("bossHp " + bossHp + " | bossTotal: " + bossTotal);

            sr.setColor(Color.MAGENTA);
            sr.rect(width / 2 - bossBarWidth / 2, height - boxHeight - 10, (bossHp / (float) (bossTotal)) * bossBarWidth, boxHeight);
        }

        sr.end();

        if (gameWorld.isBossNearby()) {
            int bossHp = gameWorld.getBossHp();
            int bossTotal = gameWorld.getBossHpTotal();
            
            batch2.begin();
            font.setColor(Color.WHITE);
            font.draw(batch2, "Boss: " + bossHp + " / " + bossTotal, width / 2 - 10 - bossBarWidth / 2, height - boxHeight - 10);
            batch2.end();
        }

        batch2.begin();
        font.setColor(Color.WHITE);
        font.draw(batch2, "FPS: " + Gdx.graphics.getFramesPerSecond() + " | HP: " + hp, 10, 20);
        batch2.end();
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

    @Override
    public void resize(int width, int height) {
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

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            gameWorld.stopShooting();

            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            shoot(screenX, screenY, button);

            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            shoot(screenX, screenY, 0);

            return false;
        }

        private void shoot(int screenX, int screenY, int btn) {
            Vector3 click = new Vector3(screenX, screenY, 0);

            Camera camera = gameWorld.getCamera();
            click = camera.unproject(click);

            Vector2 playerPos = gameWorld.getPlayerPos();
            Vector3 target = click.sub(new Vector3(playerPos.x, playerPos.y, 0));

            gameWorld.shoot(target);
        }

    }
}
