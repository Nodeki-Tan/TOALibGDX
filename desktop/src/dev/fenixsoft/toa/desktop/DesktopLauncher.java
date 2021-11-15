package dev.fenixsoft.toa.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.managers.AssetManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = MainCore.TITLE + MainCore.VERSION ;
		config.width = 1280;
		config.height = 720;
		config.resizable = true;

		//Pack entity textures
		//TODO: Remove this before releasing the game!!!
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.pot = true;
		settings.fast = true;
		settings.combineSubdirectories = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.duplicatePadding = true;
		settings.maxHeight = 2048;
		settings.maxWidth = 2048;
		TexturePacker.process(settings, AssetManager.ASSET_PATH + "rawEntities", AssetManager.ASSET_PATH + "./", "EntitiesAtlas");

		//Pack GUI textures
		//TODO: Remove this before releasing the game!!!
		//TexturePacker.process(settings, AssetManager.ASSET_PATH + "rawGUI", AssetManager.ASSET_PATH + "./", "guiAtlas");

		new LwjglApplication(new MainCore(), config);
	}
}
