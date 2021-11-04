package dev.fenixsoft.toa.toolbox;

import dev.fenixsoft.toa.core.MapCore;

public class HeightGenerator {

    public static final int CHUNK_WIDTH = MapCore.CHUNK_WIDTH;

	/* PLAINS!
	static int octaves = 4;
	static float scale = 400f;

	static float persistance = .25f;
	static float lacunarity = 3f;

	static float solidGroundHeight = 128f;
	static float terrainHeight = 32f;
	*/

    /* PLAINS 2.0?, bigger groups, more plain, yet natural enough
    static int octaves = 8;
	static float scale = 800f;

	static float persistance = .25f;
	static float lacunarity = 3f;

	static float solidGroundHeight = 128f;
	static float terrainHeight = 32f;
	*/

    /* MOUNTAINS!!, also maybe BiomeGen
    static int octaves = 16;
	static float scale = 2000f;

	static float persistance = .25f;
	static float lacunarity = 4f;

	static float solidGroundHeight = 128f;
	static float terrainHeight = 96f;
	*/

    //THIS MAKES BIOMES!
    public static float[] GenerateNoiseMap(int x, int z){

        int octaves = 4;
        float scale = 80f;

        float persistance = .25f;
        float lacunarity = 4f;

        float[] noiseMap = new float[CHUNK_WIDTH * CHUNK_WIDTH];

        float amplitude = 1;
        float frequency = 1;

        for (int i = 0; i < CHUNK_WIDTH; i++)
        {
            for (int o = 0; o < CHUNK_WIDTH; o++)
            {

                amplitude = 1;
                frequency = 1;
                float noiseHeight = 0;

                for (int j = 0; j < octaves; j++)
                {
                    float sampleX = (i + x) / scale * frequency;
                    float sampleY = (o + z) / scale * frequency;

                    float perlinValue = (float) (SimplexNoise.noise(sampleX, sampleY) + 1) * 0.5f;
                    noiseHeight += perlinValue * amplitude;

                    amplitude *= persistance;
                    frequency *= lacunarity;
                }

                noiseMap[i + (o * CHUNK_WIDTH)] = Maths.Normaliced(noiseHeight, 1, 0) * 6;
            }
        }

        return noiseMap;
    }

    public static float[] GenerateHeightMap(int x, int z){

        int octaves = 8;
        float scale = 1000f;

        float persistance = .25f;
        float lacunarity = 3f;

        int solidGroundHeight = 128;
        int terrainHeight = 256;

        float[] noiseMap = new float[CHUNK_WIDTH];

        float amplitude = 1;
        float frequency = 1;

        for (int i = 0; i < CHUNK_WIDTH; i++)
        {

            amplitude = 1;
            frequency = 1;
            float noiseHeight = 0;

            for (int j = 0; j < octaves; j++)
            {
                float sampleX = (i + x) / scale * frequency;
                float sampleY = (z) / scale * frequency;

                float perlinValue = (float) (SimplexNoise.noise(sampleX, sampleY) + 1) * 0.5f;
                noiseHeight += perlinValue * amplitude;

                amplitude *= persistance;
                frequency *= lacunarity;
            }

            noiseMap[i] = (Maths.Normaliced(noiseHeight, 1, 0) * terrainHeight) + solidGroundHeight;

        }

        return noiseMap;
    }

    public static float Evaluate2dPoint(float x, float y, float scale){

        int octaves = 4;

        float persistance = .25f;
        float lacunarity = 5f;

        float value = 0f;

        float amplitude = 1;
        float frequency = 1;

        amplitude = 1;
        frequency = 1;
        float noiseHeight = 0;

        for (int j = 0; j < octaves; j++)
        {
            float sampleX = x / scale * frequency;
            float sampleY = y / scale * frequency;

            float perlinValue = (float) (SimplexNoise.noise(sampleX, sampleY) + 1) * 0.5f;
            noiseHeight += perlinValue * amplitude;

            amplitude *= persistance;
            frequency *= lacunarity;
        }

        value = Maths.Normaliced(noiseHeight, 1, 0);

        return value;
    }

}
