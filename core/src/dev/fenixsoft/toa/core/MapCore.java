package dev.fenixsoft.toa.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import dev.fenixsoft.toa.managers.LevelManager;
import dev.fenixsoft.toa.managers.TileManager;
import dev.fenixsoft.toa.mapData.Chunk;
import dev.fenixsoft.toa.toolbox.MapGenerator;
import dev.fenixsoft.toa.toolbox.SimplexNoise;
import dev.fenixsoft.toa.toolbox.Utils;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class MapCore implements Runnable{

    public Thread thread;

    public static boolean paused = false;
    public static boolean running = false;

    public static int ticks = 0;

    public static final int CHUNK_WIDTH = 32;
    public static final int LEVEL_TILE_SIZE = 16;

    public static final Vector2 CHUNK_SIZE_UNIT = new Vector2(LEVEL_TILE_SIZE * CHUNK_WIDTH, LEVEL_TILE_SIZE * CHUNK_WIDTH);
    public static final Vector2 LEVEL_TILE_UNIT = new Vector2(LEVEL_TILE_SIZE, LEVEL_TILE_SIZE);

    //SIZE IN ROOMS!!!
    public static int LEVEL_HEIGHT = 128;
    public static int LEVEL_WIDTH = 128;

    public static final int OVERWORLD_TILE_SIZE = 16;

    public static final int OVERWORLD_LAYERS = 1;
    public static final int OVERWORLD_SIZE = (4096*2) / CHUNK_WIDTH;

    public static final int TILE_RENDERING_BORDER = 2;

    private static Chunk[] overworldMapData = new Chunk[OVERWORLD_SIZE * OVERWORLD_SIZE * OVERWORLD_LAYERS];

    static short selectedTile = 4;

    //keep track of generated height maps!
    //private final static Map<String,float[]> heightData = new ConcurrentHashMap<>();
    //private final static Map<String,float[]> biomeData = new ConcurrentHashMap<>();

    public int seed = 0;

    public static int getFrameAt(int x, int y) {

        boolean isCliff = TileManager.TILE_LIST.get(MapCore.getLevelTile(x,y)).isCliff();
        boolean isAutoTile = TileManager.TILE_LIST.get(MapCore.getLevelTile(x,y)).isAutoTile();

        int result = 0;

        short tile = getLevelTile(x,y);

        // Base ones
        short top, down, left, right, topRight, topLeft, downRight, downLeft;

        top = getLevelTile(x,y+1);
        down = getLevelTile(x,y-1);
        left = getLevelTile(x-1,y);
        right = getLevelTile(x+1,y);

        topRight = getLevelTile(x+1,y+1);
        topLeft = getLevelTile(x-1,y+1);

        downRight = getLevelTile(x+1,y-1);
        downLeft = getLevelTile(x-1,y-1);

        // Row 2
        short borderMid, borderLeft, borderRight;
        int multi = 2;
        borderMid = getLevelTile(x,y+multi);

        borderLeft = getLevelTile(x-1,y+multi);
        borderRight = getLevelTile(x+1,y+multi);

        // Row 3
        short farMid, farLeft, farRight;
        farMid = getLevelTile(x,y-multi);

        farLeft = getLevelTile(x-1,y-multi);
        farRight = getLevelTile(x+1,y-multi);

        // Row 3
        short farFarMid;
        farFarMid = getLevelTile(x,y-3);

        // Row 4
        short farFarFarMid;
        farFarFarMid = getLevelTile(x,y-4);

        if (down != tile)
        {
            result = 8;
        }

        if (top != tile)
        {
            result = 3;
        }

        if (left != tile)
        {
            result = 5;
        }

        if (right != tile)
        {
            result = 6;
        }

        if(!isCliff) {
            if (down != tile && left != tile) {
                result = 7;
            }

            if (down != tile && right != tile) {
                result = 9;
            }
        }else{

            if (down != tile && left != tile) {
                result = 23;
            }

            if (down != tile && right != tile) {
                result = 22;
            }

        }

        if (top != tile && left != tile)
        {
            result = 2;
        }

        if (top != tile && right != tile)
        {
            result = 4;
        }

        if(isCliff) {

            if (top == tile && right != tile && down != tile && left != tile) {
                result = 19;
            }

            if (top != tile && right == tile && down != tile && left != tile) {
                result = 16;
            }

            if (top != tile && right != tile && down == tile && left != tile) {
                result = 17;
            }

            if (top != tile && right != tile && down != tile && left == tile) {
                result = 18;
            }

            if (top != tile && right == tile && down != tile && left == tile) {
                result = 20;
            }

            if (top == tile && right != tile && down == tile && left != tile) {
                result = 21;
            }
        }

        if (top != tile && right != tile && down != tile && left != tile)
        {
            result = 1;
        }

        if(isCliff) {

            // Tier 1 cliffs
            if(down == tile && top == tile && farMid != tile || down != tile && top == tile && farMid != tile ){

                if (down == tile && left == tile && top == tile && right != tile
                        && farMid == tile)
                {
                    result = 6;
                }

                if (down == tile && left != tile && top == tile && right == tile
                        && farMid == tile)
                {
                    result = 5;
                }

                if (down != tile && left != tile && top == tile && right == tile
                        && topRight == tile ) {
                    result = 13;
                }

                if (down != tile && left == tile && top == tile && right != tile
                        && topLeft == tile
                ) {
                    result = 15;
                }

                if (down != tile && left == tile && top == tile && right == tile
                        && topRight == tile && topLeft == tile
                ) {
                    result = 14;
                }

                if ( down == tile && left != tile && top == tile && right == tile
                        && topRight == tile
                        && farMid != tile
                ){
                    result = 7;
                }

                if ( down == tile && left == tile && top == tile && right == tile
                        && topRight == tile && topLeft == tile

                        && farMid != tile
                        && downLeft != tile
                ){
                    result = 7;
                }

                if ( down == tile && left == tile && top == tile && right != tile
                        && topLeft == tile
                        && farMid != tile
                ){
                    result = 9;
                }

                if ( down == tile && left == tile && top == tile && right == tile
                        && topRight == tile && topLeft == tile

                        && farMid != tile
                        && downRight != tile
                ){
                    result = 9;
                }

                if ( down == tile && left == tile && top == tile && right == tile
                        && topLeft == tile && topRight == tile
                        && downLeft == tile && downRight == tile
                        && farMid != tile
                ){
                    result = 8;
                }

            }

            if (down != tile && left == tile && top == tile && right == tile
                    && topRight == tile && topLeft == tile
                    && borderMid == tile && borderLeft == tile && borderRight == tile
            ) {
                result = 14;
            }

            if (down == tile && left == tile && top == tile && right == tile
                    && topRight == tile && topLeft == tile
                    && downLeft == tile && downRight == tile
                    && farMid != tile) {
                result = 11;
            }

            if (down != tile && left != tile && top == tile && right == tile
                    && topRight == tile
                    && borderMid == tile && borderRight == tile
            ) {
                result = 13;
            }

            if (down == tile && left != tile && top == tile && right == tile
                    && topRight == tile
                    && downRight == tile
            ) {
                result = 10;
            }

            if (down == tile && left == tile && top == tile && right == tile
                    && topRight == tile
                    && downRight == tile
                    && topLeft == tile
                    && downLeft != tile
            ) {
                result = 10;
            }

            if (down == tile && left == tile && top == tile && right != tile
                    && topLeft == tile
                    && downLeft == tile
            ) {
                result = 12;
            }

            if (down == tile && left == tile && top == tile && right == tile
                    && topLeft == tile
                    && downLeft == tile
                    && topRight == tile
                    && downRight != tile
            ) {
                result = 12;
            }

            if (down != tile && left == tile && top == tile && right != tile
                    && topLeft == tile
                    && borderMid == tile && borderLeft == tile
            ) {
                result = 15;
            }

            if ( down == tile && left == tile && top == tile && right == tile
                    && topLeft == tile && topRight == tile
                    && downLeft == tile && downRight == tile
                    && farMid == tile && farFarMid != tile
            ){
                result = 8;
            }

            if ( down == tile && left != tile && top == tile && right == tile
                    && topRight == tile
                    && farFarMid != tile
                    && farMid == tile
            ){
                result = 7;
            }

            if ( down == tile && left == tile && top == tile && right == tile
                    && topRight == tile && topLeft == tile

                    && farMid == tile && farFarMid != tile
                    && farLeft != tile
            ){
                result = 7;
            }

            if ( down == tile && left == tile && top == tile && right != tile
                    && topLeft == tile
                    && farFarMid != tile
                    && farMid == tile
            ){
                result = 9;
            }

            if ( down == tile && left == tile && top == tile && right == tile
                    && topRight == tile && topLeft == tile
                    && farMid == tile && farFarMid != tile
                    && farRight != tile
            ){
                result = 9;
            }

            if (down == tile && left == tile && top == tile && right != tile
                    && farMid == tile && farFarMid == tile )
            {
                result = 6;
            }

            if (down == tile && left == tile && top == tile && right == tile
                    && farMid == tile && farFarMid == tile && farRight != tile)
            {
                result = 6;
            }

            if (down == tile && left == tile && top == tile && right == tile
                    && farMid == tile && farFarMid == tile && downRight != tile)
            {
                result = 6;
            }

            if (down == tile && left != tile && top == tile && right == tile
                    && farMid == tile && farFarMid == tile )
            {
                result = 5;
            }

            if (down == tile && left == tile && top == tile && right == tile
                    && farMid == tile && farFarMid == tile && farLeft != tile)
            {
                result = 5;
            }

            if (down == tile && left == tile && top == tile && right == tile
                    && farMid == tile && farFarMid == tile && downLeft != tile)
            {
                result = 5;
            }

        }

        return result;
    }

    void init(){

        int X = ThreadLocalRandom.current().nextInt(0, 99999);
        int Y = ThreadLocalRandom.current().nextInt(0, 99999);

        float scale = ThreadLocalRandom.current().nextInt(150, 500);

        float persistance = 0.25f;
        float lacunarity = ThreadLocalRandom.current().nextInt(3, 5);

        for(int i = 0; i < OVERWORLD_SIZE; i++) {
            for (int j = 0; j < OVERWORLD_SIZE; j++) {
                for (int z = 0; z < OVERWORLD_LAYERS; z++) {
                    /*
                    overworldMapData[i +
                            (j * OVERWORLD_SIZE) +
                            (z *
                                    (OVERWORLD_SIZE * OVERWORLD_SIZE)
                            )] =  MapGenerator.generateData(i + X,j + Y, i, j, scale, persistance, lacunarity);
                    */
                }
            }
        }



        System.out.println("Chunks loaded");

    }

    public static int generateLevel(int xPos){
        return LevelManager.generateLevel(xPos, LEVEL_HEIGHT, LEVEL_WIDTH);
    }

    @Override
    public void run() {
        init();

        long lastTime = TimeUtils.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int counter = 0;

        long lastTimer = TimeUtils.millis();
        double delta = 0;

        while (running) {

            if(!paused) {

                long now = TimeUtils.nanoTime();
                delta += (now - lastTime) / nsPerTick;
                lastTime = now;

                while (delta >= 1) {
                    counter++;
                    tick();
                    delta -= 1;
                }

                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (TimeUtils.millis() - lastTimer >= 1000) {
                    lastTimer += 1000;
                    //System.out.println(ticks + " MapCore ticks");
                    ticks = counter;
                    counter = 0;
                }
            }
        }

        stop();

    }

    public synchronized void start(){
        thread = new Thread(this);
        running = true;
        thread.start();

        seed = 0;

    }

    public synchronized void stop(){
        try {
            thread.interrupt();
            thread.join();
        } catch (InterruptedException e) {
            //TODO autogenerated block
        }
    }

    public static void tick() {

        //int posX = (int) MainCore.camera.getPosition().x - ((w / 2) / TileSize);
        //int posY = (int) MainCore.camera.getPosition().y - ((h / 2) / TileSize);

        int posX = (int) MainCore.camera.getPosition().x / CHUNK_WIDTH;
        int posY = (int) MainCore.camera.getPosition().y / CHUNK_WIDTH;

        /*
        for(int x = posX - (CHUNK_VIEW_DISTANCE); x < posX + (CHUNK_VIEW_DISTANCE); x++) {
            for(int y = posY - (CHUNK_VIEW_DISTANCE); y < posY + (CHUNK_VIEW_DISTANCE); y++) {

                if(mapDataB[((x + (y * CHUNK_LOAD_DISTANCE) + (0 * (CHUNK_LOAD_DISTANCE * CHUNK_LOAD_DISTANCE))))] != null) {

                    Chunk chunk = mapDataB[((x + (y * CHUNK_LOAD_DISTANCE) + (0 * (CHUNK_LOAD_DISTANCE * CHUNK_LOAD_DISTANCE))))];

                    if (!chunk.getRender()) {
                        chunk.setRender(true);
                        OverworldRenderer.getChunksList().add(chunk);
                    }
                }

            }
        }

        for(Chunk chunk: OverworldRenderer.getChunksList()) {

            int x = (int)chunk.getPosition().x;
            int y = (int)chunk.getPosition().y;

            if(x < posX - CHUNK_VIEW_DISTANCE
                    || x > posX + CHUNK_VIEW_DISTANCE
                    || y < posY - CHUNK_VIEW_DISTANCE
                    || y > posY + CHUNK_VIEW_DISTANCE) {

                if(chunk.getRender()) {
                    chunk.setRender(false);
                    OverworldRenderer.getChunksList().remove(chunk);
                }



            }
        }
        */

            //editTerrain(posX, posZ);

    }

    /*
    public static void editTerrain(int posX, int posZ) {

        if(InputHandler.isKeyPressed(Keyboard.KEY_I)){
            Mouse.setClipMouseCoordinatesToWindow(true);
            Mouse.setGrabbed(true);
        }

        if(InputHandler.isKeyPressed(Keyboard.KEY_SUBTRACT) && selectedTile - 1 >= 1){
            selectedTile--;
            System.out.println("tile is " + selectedTile);
        }

        if(InputHandler.isKeyPressed(Keyboard.KEY_ADD) && selectedTile + 1 <= TileManager.TILE_LIST.size()-1){
            selectedTile++;
            System.out.println("tile is " + selectedTile);
        }

        if(InputHandler.isKeyPressed(Keyboard.KEY_F5)){

            try {
                saveWorld(posX, posZ);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if(Mouse.isButtonDown(1)) {

            Vector3f collisionPointInMap = RenderCore.camera.ray.checkRayInMap(10, true, true);

            if(collisionPointInMap != null){

                int X = (int) collisionPointInMap.x;
                int Y = (int) collisionPointInMap.y;
                int Z = (int) collisionPointInMap.z;

                System.out.println("mouse clicked in [" + collisionPointInMap + "]");

                setTile(X, Y, Z, selectedTile);

            }

        }

        if(Mouse.isButtonDown(0)) {

            Vector3f collisionPointInMap = RenderCore.camera.ray.checkRayInMap(10, false, true);

            if(collisionPointInMap != null){

                int X = (int) collisionPointInMap.x;
                int Y = (int) collisionPointInMap.y;
                int Z = (int) collisionPointInMap.z;

                System.out.println("mouse clicked in [" + collisionPointInMap + "]");

                setTile(X, Y, Z, (short) 0);

            }

        }

    }
    */

    public static boolean loadChunk(int x, int y, int z) {

        File fileDir = new File("saves/" + x + "_" + y + "_" + z + ".Chunk");

        if(fileDir.exists()) {

            short[] data = new short[CHUNK_WIDTH*CHUNK_WIDTH];

            int airBlocks = 0;

            String file = Utils.loadFileAsString("saves/" + x + "_" + y + "_" + z + ".Chunk");

            String[] lines = file.split("_");

            for (int i = 0; i < data.length; i++) {

                data[i] = (short) Utils.parseInt(lines[i]);
                if (data[i] == 0){
                    airBlocks++;
                }

            }

            Chunk chunk = new Chunk(data);

            //mapData.put("" + x + "_" + y + "_" + z + "", chunk);

            System.out.println("loaded the Chunk save file (" + x + "_" + y + "_" + z + ")");

            return true;

        }

        return false;
    }

    // TODO: saving and loading... BROKEN!!!
    /*
    public static void saveWorld(int posX, int posZ) throws IOException {

        for(int x = posX - (CHUNK_LOAD_DISTANCE); x < posX + (CHUNK_LOAD_DISTANCE); x++) {
            for(int z = posZ - (CHUNK_LOAD_DISTANCE); z < posZ + (CHUNK_LOAD_DISTANCE); z++) {
                for(int y =  0; y < OVERWORLD_LAYERS ; y++) {
                    saveChunk(x, y, z);
                }
            }
        }

    }

    public static void saveChunk(int x, int y, int z) throws IOException {

        Chunk chunk = mapData.get("" + x + "_" + y + "_" + z + "");

        if(chunk != null) {
            if(!chunk.getSaved()) {

                PrintStream out = new PrintStream(new FileOutputStream("saves/" + x + "_" + y + "_" + z + ".Chunk"));
                ObjectOutputStream ou = new ObjectOutputStream(out);

                short[] data = chunk.getTiles();

                for (short type: data) {

                    ou.writeInt(type);

                }

                ou.close();

                chunk.setSaved();

                System.out.println("updated the Chunk save file (" + x  + "_" + y + "_" + z + ")");

            }
        }

    }
    */

    public static void setOverworldTile(int xPos, int yPos, int zPos, short value) {
        float xTile, yTile;

        xTile = xPos + (-(xPos / CHUNK_WIDTH) * CHUNK_WIDTH);
        yTile = yPos + (-(yPos / CHUNK_WIDTH) * CHUNK_WIDTH);

        float xChunk, yChunk;
        xChunk = (xPos / (float)CHUNK_WIDTH);
        yChunk = (yPos / (float)CHUNK_WIDTH);

        if(xChunk < 0) {
            xChunk = (float) Math.floor(xChunk);
        }

        if(yChunk < 0) {
            yChunk = (float) Math.floor(yChunk);
        }

        if(xPos <=-1 || yPos <=-1
        || xPos >= OVERWORLD_SIZE * CHUNK_WIDTH
        || yPos >= OVERWORLD_SIZE * CHUNK_WIDTH){
            return;
        }

        if(overworldMapData[ (((int)xChunk + ((int)yChunk * OVERWORLD_SIZE) + (zPos * (OVERWORLD_SIZE * OVERWORLD_SIZE))))] != null) {
            //System.out.println("Chunk in [" + (int)xChunk + "," + (int)yChunk + "," + (int)zChunk +"]");
            overworldMapData[((int)xChunk + ((int)yChunk * OVERWORLD_SIZE) + (zPos * (OVERWORLD_SIZE * OVERWORLD_SIZE)))].
                setTile((int) xTile, (int) yTile, value);
        }

    }

    public static short getOverworldTile(int xPos, int yPos, int zPos) {
        float xTile, yTile;

        xTile = xPos + (-(xPos / CHUNK_WIDTH) * CHUNK_WIDTH);
        yTile = yPos + (-(yPos / CHUNK_WIDTH) * CHUNK_WIDTH);

        float xChunk, yChunk;
        xChunk = (xPos / (float)CHUNK_WIDTH);
        yChunk = (yPos / (float)CHUNK_WIDTH);

        if(xChunk < 0) {
            xChunk = (float) Math.floor(xChunk);
        }

        if(yChunk < 0) {
            yChunk = (float) Math.floor(yChunk);
        }

        if(xPos <=-1 || yPos <=-1
        || xPos >= OVERWORLD_SIZE * CHUNK_WIDTH
        || yPos >= OVERWORLD_SIZE * CHUNK_WIDTH){
            return 0;
        }

        if(overworldMapData[ (((int)xChunk + ((int)yChunk * OVERWORLD_SIZE) + (zPos * (OVERWORLD_SIZE * OVERWORLD_SIZE))))] != null) {
            return overworldMapData[((int)xChunk + ((int)yChunk * OVERWORLD_SIZE) + (zPos * (OVERWORLD_SIZE * OVERWORLD_SIZE)))].
                getTile((int)xTile, (int)yTile);
        }
        return 0;
    }

    public static void setLevelTile(int xPos, int yPos, short value) {
        float xTile, yTile;

        xTile = xPos + (-(xPos / CHUNK_WIDTH) * CHUNK_WIDTH);
        yTile = yPos + (-(yPos / CHUNK_WIDTH) * CHUNK_WIDTH);

        float xChunk, yChunk;
        xChunk = (xPos / (float)CHUNK_WIDTH);
        yChunk = (yPos / (float)CHUNK_WIDTH);

        if(xChunk < 0) {
            xChunk = (float) Math.floor(xChunk);
        }

        if(yChunk < 0) {
            yChunk = (float) Math.floor(yChunk);
        }

        if(xPos <=-1 || yPos <=-1
        || xPos >= LEVEL_WIDTH * CHUNK_WIDTH
        || yPos >= LEVEL_HEIGHT * CHUNK_WIDTH){
            return;
        }

        if(LevelManager.getLevelMapData()[(int)xChunk + ((int)yChunk * LEVEL_WIDTH)] != null) {
            //System.out.println("Chunk in [" + (int)xChunk + "," + (int)yChunk +"]");
            LevelManager.getLevelMapData()[(int)xChunk + ((int)yChunk * LEVEL_WIDTH)].
                    setTile((int) xTile, (int) yTile, value);
        }

    }

    public static short getLevelTile(int xPos, int yPos) {
        float xTile, yTile;

        xTile = xPos + (-(xPos / CHUNK_WIDTH) * CHUNK_WIDTH);
        yTile = yPos + (-(yPos / CHUNK_WIDTH) * CHUNK_WIDTH);

        float xChunk, yChunk;
        xChunk = (xPos / (float)CHUNK_WIDTH);
        yChunk = (yPos / (float)CHUNK_WIDTH);

        if(xChunk < 0) {
            xChunk = (float) Math.floor(xChunk);
        }

        if(yChunk < 0) {
            yChunk = (float) Math.floor(yChunk);
        }

        if(xPos <=-1 || yPos <=-1
        || xPos >= LEVEL_WIDTH * CHUNK_WIDTH
        || yPos >= LEVEL_HEIGHT * CHUNK_WIDTH){
            return 0;
        }

        if(LevelManager.getLevelMapData()[(int)xChunk + ((int)yChunk * LEVEL_WIDTH)] != null) {
            //System.out.println("Chunk in [" + (int)xChunk + "," + (int)yChunk + "]");
            return LevelManager.getLevelMapData()[(int)xChunk + ((int)yChunk * LEVEL_WIDTH)].
                    getTile((int)xTile, (int)yTile);
        }

        return 0;
    }

    public static int getGroundInLevel(int xPos, int yPos){
        int result = 0;

        for (int i = yPos; i >= 0; i--) {
            short data = getLevelTile(xPos, i);

            if(TileManager.TILE_LIST.get(data).isSolid()){
                result = i + 1;
                System.out.println("checked " + result + " in a " + data + " block type");
                break;
            }
        }

        return result;
    }

    /*
    public static Chunk getMapData(int xPos, int yPos, int zPos) {
        return mapData.get("" + xPos +"_"+ yPos +"_"+ zPos + "");
    }

    public static short[] getTileData(int xPos, int yPos, int zPos){
        return mapData.get("" + xPos +"_"+ yPos +"_"+ zPos + "").getTiles();
    }
    */

    /*
    public static void generateData(int x, int y){

        float[] values = heightData.get("" + x + "_" + y + "");
        float[] biomes = biomeData.get("" + x + "_" + y + "");

        if (values == null || biomes == null) {

            biomes = HeightGenerator.GenerateNoiseMap((x * CHUNK_WIDTH) + seed, (y * CHUNK_WIDTH) + seed);
            values = HeightGenerator.GenerateHeightMap((x * CHUNK_WIDTH) + seed, (y * CHUNK_WIDTH) + seed, biomes);

            heightData.put("" + x + "_" + y + "", values);
            biomeData.put("" + x + "_" + y + "", biomes);

        }
        else{

            for (int z = 0; z < OVERWORLD_LAYERS; z++) {

                Chunk chunk = mapData.get("" + x + "_" + y + "_" + z + "");

                System.out.println("Chunk in [" + x + "," + y + "]" + " in the layer " + z);

                if (chunk == null) {

                    if (!loadChunk(x, y, z)) {
                        Chunk data = MapGenerator.generateData(x, y, values, biomes);

                        mapData.put("" + x + "_" + y + "_" + z + "", data);
                    }

                }

            }

        }

    }
    */

    public static boolean isSolid(int x, int y){

        if(getOverworldTile(x, y, 0) != 0)
            return true;

        return false;
    }

    /*
    public static Map<String,Chunk> getMapData() {
        return mapData;
    }

    public static Map<String,float[]> getHeightData() {
        return heightData;
    }

    public static Map<String,float[]> getBiomeData() {
        return biomeData;
    }
    */

}
