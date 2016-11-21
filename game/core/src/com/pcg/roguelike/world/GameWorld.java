package com.pcg.roguelike.world;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pcg.roguelike.entity.components.BodyComponent;
import com.pcg.roguelike.entity.components.DirectionComponent;
import com.pcg.roguelike.entity.components.DirectionComponent.Direction;
import com.pcg.roguelike.entity.components.MovementComponent;
import com.pcg.roguelike.entity.components.PlayerComponent;
import com.pcg.roguelike.entity.components.ShootingComponent;
import com.pcg.roguelike.entity.components.SpeedComponent;
import com.pcg.roguelike.entity.components.SpriteComponent;
import com.pcg.roguelike.entity.components.WeaponComponent;
import com.pcg.roguelike.entity.systems.DirectionSystem;
import com.pcg.roguelike.entity.systems.MovementSystem;
import com.pcg.roguelike.entity.systems.PhysicsSystem;
import com.pcg.roguelike.entity.systems.PlayerAnimationSystem;
import com.pcg.roguelike.entity.systems.RenderingSystem;
import com.pcg.roguelike.entity.systems.ShootingSystem;
import com.pcg.roguelike.item.weapon.EnergyStaff;
import com.pcg.roguelike.item.weapon.Weapon;
import com.pcg.roguelike.world.generator.BSPGenerator;
import com.pcg.roguelike.world.generator.TextureGenerator;

import java.util.Random;

/**
 * Created by BugDeveloper on 16.11.2016.
 */
public class GameWorld {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int TILE_SIZE = 32;
    public static final int TEXTURE_ANTIALIASING = 3;
    public static final int TEXTURE_VARIATIONS = 10;
    public static final int PIXEL_TO_METERS = 2;
    
    public static final short CATEGORY_PLAYER = 1; 
    public static final short CATEGORY_WALL = 1 << 1;
    public static final short CATEGORY_PROJECTILE_PLAYER = 1 << 2;
    public static final short CATEGORY_PROJECTILE_ENEMY = 1 << 3;
    public static final short CATEGORY_ENEMY = 1 << 5; 
    
    public static final short MASK_PLAYER = CATEGORY_WALL | CATEGORY_PROJECTILE_ENEMY;
    public static final short MASK_MONSTER = CATEGORY_WALL | CATEGORY_PLAYER | CATEGORY_PROJECTILE_PLAYER;
    public static final short MASK_PROJECTILE = CATEGORY_WALL;
    public static final short MASK_PROJECTILE_ENEMY = MASK_PROJECTILE | CATEGORY_PLAYER;
    public static final short MASK_PROJECTILE_PLAYER = MASK_PROJECTILE | CATEGORY_ENEMY;
    
    private BSPGenerator gen;
    private TextureGenerator texGen;
    private TiledMap map;
    private Random rand;

    private World world;
    private Engine engine;

    private TiledMapRenderer mapRenderer;
    private Entity player;

    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private ComponentMapper<SpriteComponent> sc = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<ShootingComponent> sm = ComponentMapper.getFor(ShootingComponent.class);

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    private int playerClass = 0;
    
    public TiledMap getMap() {
        return map;
    }

    public World getBox2dWorld() {
        return world;
    }

    public GameWorld() {
        rand = new Random(System.currentTimeMillis());
        this.debugRenderer = new Box2DDebugRenderer();
        this.world = new World(new Vector2(0, 0), true);
        texGen = new TextureGenerator(TILE_SIZE, TILE_SIZE, rand);
        gen = new BSPGenerator(WIDTH, HEIGHT, 5, 3, rand);

        this.engine = new Engine();
        PhysicsSystem physicsSystem = new PhysicsSystem(this);

        this.engine.addSystem(physicsSystem);
        this.engine.addEntityListener(Family.all(BodyComponent.class).get(), physicsSystem);
        this.engine.addSystem(new MovementSystem());
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.camera.update();

        this.engine.addSystem(new RenderingSystem(this.batch, this.camera));
        this.engine.addSystem(new DirectionSystem());
        this.engine.addSystem(new PlayerAnimationSystem());
        this.engine.addSystem(new ShootingSystem(this));
    }

