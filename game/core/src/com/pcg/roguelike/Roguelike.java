/**
 * *****************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *****************************************************************************
 */
package com.pcg.roguelike;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.pcg.roguelike.world.World;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.styled.TilesetType;
import squidpony.squidmath.RNG;

public class Roguelike extends ApplicationAdapter {

    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    
    private BitmapFont font;
    private SpriteBatch batch;    
    
    private World world;
    
    @Override
    public void create() {
        world = new World();
        
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        Gdx.input.setInputProcessor(new OrthoCamController(camera));

        font = new BitmapFont();
        batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(world.getMap());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }

    public class OrthoCamController extends InputAdapter {

        final OrthographicCamera camera;
        final Vector3 curr = new Vector3();
        final Vector3 last = new Vector3(-1, -1, -1);
        final Vector3 delta = new Vector3();

        public OrthoCamController(OrthographicCamera camera) {
            this.camera = camera;
        }

        @Override
        public boolean touchDragged(int x, int y, int pointer) {
            camera.unproject(curr.set(x, y, 0));
            if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
                camera.unproject(delta.set(last.x, last.y, 0));
                delta.sub(curr);
                camera.position.add(delta.x, delta.y, 0);
            }
            last.set(x, y, 0);
            return false;
        }

        @Override
        public boolean touchUp(int x, int y, int pointer, int button) {
            last.set(-1, -1, -1);
            return false;
        }
    }
}
