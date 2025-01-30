package dev.fenixsoft.toa.entities;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.TileManager;

public class LevelRenderer extends Entity{

    int dataX = 0;
    int dataY = 0;
    int x = 0;
    int y = 0;

    public LevelRenderer(Vector2 position, Vector2 scale) {
        super(position, scale);
    }

    public void renderTick(){

        dataX = (int) ((MainCore.camera.getPosition().x / MapCore.LEVEL_TILE_SIZE) - ((MainCore.SCREEN_WIDTH / MapCore.LEVEL_TILE_SIZE) / 2));
        dataY = (int) ((MainCore.camera.getPosition().y / MapCore.LEVEL_TILE_SIZE) - ((MainCore.SCREEN_HEIGHT / MapCore.LEVEL_TILE_SIZE) / 2));

        x = (dataX * MapCore.LEVEL_TILE_SIZE);
        y = (dataY * MapCore.LEVEL_TILE_SIZE);

        setPosition(new Vector2(dataX, dataY));

    }

    public void render(){

        //System.out.println("Camera in " + x + "," + y);

        for(int i = -MapCore.TILE_RENDERING_BORDER;
            i < (MainCore.SCREEN_WIDTH / MapCore.LEVEL_TILE_SIZE) + MapCore.TILE_RENDERING_BORDER; i++) {

            for(int j = -MapCore.TILE_RENDERING_BORDER;
                j < (MainCore.SCREEN_HEIGHT / MapCore.LEVEL_TILE_SIZE) + MapCore.TILE_RENDERING_BORDER; j++) {

                if(MapCore.getLevelTile(dataX + i, dataY + j) == 6){

                    MainCore.worldBatch.draw(
                            AssetManager.entityAtlas.findRegions("Tiles/Overworld/Dirt").get(MapCore.getFrameAt(dataX + i, dataY + j)),
                            x + (i * MapCore.LEVEL_TILE_SIZE),
                            y + (j * MapCore.LEVEL_TILE_SIZE),
                            MapCore.LEVEL_TILE_SIZE,
                            MapCore.LEVEL_TILE_SIZE);

                }/*else if(MapCore.getLevelTile(dataX + i, dataY + j) == 3){

                    MainCore.worldBatch.draw(
                            AssetManager.entityAtlas.findRegions("Tiles/Overworld/Sand").get(MapCore.getFrameAt(dataX + i, dataY + j)),
                            x + (i * MapCore.LEVEL_TILE_SIZE),
                            y + (j * MapCore.LEVEL_TILE_SIZE),
                            MapCore.LEVEL_TILE_SIZE,
                            MapCore.LEVEL_TILE_SIZE);

                } */else if(MapCore.getLevelTile(dataX + i, dataY + j) == 2){

                    MainCore.worldBatch.draw(
                            AssetManager.entityAtlas.findRegions("Tiles/Overworld/Water").get(MapCore.getFrameAt(dataX + i, dataY + j)),
                            x + (i * MapCore.LEVEL_TILE_SIZE),
                            y + (j * MapCore.LEVEL_TILE_SIZE),
                            MapCore.LEVEL_TILE_SIZE,
                            MapCore.LEVEL_TILE_SIZE);

                } else if(MapCore.getLevelTile(dataX + i, dataY + j) == 4){

                MainCore.worldBatch.draw(
                        AssetManager.entityAtlas.findRegions("Tiles/Overworld/Grass").get(MapCore.getFrameAt(dataX + i, dataY + j)),
                        x + (i * MapCore.LEVEL_TILE_SIZE),
                        y + (j * MapCore.LEVEL_TILE_SIZE),
                        MapCore.LEVEL_TILE_SIZE,
                        MapCore.LEVEL_TILE_SIZE);

                } else {
                    MainCore.worldBatch.draw(
                            TileManager.TILE_LIST.get(
                                            MapCore.getLevelTile(dataX + i, dataY + j))
                                    .getFrame(),
                            x + (i * MapCore.LEVEL_TILE_SIZE),
                            y + (j * MapCore.LEVEL_TILE_SIZE),
                            MapCore.LEVEL_TILE_SIZE,
                            MapCore.LEVEL_TILE_SIZE);
                }
            }
        }

    }

}