    public void create() {
        int[][] mapBlueprint = gen.generateMap();

        /* Tiles textures */
        TextureRegion[] groundTiles = new TextureRegion[TEXTURE_VARIATIONS];
        TextureRegion[] wallTiles = new TextureRegion[TEXTURE_VARIATIONS];

        /* Texture generation */
        for (int i = 0; i < TEXTURE_VARIATIONS; i++) {
            groundTiles[i] = new TextureRegion(texGen.generateTexture(Color.LIGHT_GRAY, Color.DARK_GRAY, TEXTURE_ANTIALIASING));
            wallTiles[i] = new TextureRegion(texGen.generateTexture(Color.GRAY, Color.BLACK, TEXTURE_ANTIALIASING));
        }

        /* Creating TiledMap and filling it with tiles corresponding to the blueprint */
        TiledMap tiledMap = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(WIDTH, HEIGHT, TILE_SIZE, TILE_SIZE);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                switch (mapBlueprint[x][y]) {
                    //wall
                    case 0: {
                        StaticTiledMapTile tile = new StaticTiledMapTile(wallTiles[rand.nextInt(TEXTURE_VARIATIONS)]);
                        tile.setId(0);
                        cell.setTile(tile);
                        createWall(x, y);
                    }
                    break;

                    //ground
                    case 1: {
                        StaticTiledMapTile tile = new StaticTiledMapTile(groundTiles[rand.nextInt(TEXTURE_VARIATIONS)]);
                        tile.setId(1);
                        cell.setTile(tile);
                    }
                    break;
                }
                layer.setCell(x, y, cell);
            }
        }

        tiledMap.getLayers().add(layer);
        map = tiledMap;
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        createPlayer();
    }

    private void createWall(int x, int y) {
        BodyDef bodyDef;
        PolygonShape rectShape;
        FixtureDef fixtureDef;

        //variables for reusing
        bodyDef = new BodyDef();
        rectShape = new PolygonShape();
        rectShape.setAsBox(TILE_SIZE / PIXEL_TO_METERS, TILE_SIZE / PIXEL_TO_METERS);
        fixtureDef = new FixtureDef();

        // wall body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;

        //wall fixture definition
        fixtureDef.shape = rectShape;
        fixtureDef.friction = .2f;
        fixtureDef.restitution = 0;

        fixtureDef.filter.categoryBits = CATEGORY_WALL;
        
        bodyDef.position.set((x + 0.5f) * TILE_SIZE, (y + 0.5f) * TILE_SIZE);

        Entity wall = new Entity();
        wall.add(new BodyComponent(null, bodyDef, fixtureDef));

        this.engine.addEntity(wall);
    }

    private void createPlayer() {
        //HERO
        //body
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5 * GameWorld.TILE_SIZE, 5 * GameWorld.TILE_SIZE);
        bodyDef.fixedRotation = true;
        
        //polygon
        PolygonShape rectShape = new PolygonShape();
        rectShape.setAsBox(24 / PIXEL_TO_METERS, 24 / PIXEL_TO_METERS);

        //fixture
        fixtureDef.shape = rectShape;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.25f;
        fixtureDef.filter.categoryBits = CATEGORY_PLAYER;
        fixtureDef.filter.maskBits = MASK_PLAYER;
        
        //initialization
        this.player = new Entity();
        player.add(new PlayerComponent());
        player.add(new BodyComponent(null, bodyDef, fixtureDef));

        player.add(new SpriteComponent(null, 0));
        player.add(new MovementComponent(new Vector2(0, 0)));
        player.add(new SpeedComponent(500f));
        player.add(new ShootingComponent(null, 15));
        player.add(new DirectionComponent(Direction.DOWN));
        player.add(new WeaponComponent(new EnergyStaff()));
        
        engine.addEntity(player);
    }

    public void render(float delta) {
        Body b = bm.get(player).body;
        if (b != null) {
            Vector2 playerPos = b.getPosition();
            camera.position.set(playerPos.x, playerPos.y, 0);
        }
        
        camera.update();
        
        if (mapRenderer != null) {
            mapRenderer.setView(camera);
            mapRenderer.render();
        }
        
        this.engine.update(delta);
        debugRenderer.render(this.world, camera.combined);
    }

    public void addEntity(Entity e) {
        this.engine.addEntity(e);
    }

    public void movePlayer(Vector2 movement) {
        if (movement.isZero(1f)) {
            mm.get(player).isMoving = false;
            return;
        }
        
        mm.get(player).isMoving = true;
        
        Vector2 m = mm.get(player).movement;
        m.x = movement.x;
        m.y = movement.y;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Vector2 getPlayerPos() {
        BodyComponent bc = bm.get(player);
        return bc.body.getPosition().cpy();
    }

    public void shoot(Vector3 target) {
        ShootingComponent s = sm.get(player);
        s.target = target;
    }

    public void stopShooting() {
        ShootingComponent s = sm.get(player);
        s.target = null;
    }
}
