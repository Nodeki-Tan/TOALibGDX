package dev.fenixsoft.toa.core.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.GameCore;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.entities.LevelRenderer;
import dev.fenixsoft.toa.entities.OverworldRenderer;
import dev.fenixsoft.toa.entities.player.LocalPlayer;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.TileManager;
import dev.fenixsoft.toa.physics.BoundingBox;
import dev.fenixsoft.toa.physics.PhysicsConstants;
import dev.fenixsoft.toa.stats.Stats;

public class GameState extends State{

	private MapCore map;
	private OverworldRenderer overWorldRenderer;
	private LevelRenderer leverRenderer;
	private float elapsedTime = 0.0f;
	int currentMemory;

	LocalPlayer player;

	public GameState() {
	}

	public void init() {
		map = new MapCore();
		map.start();

		overWorldRenderer = new OverworldRenderer(Vector2.Zero, null);
		leverRenderer = new LevelRenderer(Vector2.Zero, null);

		Vector2 levelPos = new Vector2(16 , (MapCore.generateLevel(1) * MapCore.LEVEL_TILE_SIZE)+ 20);

		Entity playerEntity = new Entity(levelPos, new Vector2(48.0f / MapCore.LEVEL_TILE_SIZE, 26.0f / MapCore.LEVEL_TILE_SIZE));
		BoundingBox playerAABB = new BoundingBox(
				levelPos,
				new Vector2(10,(3 * MapCore.LEVEL_TILE_SIZE)-1),
				AssetManager.COLOR_RED,
				true, PhysicsConstants.FULL_BLOCK);

		player = new LocalPlayer(
				new Vector2(
						(MapCore.OVERWORLD_SIZE * MapCore.CHUNK_WIDTH * MapCore.OVERWORLD_TILE_SIZE) / 2,
						(MapCore.OVERWORLD_SIZE * MapCore.CHUNK_WIDTH * MapCore.OVERWORLD_TILE_SIZE) / 2),
				levelPos,
				new Stats(8,1,1,1,1,1,1),
				new Entity(new Vector2(0, 0), new Vector2(2, 2)),
				playerEntity,
				MainCore.camera,
				playerAABB);

		done = true;
	}

	public void tick(float delta) {

		player.tick(delta);

		if (elapsedTime >= 1) {
			elapsedTime = 0;
			currentMemory = (int) ((Gdx.app.getNativeHeap() / 1024) / 1024);
		}

		elapsedTime += Gdx.graphics.getDeltaTime();

	}

	public void renderTick(float delta) {

		player.renderTick();

		if(player.inLevel) {
			leverRenderer.renderTick();
		}
		else {
			overWorldRenderer.renderTick();
		}


	}

	public void render(float delta)  {

		if(player.inLevel) {
			leverRenderer.render();
		}
		else {
			overWorldRenderer.render();
		}

		player.render();

	}

	public void renderUI(float delta)  {

		//DEBUG DRAWS!!!
		AssetManager.fontWhite.draw(MainCore.UIBatch, "GameCore ticks: " + GameCore.ticks, 8,32);
		AssetManager.fontWhite.draw(MainCore.UIBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 8,64);
		AssetManager.fontWhite.draw(MainCore.UIBatch, "Memory usage: " + currentMemory + " MB", 8,96);
		AssetManager.fontWhite.draw(MainCore.UIBatch, "MapCore ticks: " + MapCore.ticks, 8,128);

		if(player.inLevel) {
			AssetManager.fontWhite.draw(MainCore.UIBatch, "Player in: " + "["
					+ (int)(player.getLevelPosition().x / MapCore.LEVEL_TILE_SIZE)
					+ "," + (int)(player.getLevelPosition().y / MapCore.LEVEL_TILE_SIZE) + "]", 8,128 + 32);
		}
		else {
			AssetManager.fontWhite.draw(MainCore.UIBatch, "Player in: " + "["
					+ (int)(player.getOverworldPosition().x / MapCore.OVERWORLD_TILE_SIZE)
					+ "," + (int)(player.getOverworldPosition().y / MapCore.OVERWORLD_TILE_SIZE) + "]", 8,128 + 32);
		}


	}

	@Override
	public void dispose() {

	}

}
