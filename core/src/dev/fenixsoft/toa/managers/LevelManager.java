package dev.fenixsoft.toa.managers;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.entities.Actor;
import dev.fenixsoft.toa.mapData.Chunk;
import dev.fenixsoft.toa.physics.*;
import dev.fenixsoft.toa.toolbox.MapGenerator;
import org.lwjgl.Sys;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LevelManager {

    private static Chunk[] levelMapData;
    private static Map<Vector2, ArrayList<BoundingBox>> levelChunkPhysicsWorld = new HashMap<Vector2, ArrayList<BoundingBox>>();
    private static Map<Vector2, ArrayList<BoundingBox>> levelEntityPhysicsWorld = new HashMap<Vector2, ArrayList<BoundingBox>>();

    public static List<Actor> actorList = new ArrayList<>();

    private static Map<Vector2, BoundingBox> levelPhysicsAreas = new HashMap<Vector2, BoundingBox>();

    static int LEVEL_HEIGHT;
    static int LEVEL_WIDTH;

    public static void createLevel(int _LEVEL_HEIGHT, int _LEVEL_WIDTH){

        LEVEL_HEIGHT = _LEVEL_HEIGHT;
        LEVEL_WIDTH = _LEVEL_WIDTH;

        levelMapData = new Chunk[LEVEL_HEIGHT * LEVEL_WIDTH];

        int X = ThreadLocalRandom.current().nextInt(0, 99999);
        int Y = ThreadLocalRandom.current().nextInt(0, 99999);

        float scale = ThreadLocalRandom.current().nextInt(150, 500);

        float persistance = 0.25f;
        float lacunarity = ThreadLocalRandom.current().nextInt(3, 5);

        for(int i = 0; i < LEVEL_WIDTH; i++) {
            for (int j = 0; j < LEVEL_HEIGHT; j++) {

                levelMapData[i + (j * LEVEL_WIDTH)] = MapGenerator.generateData(i + 0, j + 0, i, j, 200, persistance, 4);

            }
        }

        // Hack for correcting cliffs

        for(int i = 0; i < LEVEL_WIDTH; i++) {
            for (int j = 0; j < LEVEL_HEIGHT; j++) {


                for(int x = 0; x < MapCore.CHUNK_WIDTH; x++) {
                    for (int y = 0; y < MapCore.CHUNK_WIDTH; y++) {

                        int TY, TX;

                        TX = x + (i * MapCore.CHUNK_WIDTH);
                        TY = y + (j * MapCore.CHUNK_WIDTH);

                        // Base ones
                        short top, down, left, right;
                        short tile = MapCore.getLevelTile(TX, TY);

                        top = MapCore.getLevelTile(x,y+1);
                        down = MapCore.getLevelTile(x,y-1);
                        left = MapCore.getLevelTile(x-1,y);
                        right = MapCore.getLevelTile(x+1,y);

                        //if (top == tile || right == tile || down == tile || left == tile) {

                            if (tile == 6) {
                                MapCore.setLevelTile(TX, TY - 1, (short) 6);
                                MapCore.setLevelTile(TX, TY - 2, (short) 6);
                                MapCore.setLevelTile(TX, TY - 3, (short) 6);

                                MapCore.setLevelTile(TX - 1, TY, (short) 6);

                                MapCore.setLevelTile(TX - 1, TY - 1, (short) 6);
                                MapCore.setLevelTile(TX - 1, TY - 2, (short) 6);
                                MapCore.setLevelTile(TX - 1, TY - 3, (short) 6);
                            }

                        //}

                    }
                }

            }
        }

    }

    public static int generateLevel(int xPos, int LEVEL_HEIGHT, int LEVEL_WIDTH){

        createLevel(LEVEL_HEIGHT, LEVEL_WIDTH);

        for(int i = 0; i < LEVEL_WIDTH; i++) {
            for (int j = 0; j < LEVEL_HEIGHT; j++) {

                Vector2 pos = new Vector2(i, j);

                Vector2 globalPos = new Vector2(
                        (i  * (MapCore.CHUNK_WIDTH * MapCore.LEVEL_TILE_SIZE)),
                        (j * (MapCore.CHUNK_WIDTH * MapCore.LEVEL_TILE_SIZE)));

                BoundingBox AABB = new StaticBoundingBox(
                        globalPos,
                        MapCore.CHUNK_SIZE_UNIT,
                        AssetManager.COLOR_GREEN,
                        true,  PhysicsConstants.FULL_BLOCK);

                levelChunkPhysicsWorld.put(pos, makeCollisionArea(levelMapData[i + (j * LEVEL_WIDTH)], i, j));
                levelPhysicsAreas.put(pos, AABB);

            }
        }

        int result = MapCore.getGroundInLevel(xPos, LEVEL_HEIGHT * MapCore.CHUNK_WIDTH);

        return result;
    }

    public static ArrayList<BoundingBox> makeCollisionArea(Chunk data, int x, int y){
        ArrayList<BoundingBox> list  = new ArrayList<BoundingBox>();

        for (int i = 0; i < MapCore.CHUNK_WIDTH; i++) {
            for (int j = 0; j < MapCore.CHUNK_WIDTH; j++) {

                short actual, up, down, left, right;

                Vector2 globalPos = new Vector2(
                        (i * MapCore.LEVEL_TILE_SIZE) + (x * (MapCore.CHUNK_WIDTH * MapCore.LEVEL_TILE_SIZE)),
                        (j * MapCore.LEVEL_TILE_SIZE) + (y * (MapCore.CHUNK_WIDTH * MapCore.LEVEL_TILE_SIZE)));

                Vector2 tileGlobalPos = new Vector2(globalPos.x / MapCore.LEVEL_TILE_SIZE,globalPos.y / MapCore.LEVEL_TILE_SIZE);


                actual = data.getTile(i, j);

                if (j == 0 || j == MapCore.CHUNK_WIDTH - 1){
                    up      = MapCore.getLevelTile((int)tileGlobalPos.x, (int)tileGlobalPos.y +1);
                    down    = MapCore.getLevelTile((int)tileGlobalPos.x, (int)tileGlobalPos.y -1);

                }else{
                    up      = data.getTile(i, j + 1);
                    down    = data.getTile(i, j - 1);
                }

                if (i == 0 || i == MapCore.CHUNK_WIDTH - 1){
                    left    = MapCore.getLevelTile((int)tileGlobalPos.x - 1, (int)tileGlobalPos.y);
                    right   = MapCore.getLevelTile((int)tileGlobalPos.x + 1, (int)tileGlobalPos.y);

                }else{
                    left    = data.getTile(i - 1, j);
                    right   = data.getTile(i + 1, j);
                }


                if (TileManager.TILE_LIST.get(actual).isSolid()){

                    if (!TileManager.TILE_LIST.get(up).isSolid()||
                        !TileManager.TILE_LIST.get(down).isSolid()||
                        !TileManager.TILE_LIST.get(left).isSolid()||
                        !TileManager.TILE_LIST.get(right).isSolid()){

                        BoundingBox AABB = new StaticBoundingBox(
                                globalPos,
                                MapCore.LEVEL_TILE_UNIT,
                                AssetManager.COLOR_WHITE,
                                true, PhysicsConstants.FULL_BLOCK);

                        list.add(AABB);

                    }

                }

            }
        }

        return list;
    }

    public static boolean levelRaycast(Vector2 rayOrig, Vector2 rayDir, BoundingBox target, RayContactResult out){
        ArrayList<BoundingBox> AABBs = new ArrayList<>();

        RayContactResult localOut = new RayContactResult();

        for (Vector2 pos: levelPhysicsAreas.keySet()) {
            BoundingBox box = levelPhysicsAreas.get(pos);

            if (PhysicsSolver.rayVsRect(rayOrig, rayDir, box, localOut)){

                for (int i = 0; i < levelChunkPhysicsWorld.get(pos).size(); i++) {
                    AABBs.add(levelChunkPhysicsWorld.get(pos).get(i));
                }

            }

        }

        return PhysicsSolver.rayVsRect(rayOrig, rayDir, target, AABBs, out);
    }

    public static void levelCollisionSolving(BoundingBox mover, float delta){
        ArrayList<BoundingBox> AABBs = new ArrayList<>();

        RayContactResult localOut = new RayContactResult();



        for (Vector2 pos: levelPhysicsAreas.keySet()) {
            BoundingBox box = levelPhysicsAreas.get(pos);

            if(PhysicsSolver.dinamycRectVsRect(mover, box, localOut, delta)
            || PhysicsSolver.rectVsRect(mover, box)){

                for (int i = 0; i < levelChunkPhysicsWorld.get(pos).size(); i++) {
                    AABBs.add(levelChunkPhysicsWorld.get(pos).get(i));
                }

            }

        }

        for (Actor actor: actorList) {
            AABBs.add(actor.getLevelAABB());
        }

        PhysicsSolver.solveIteractionsInArea(mover, AABBs, delta);
    }

    public static Chunk[] getLevelMapData(){
        return levelMapData;
    }

    public static void cleanUp() {
        levelMapData = null;
        levelChunkPhysicsWorld.clear();
        levelEntityPhysicsWorld.clear();
        levelPhysicsAreas.clear();
    }

}
