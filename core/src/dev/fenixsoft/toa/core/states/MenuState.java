package dev.fenixsoft.toa.core.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.fenixsoft.toa.core.GameCore;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.StateManager;

public class MenuState extends State {

	private Stage stage;
	private Table table;
	private TextButton exitButton, startButton, physicsDebugButton, guiTestButton;
	private Label label;

	public void init() {
		stage = new Stage();
		table = new Table(AssetManager.menuSkin);

		Gdx.input.setInputProcessor(stage);

		table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

		textButtonStyle.up = AssetManager.menuSkin.getDrawable("GUI/Button_Up");
		textButtonStyle.down = AssetManager.menuSkin.getDrawable("GUI/Button_Down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = AssetManager.white;

		exitButton = new TextButton("Exit", textButtonStyle);
		startButton = new TextButton("Game test", textButtonStyle);
		physicsDebugButton = new TextButton("Debug test", textButtonStyle);
		guiTestButton = new TextButton("GUI test", textButtonStyle);

		startButton.pad(20);
		physicsDebugButton.pad(20);
		guiTestButton.pad(20);
		exitButton.pad(20);

		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				StateManager.setCurrentState(GameCore.gameState);

			}
		});

		physicsDebugButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				StateManager.setCurrentState(GameCore.physicsDebugState);

			}
		});

		guiTestButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				StateManager.setCurrentState(GameCore.guiTestState);

			}
		});

		exitButton.pad(20);
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				GameCore.terminateApp();

			}
		});

		startButton.setWidth(256);
		physicsDebugButton.setWidth(256);
		guiTestButton.setWidth(256);
		exitButton.setWidth(256);

		Label.LabelStyle labelStyle = new Label.LabelStyle(AssetManager.white, Color.WHITE);

		label = new Label(MainCore.TITLE, labelStyle);
		label.setFontScale(4);

		table.add(label);
		table.row();
		table.getCell(label).spaceBottom(64);

		table.add(startButton);
		table.row();
		table.getCell(startButton).spaceBottom(16);

		table.add(physicsDebugButton);
		table.row();
		table.getCell(physicsDebugButton).spaceBottom(16);

		table.add(guiTestButton);
		table.row();
		table.getCell(guiTestButton).spaceBottom(16);

		table.add(exitButton);

		//Debug, may get deleted or commented
		//table.debug();

		stage.addActor(table);

		done = true;
	}

	public void tick(float delta) {
		// TODO Auto-generated method stub

	}

	public void renderTick(float delta) {

	}

	public void render(float delta) {

	}

	public void renderUI(float delta) {
		stage.act(delta);

		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
