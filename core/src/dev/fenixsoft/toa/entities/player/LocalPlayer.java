package dev.fenixsoft.toa.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.entities.Camera;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.stats.Stats;

public class LocalPlayer extends Player {

    private Camera camera;

    private float overworldSpeed = 4f * MapCore.OVERWORLD_TILE_SIZE;

    public LocalPlayer(Vector2 overworldPosition, Vector2 levelPosition, Stats playerStats, Entity overworldSprite, Entity levelSprite, Camera camera) {
        super(overworldPosition, levelPosition, playerStats, overworldSprite, levelSprite);
        this.camera = camera;
    }

    public void tick() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            inLevel = !inLevel;
        }

        move();

    }

    public void move(){

        if (inLevel){

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                getLevelPosition().y -= (getPlayerStats().getSpeed() * MapCore.LEVEL_TILE_SIZE) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                getLevelPosition().y += (getPlayerStats().getSpeed() * MapCore.LEVEL_TILE_SIZE) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                getLevelPosition().x += (getPlayerStats().getSpeed() * MapCore.LEVEL_TILE_SIZE) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                getLevelPosition().x -= (getPlayerStats().getSpeed() * MapCore.LEVEL_TILE_SIZE) * Gdx.graphics.getDeltaTime();
            }

        }
        else {

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                getOverworldPosition().y -= overworldSpeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                getOverworldPosition().y += overworldSpeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                getOverworldPosition().x += overworldSpeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                getOverworldPosition().x -= overworldSpeed * Gdx.graphics.getDeltaTime();
            }

        }

    }

    public void renderTick(){

        float x = 0;
        float y = 0;

        if (inLevel){

            x = getLevelPosition().x;
            y = getLevelPosition().y;

            getLevelSprite().setPosition(x, y);

        }
        else {

            x = getOverworldPosition().x;
            y = getOverworldPosition().y;

            getOverworldSprite().setPosition(x, y);

        }

        camera.setPosition(x, y);

    }

    public void render(){

        if (inLevel){

            getLevelSprite().render("Entities/Human/Walk", 0, 16, 0);

        }
        else {

            getOverworldSprite().render("Entities/OverWorld/Viking", 0, 0, 0);

        }

    }

}
