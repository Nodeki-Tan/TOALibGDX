package dev.fenixsoft.toa.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.mapData.Tile;

import java.util.ArrayList;
import java.util.List;

public class TileManager {

    public static final List<Tile> TILE_LIST = new ArrayList<Tile>();

    public static  void loadTiles(){

        int index = 0;

        for (int i = 0; i < 8; i++) {
            if (i <= 5) {
                TILE_LIST.add(i, new Tile(
                        false,
                        false,
                        false,
                        false,
                        (byte)0,
                        0,
                        0,
                        0,
                        Vector3.Zero,
                        AssetManager.entityAtlas.findRegions("Tiles/Overworld/Tile").get(i)
                ));
            } else if (i == 7) {
                TILE_LIST.add(i, new Tile(
                        false,
                        false,
                        true,
                        true,
                        (byte)2,
                        0,
                        0,
                        0,
                        Vector3.Zero,
                        AssetManager.entityAtlas.findRegions("Tiles/Overworld/Tile").get(i)
                ));
            } else if (i == 6) {
                TILE_LIST.add(i, new Tile(
                        false,
                        true,
                        true,
                        true,
                        (byte)1,
                        0,
                        0,
                        0,
                        Vector3.Zero,
                        AssetManager.entityAtlas.findRegions("Tiles/Overworld/Dirt").get(1)
                ));
            }

            /*if (i == 3) {
                TILE_LIST.add(i, new Tile(
                        false,
                        false,
                        false,
                        true,
                        (byte)1,
                        0,
                        0,
                        0,
                        Vector3.Zero,
                        AssetManager.entityAtlas.findRegions("Tiles/Overworld/Sand").get(1)
                ));
            }*/
            if (i == 2) {
                TILE_LIST.add(i, new Tile(
                        false,
                        false,
                        false,
                        true,
                        (byte)1,
                        0,
                        0,
                        0,
                        Vector3.Zero,
                        AssetManager.entityAtlas.findRegions("Tiles/Overworld/Water").get(1)
                ));
            }

            if (i == 4 ) {
                TILE_LIST.add(i, new Tile(
                        false,
                        false,
                        false,
                        true,
                        (byte)1,
                        0,
                        0,
                        0,
                        Vector3.Zero,
                        AssetManager.entityAtlas.findRegions("Tiles/Overworld/Grass").get(1)
                ));
            }

        }
    }

    public static void cleanUp() {
        TILE_LIST.clear();
    }

}
