package dev.fenixsoft.toa.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.fenixsoft.toa.entities.Camera;
import dev.fenixsoft.toa.entities.DebugSquareEntity;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.EntityManager;
import dev.fenixsoft.toa.managers.StateManager;

import java.util.List;

public class MainCore extends Game {

	public static final String VERSION = " 0.0.1a ";
	public static final String TITLE = "Project TOA!";

	private static GameCore core;

	public static Camera camera;

	public static OrthographicCamera renderCamera;
	FitViewport viewport;

	public static SpriteBatch worldBatch;
	public static SpriteBatch UIBatch;
	public static SpriteBatch screenBatch;

	public static ShapeRenderer shapeRenderer;

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;

	FrameBuffer screenBuffer;
	Texture renderedScreenTexture;
	TextureRegion fboTextureRegion;

	@Override
	public void create () {
		worldBatch = new SpriteBatch();
		UIBatch = new SpriteBatch();
		screenBatch = new SpriteBatch();

		shapeRenderer = new ShapeRenderer();

		SCREEN_WIDTH = Gdx.graphics.getWidth() / 4;
		SCREEN_HEIGHT = Gdx.graphics.getHeight() / 4;

		AssetManager.initAssetsCore();
		camera = new Camera();

		renderCamera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);

		renderCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, renderCamera);

		screenBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, SCREEN_WIDTH, SCREEN_HEIGHT, false);

		UIBatch.setProjectionMatrix(renderCamera.combined);

		core = new GameCore();
		core.start();
	}

	public void render() {

		//--- render frame to buffer
		screenBuffer.begin();
		//--- render the game
		renderGame();

		//--- close the frame buffer
		screenBuffer.end(
				viewport.getScreenX(),
				viewport.getScreenY(),
				viewport.getScreenWidth(),
				viewport.getScreenHeight());

		//--- create texture from frameBuffer
		renderedScreenTexture = screenBuffer.getColorBufferTexture();
		renderedScreenTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
		fboTextureRegion = new TextureRegion(renderedScreenTexture);
		fboTextureRegion.flip(false, true);

		//--- finally render frame with postprocess shader
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//--- apply the shader
		//screenBatch.setShader(monoShader);

		//--- draw the damn thing
		screenBatch.begin();
		screenBatch.draw(fboTextureRegion, 0, 0, 1280, 720);
		screenBatch.end();

	}

	void renderGame(){
		Gdx.gl.glClearColor(184 / 255.0f, 204 / 255.0f, 216 / 255.0f, 255 / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//calculate renderTick(sprite position, camera mov etc, to prepare for rendering)
		if (StateManager.getCurrentState() != null && StateManager.getCurrentState().done) {
			StateManager.getCurrentState().renderTick(Gdx.graphics.getDeltaTime());
		}

		renderCamera.position.set(camera.getPosition(), 0);

		renderCamera.update();

		//render Entities stuff

		worldBatch.begin();

		worldBatch.setProjectionMatrix(renderCamera.combined);

		//Reset color to white in case needed
		worldBatch.setColor(Color.WHITE);

		//render the current state
		if (StateManager.getCurrentState() != null && StateManager.getCurrentState().done) {
			StateManager.getCurrentState().render(Gdx.graphics.getDeltaTime());
		}

		worldBatch.end();

		//render debug stuff
		if(GameCore.debugMode){
			renderDebugSquares(EntityManager.debugSquareEntityList);
		}

		//render UI stuff
		UIBatch.begin();

		UIBatch.setColor(Color.WHITE);

		//render the current state UI
		if (StateManager.getCurrentState() != null && StateManager.getCurrentState().done) {
			StateManager.getCurrentState().renderUI(Gdx.graphics.getDeltaTime());
		}

		UIBatch.end();

	}

	public static void renderDebugSquares(List<DebugSquareEntity> debugSquareEntityList){

		shapeRenderer.setProjectionMatrix(worldBatch.getProjectionMatrix());

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

		for (int i = 0; i < debugSquareEntityList.size(); i++) {
			debugSquareEntityList.get(i).render(shapeRenderer);
		}

		shapeRenderer.end();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		//--- dispose MainCore assets
		worldBatch.dispose();
		screenBatch.dispose();
		shapeRenderer.dispose();
		renderedScreenTexture.dispose();
		screenBuffer.dispose();

		//--- dispose GameCore assets
		core.terminateApp();
		StateManager.getCurrentState().dispose();

		//--- dispose Game assets
		AssetManager.cleanUp();
	}

}
