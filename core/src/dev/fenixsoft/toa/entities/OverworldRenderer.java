package dev.fenixsoft.toa.entities;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.managers.AssetManager;

public class OverworldRenderer extends Entity{

    int dataX = 0;
    int dataY = 0;
    int x = 0;
    int y = 0;

    public OverworldRenderer(Vector2 position, Vector2 scale) {
        super(position, scale);
    }

    public void renderTick(){

        dataX = (int) ((MainCore.camera.getPosition().x / MapCore.OVERWORLD_TILE_SIZE) - ((MainCore.SCREEN_WIDTH / MapCore.OVERWORLD_TILE_SIZE) / 2));
        dataY = (int) ((MainCore.camera.getPosition().y / MapCore.OVERWORLD_TILE_SIZE) - ((MainCore.SCREEN_HEIGHT / MapCore.OVERWORLD_TILE_SIZE) / 2));

        x = (dataX * MapCore.OVERWORLD_TILE_SIZE);
        y = (dataY * MapCore.OVERWORLD_TILE_SIZE);

        setPosition(new Vector2(dataX, dataY));

    }

    public void render(){

        //System.out.println("Camera in " + x + "," + y);

        for(int i = -MapCore.TILE_RENDERING_BORDER; i < (MainCore.SCREEN_WIDTH / MapCore.OVERWORLD_TILE_SIZE) + MapCore.TILE_RENDERING_BORDER; i++) {
            for(int j = -MapCore.TILE_RENDERING_BORDER; j < (MainCore.SCREEN_HEIGHT / MapCore.OVERWORLD_TILE_SIZE) + MapCore.TILE_RENDERING_BORDER; j++) {



                MainCore.worldBatch.draw(AssetManager.entityAtlas.findRegions("Tiles/Overworld/Tile").get(
                        MapCore.getOverworldTile(dataX + i, dataY + j, 0)
                ), x  + (i * MapCore.OVERWORLD_TILE_SIZE), y  + (j * MapCore.OVERWORLD_TILE_SIZE), MapCore.OVERWORLD_TILE_SIZE, MapCore.OVERWORLD_TILE_SIZE);

            }
        }

    }

}
