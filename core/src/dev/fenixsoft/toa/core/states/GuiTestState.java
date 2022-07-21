package dev.fenixsoft.toa.core.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.TileManager;
import dev.fenixsoft.toa.uiSystem.UiGrid;
import dev.fenixsoft.toa.uiSystem.UiSprite;

public class GuiTestState extends State{

    UiGrid grid;

    public void init() {

        UiSprite sprite = new UiSprite(
                TileManager.TILE_LIST.get(0).getFrame(),
                0, 80, null,
                null, null,
                 AssetManager.fontWhite,
                 new Vector2(32, 32));

        sprite.setText("Test");

        UiSprite sprite2 = new UiSprite(
                TileManager.TILE_LIST.get(0).getFrame(),
                0, 40, null,
                null, null,
                 AssetManager.fontWhite,
                 new Vector2(10, 10));



        sprite2.setText("pruebAaAjjjjqqAAAAAA");

        grid = new UiGrid(2, 16, 0, 0, new Vector2(32, 128));

        grid.addBlock(new Vector2(0, 0), sprite);
        grid.addBlock(new Vector2(0, 1), sprite2);

        grid.resizeAllByTextSize();
        grid.positionBlocks();



        //TODO: PUT THE DONE TRIGGER AS AN UNIVERSAL THING TO HAPPEN BEFORE ALL INIT FINISHES IN STATES
        done = true;
    }

    public void tick(float delta) {
        grid.updateKeyboardHeld(delta);

        boolean Y = false;

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            Y = true;

            //grid.moveCursorVertical(Y, Options.wrap);

            System.out.println("Cursor in " + grid.getCursorY());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Y = false;

            //grid.moveCursorVertical(Y, Options.wrap);

            System.out.println("Cursor in " + grid.getCursorY());
        }




    }


    public void alertOver(String name){
        System.out.println("Cursor in " + name);
    }

    @Override
    public void renderTick(float delta) {

    }

    public void render(float delta) {

    }

    public void renderUI(float delta) {

        grid.draw(MainCore.UIBatch);

    }

    @Override
    public void dispose() {}

}