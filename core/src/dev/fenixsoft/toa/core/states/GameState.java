package dev.fenixsoft.toa.core.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.GameCore;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.entities.Actor;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.entities.LevelRenderer;
import dev.fenixsoft.toa.entities.OverworldRenderer;
import dev.fenixsoft.toa.entities.player.LocalPlayer;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.LevelManager;
import dev.fenixsoft.toa.managers.TileManager;
import dev.fenixsoft.toa.mapData.Tile;
import dev.fenixsoft.toa.physics.BoundingBox;
import dev.fenixsoft.toa.physics.PhysicsConstants;
import dev.fenixsoft.toa.stats.Stats;

import java.util.ArrayList;
import java.util.List;

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

		MapCore.generateLevel(0);

		Vector2 levelPos = new Vector2(2048 , 2048);

		SpawnPlayer(levelPos, new Vector2(24.0f / MapCore.LEVEL_TILE_SIZE, 48.0f / MapCore.LEVEL_TILE_SIZE), new Vector2(10, 8), true);

		done = true;
	}

	public void SpawnPlayer(Vector2 pos, Vector2 spriteSize, Vector2 AABBsize, boolean isXCenteredAABB){

		Vector2 AABBPos = new Vector2((pos.x + ((spriteSize.x * MapCore.LEVEL_TILE_SIZE) / 2)) - (AABBsize.x / 2), pos.y);

		BoundingBox AABB = new BoundingBox(
				AABBPos,
				AABBsize,
				AssetManager.COLOR_RED,
				true, PhysicsConstants.FULL_BLOCK);


		Entity playerEntity = new Entity(pos, spriteSize, false);


		player = new LocalPlayer(
				new Vector2(
						(MapCore.OVERWORLD_SIZE * MapCore.CHUNK_WIDTH * MapCore.OVERWORLD_TILE_SIZE) / 2,
						(MapCore.OVERWORLD_SIZE * MapCore.CHUNK_WIDTH * MapCore.OVERWORLD_TILE_SIZE) / 2),
				pos,
				new Stats(8,1,1,1,1,1,1),
				new Entity(new Vector2(0, 0), new Vector2(2, 2)),
				playerEntity,
				MainCore.camera,
				AABB);

	}

	public static void SpawnActor(Vector2 pos, String sprite, Vector2 spriteSize, Vector2 AABBsize, boolean isXCenteredAABB){

		Actor actor;

		Vector2 AABBPos = new Vector2((pos.x + ((spriteSize.x * MapCore.LEVEL_TILE_SIZE) / 2)) - (AABBsize.x / 2), pos.y);

		BoundingBox AABB = new BoundingBox(
				AABBPos,
				AABBsize,
				AssetManager.COLOR_RED,
				true, PhysicsConstants.FULL_BLOCK);

		actor = new Actor(pos, spriteSize, AABB, "Entities/" + sprite);

		LevelManager.actorList.add(actor);

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

		List<Actor> toRender = new ArrayList<Actor>();

		toRender.addAll(LevelManager.actorList);
		toRender.add(player);

		YSort(toRender);

		//player.render();

		for (Actor actor: toRender) {
			actor.render();
		}

		//for (Actor actor: LevelManager.actorList) {
		//	actor.render();
		//}



	}

	public static void YSort(List<Actor> entities)
	{

		Actor temp;

		for (int i = 0; i < entities.size(); i++)
		{
			for (int j = 0; j < entities.size(); j++)
			{
				if (entities.get(i).getPosition().y > entities.get(j).getPosition().y)
				{
					temp = entities.get(i);

					entities.set(i, entities.get(j));

					entities.set(j, temp);
				}
			}
		}

	}

	public void renderUI(float delta)  {

		//DEBUG DRAWS!!!
		AssetManager.fontWhite.draw(MainCore.UIBatch, "GameCore ticks: " + GameCore.ticks, 8,32);
		AssetManager.fontWhite.draw(MainCore.UIBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 8,64);
		AssetManager.fontWhite.draw(MainCore.UIBatch, "Memory usage: " + currentMemory + " MB", 8,96);
		AssetManager.fontWhite.draw(MainCore.UIBatch, "MapCore ticks: " + MapCore.ticks, 8,128);

		if(player.inLevel) {
			AssetManager.fontWhite.draw(MainCore.UIBatch, "Player Speed: " + "[" + player.getLevelAABB().velocity + "]", 8,128 + 32);
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
