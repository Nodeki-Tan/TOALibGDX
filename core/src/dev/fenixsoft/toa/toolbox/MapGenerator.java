package dev.fenixsoft.toa.toolbox;


import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.mapData.Chunk;

import java.util.concurrent.ThreadLocalRandom;

public class MapGenerator {

    public static final int CHUNK_WIDTH = MapCore.CHUNK_WIDTH;

    static int waterLevel = 256 + 64;

    public static Chunk generateData(int x, int y, int cX, int cY, float scale, float persistance, float lacunarity){

        float[] biomes;

        biomes = HeightGenerator.GenerateNoiseMap((x * CHUNK_WIDTH), (y * CHUNK_WIDTH), scale,  persistance,  lacunarity);

        short[] data = new short[CHUNK_WIDTH*CHUNK_WIDTH];

        for(int i = 0; i < CHUNK_WIDTH; i++) {
            for(int j = 0; j < CHUNK_WIDTH; j++) {

                data[i + (j * CHUNK_WIDTH)] = 1;

                float height =  biomes[i + (j * CHUNK_WIDTH)];

                if (height <= 2.8f) {

                    data[i + (j * CHUNK_WIDTH)] = 2;

                }
                else if (height <= 3.2f && height > 2.8f) {

                    data[i + (j * CHUNK_WIDTH)] = 3;

                }
                else if (height <= 3.8f && height > 3.2f) {

                    data[i + (j * CHUNK_WIDTH)] = 4;

                }
                else if (height <= 4.6f && height > 3.8f) {

                    data[i + (j * CHUNK_WIDTH)] = 5;

                }
                else if (height <= 5.6f && height > 4.6f) {

                    data[i + (j * CHUNK_WIDTH)] = 6;

                }
                else if (height > 5.6f) {

                    data[i + (j * CHUNK_WIDTH)] = 7;

                }

            }
        }

        return new Chunk(data);

    }


    static boolean checkPoint(float x, float y, float scale, float posibility){

        float value = HeightGenerator.Evaluate2dPoint(x,y, scale);

        if(value <= posibility){
            return true;
        }
        return false;

    }

    static float checkWorldBorder(float x, float y, float height){

        float result = 0;

        Vector2 a = new Vector2(x, y);

        Vector2 b = new Vector2((MapCore.OVERWORLD_SIZE * CHUNK_WIDTH) / 2,(MapCore.OVERWORLD_SIZE * CHUNK_WIDTH) / 2);

        float distance = Vector2.dst2(a.x, a.y, b.x, b.y);

        float percentage = Maths.getPercentage(distance,
                (MapCore.OVERWORLD_SIZE * CHUNK_WIDTH) /
                        ((MapCore.OVERWORLD_SIZE * CHUNK_WIDTH)) / (Maths.getRuleOf3(MapCore.OVERWORLD_SIZE, 2048 / CHUNK_WIDTH, 8f)));

        if(percentage >= 5f){
            result = height * -((percentage / 100f) - 1);
            return result;
        }

        return height;

    }

}
