package dev.fenixsoft.toa.core.states;

import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.managers.AssetManager;

public class GuiTestState extends State{

    public void init() {

        //TODO: PUT THE DONE TRIGGER AS AN UNIVERSAL THING TO HAPPEN BEFORE ALL INIT FINISHES IN STATES
        done = true;
    }

    public void tick(float delta) {

    }

    @Override
    public void renderTick(float delta) {

    }

    public void render(float delta) {

    }

    public void renderUI(float delta) {

        //DEBUG DRAWS!!!
        AssetManager.fontWhite.draw(MainCore.screenBatch, "line 1", 8,32);
        AssetManager.fontWhite.draw(MainCore.screenBatch, "line 2", 8,64);
        AssetManager.fontWhite.draw(MainCore.screenBatch, "line 3", 8,96);
        AssetManager.fontWhite.draw(MainCore.screenBatch, "line 4", 8,96 + 32);

    }

    @Override
    public void dispose() {}

}