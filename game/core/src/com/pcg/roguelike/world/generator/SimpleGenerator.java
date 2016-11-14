package com.pcg.roguelike.world.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.pcg.roguelike.world.World;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.styled.TilesetType;
import squidpony.squidmath.RNG;

public class SimpleGenerator implements ILevelGen {

    private Texture tiles;
    private char[][] decoDungeon, bareDungeon, lineDungeon, spaces;

    @Override
    public void generateLevel(World world) {
        TiledMap map = new TiledMap();

        DungeonGenerator dungeonGen = new DungeonGenerator(World.WIDTH, World.HEIGHT, new RNG());
        //uncomment this next line to randomly add water to the dungeon in pools.
        dungeonGen.addWater(15);
        dungeonGen.addGrass(20);

        //decoDungeon = dungeonGen.generate(TilesetType.REFERENCE_CAVES); // generate caves
        decoDungeon = dungeonGen.generate(TilesetType.ROOMS_AND_CORRIDORS_A);

        //getBareDungeon provides the simplest representation of the generated dungeon -- '#' for walls, '.' for floors.
        bareDungeon = dungeonGen.getBareDungeon();

        {
            tiles = new Texture(Gdx.files.internal("tiles.png"));
            TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
            MapLayers layers = map.getLayers();
            TiledMapTileLayer layer = new TiledMapTileLayer(World.WIDTH, World.HEIGHT, 32, 32);
            for (int x = 0; x < World.WIDTH; x++) {
                for (int y = 0; y < World.HEIGHT; y++) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                    switch (decoDungeon[x][y]) {
                        case '#':
                            cell.setTile(new StaticTiledMapTile(splitTiles[0][2]));
                            break;

                        case '~':
                            cell.setTile(new StaticTiledMapTile(splitTiles[0][1]));
                            break;

                        case '.':
                            cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
                            break;

                        case ',':
                            cell.setTile(new StaticTiledMapTile(splitTiles[1][2]));
                            break;

                        case '"':
                            cell.setTile(new StaticTiledMapTile(splitTiles[1][0]));
                            break;

                        default:
                            cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
                            System.out.println("Tile: " + decoDungeon[x][y]);
                            break;
                    }

                    layer.setCell(x, y, cell);
                }
            }

            layers.add(layer);
        }

        world.setMap(map);
    }

}
