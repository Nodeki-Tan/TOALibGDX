package dev.fenixsoft.toa.managers;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager {

	public static TextureAtlas entityAtlas;
	public static TextureAtlas GUIAtlas;
	public static BitmapFont fontWhite, black;
	public static Skin menuSkin;

	public static String ASSET_PATH = "core/assets/";

	public static Color[] COLORS = new Color[256];

	static byte pointer = 0;
	public static byte COLOR_BLACK = pointer++;
	public static byte COLOR_WHITE = pointer++;
	public static byte COLOR_YELLOW = pointer++;
	public static byte COLOR_BLUE = pointer++;
	public static byte COLOR_RED = pointer++;
	public static byte COLOR_GREEN = pointer++;
	public static byte COLOR_PURPLE = pointer++;
	public static byte COLOR_ORANGE = pointer++;

	public static void initAssetsCore(){
		ConstantsManager.init();

		entityAtlas = new TextureAtlas(ASSET_PATH + "EntitiesAtlas.atlas");
		GUIAtlas = new TextureAtlas(ASSET_PATH + "guiAtlas.atlas");
		fontWhite = new BitmapFont(new FileHandle(ASSET_PATH + "Font/AstoniaWhite.fnt"), false);
		black = new BitmapFont(new FileHandle(ASSET_PATH + "Font/AstoniaBlack.fnt"), false);

		menuSkin = new Skin(GUIAtlas);

		// DEFINE COLOR CONSTANTS FOR MEMORY SAVING
		COLORS[COLOR_BLACK] = Color.BLACK;
		COLORS[COLOR_WHITE] = Color.WHITE;
		COLORS[COLOR_YELLOW] = Color.YELLOW;
		COLORS[COLOR_BLUE] = Color.BLUE;
		COLORS[COLOR_RED] = Color.RED;
		COLORS[COLOR_GREEN] = Color.GREEN;
		COLORS[COLOR_PURPLE] = Color.PURPLE;
		COLORS[COLOR_ORANGE] = Color.ORANGE;



		TileManager.loadTiles();
	}
	
	public static void cleanUp() {
		entityAtlas.dispose();
		GUIAtlas.dispose();
		menuSkin.dispose();
		fontWhite.dispose();
		black.dispose();

		TileManager.cleanUp();
	}
	
}
