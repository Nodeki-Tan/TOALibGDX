package dev.fenixsoft.toa.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.entities.Camera;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.managers.LevelManager;
import dev.fenixsoft.toa.physics.BoundingBox;
import dev.fenixsoft.toa.physics.PhysicsSolver;
import dev.fenixsoft.toa.physics.RayContactResult;
import dev.fenixsoft.toa.stats.Stats;
import dev.fenixsoft.toa.toolbox.Maths;

public class LocalPlayer extends Player {

    private Camera camera;

    private float overworldSpeed = 4f * MapCore.OVERWORLD_TILE_SIZE;

    int frame = 1;
    private float elapsedTime = 0.0f;

    float speed = 10;
    float maxSpeed = 100;
    float gravity = 300;
    float jumpPower = 300;

    public LocalPlayer(Vector2 overworldPosition, Vector2 levelPosition,
                       Stats playerStats,
                       Entity overworldSprite, Entity levelSprite,
                       Camera camera,
                       BoundingBox AABB
    ) {
        super(overworldPosition, levelPosition, playerStats, overworldSprite, levelSprite, AABB);
        this.camera = camera;
    }

    public void tick(float delta) {


        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            inLevel = !inLevel;
        }


        move(delta);

    }

    public void move(float delta){

        if (inLevel){

            levelAABB.velocity.y -= gravity * delta;

            if (levelMoveDir.x == -2)
                levelMoveDir.x = -1;
            else if (levelMoveDir.x == 2)
                levelMoveDir.x = 1;


            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                levelAABB.velocity.x += speed;

                levelMoveDir.x = 2;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                levelAABB.velocity.x -= speed;

                levelMoveDir.x = -2;
            }

            RayContactResult out = new RayContactResult();

            Vector2 rayOrig = new Vector2(
                    levelAABB.getPosition().x + (levelAABB.getScale().x / 2),
                    levelAABB.getPosition().y + 1);

            Vector2 rayDir = new Vector2(
                    levelAABB.getPosition().x + (levelAABB.getScale().x / 2),
                    levelAABB.getPosition().y - 4);

            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                if(LevelManager.levelRaycast
                        (rayOrig, Maths.sub(rayDir, rayOrig),
                                levelAABB, out)){
                    System.out.println("contact! at " + out.contactTime);
                    levelAABB.velocity.y = jumpPower;
                }
            }

            if (levelAABB.velocity.x >= maxSpeed){
                levelAABB.velocity.x = maxSpeed;
            } else if (levelAABB.velocity.x <= -maxSpeed){
                levelAABB.velocity.x = -maxSpeed;
            }

            if (levelAABB.velocity.y >= jumpPower){
                levelAABB.velocity.y = jumpPower;
            } else if (levelAABB.velocity.y <= -gravity){
                levelAABB.velocity.y = -gravity;
            }

            LevelManager.levelCollisionSolving(levelAABB, delta);

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

        if (elapsedTime >= 0.1f) {
            elapsedTime = 0;


            if(levelMoveDir.x == -2 || levelMoveDir.x == 2) {
                frame++;
                if (frame >= 6) frame = 0;
            }else{
                frame = 1;
            }
        }

        elapsedTime += Gdx.graphics.getDeltaTime();

        float x = 0;
        float y = 0;

        if (inLevel){

            x = levelAABB.getPosition().x;
            y = levelAABB.getPosition().y;

            levelPosition.x = x;
            levelPosition.y = y;

            levelSprite.setPosition(levelPosition);

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

            if(levelMoveDir.x == -2|| levelMoveDir.x == 2)
                if (levelMoveDir.x == -2)
                    levelSprite.render("Entities/Human/Player/Run/Run", frame, 18, 1, true);
                else
                    levelSprite.render("Entities/Human/Player/Run/Run", frame, 18, 1);
            else if(levelMoveDir.x == -1|| levelMoveDir.x == 1)
                if (levelMoveDir.x == -1)
                    levelSprite.render("Entities/Human/Player/Run/Run", 0, 18, 1, true);
                else
                    levelSprite.render("Entities/Human/Player/Run/Run", 0, 18, 1);

        }
        else {

            getOverworldSprite().render("Entities/OverWorld/Viking", 0, 0, 0);

        }

    }

}
