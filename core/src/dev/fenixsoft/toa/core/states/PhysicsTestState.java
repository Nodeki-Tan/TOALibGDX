package dev.fenixsoft.toa.core.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.GameCore;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.entities.DebugSquareEntity;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.EntityManager;
import dev.fenixsoft.toa.physics.PhysicsConstants;
import dev.fenixsoft.toa.physics.PhysicsSolver;
import dev.fenixsoft.toa.physics.RayContactResult;
import dev.fenixsoft.toa.physics.BoundingBox;
import dev.fenixsoft.toa.toolbox.Maths;

import java.util.ArrayList;
import java.util.List;

public class PhysicsTestState extends State{

    private float elapsedTime = 0.0f;
    int currentMemory;

    BoundingBox player;
    Entity playerEntity;

    int frame = 1;
    Vector2 dir = new Vector2(1,0);

    List<BoundingBox> boundingBoxList = new ArrayList<BoundingBox>();

    public PhysicsTestState() {
    }

    public void init() {

        playerEntity = new Entity(new Vector2(10, 10), new Vector2(48.0f / MapCore.LEVEL_TILE_SIZE, 26.0f / MapCore.LEVEL_TILE_SIZE));
        player = new BoundingBox(
                new Vector2(10,80),
                new Vector2(10,24),
                AssetManager.COLOR_RED,
                true, PhysicsConstants.FULL_BLOCK);

        boundingBoxList.add(player);


        for (int i = 0; i < 2500; i++) {
            BoundingBox temp = new BoundingBox(
                    new Vector2(8 * i,0),
                    new Vector2(8,8),
                    AssetManager.COLOR_WHITE,
                    true,  PhysicsConstants.FULL_BLOCK);
            boundingBoxList.add(temp);
        }

        for (int i = 0; i < 2500; i++) {
            BoundingBox temp = new BoundingBox(
                    new Vector2(8 * i,64),
                    new Vector2(8,8),
                    AssetManager.COLOR_PURPLE,
                    true, PhysicsConstants.TOP_BLOCK);
            boundingBoxList.add(temp);
        }

        for (int x = 0; x < 1; x++) {
            for (int y = 0; y < 2; y++) {

                BoundingBox temp = new BoundingBox(
                        new Vector2(32 + (8 * x),72 + (8 * y)),
                        new Vector2(8,8),
                        AssetManager.COLOR_BLUE,
                        true, PhysicsConstants.LEFT_BLOCK);
                boundingBoxList.add(temp);

            }
        }

        for (int x = 0; x < 1; x++) {
            for (int y = 0; y < 2; y++) {

                BoundingBox temp = new BoundingBox(
                        new Vector2(56 + (8 * x),72 + (8 * y)),
                        new Vector2(8,8),
                        AssetManager.COLOR_ORANGE,
                        true, PhysicsConstants.RIGHT_BLOCK);
                boundingBoxList.add(temp);

            }
        }


        done = true;

    }

    public void tick(float delta) {

        float speed = 10;
        float maxSpeed = 100;
        float gravity = 300;
        float jumpPower = 200;

        player.velocity.y -= gravity * delta;

        if (dir.x == -2)
            dir.x = -1;
        else if (dir.x == 2)
            dir.x = 1;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.velocity.x += speed;

            dir.x = 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.velocity.x -= speed;

            dir.x = -2;
        }

        RayContactResult out = new RayContactResult();

        Vector2 rayPos = new Vector2(
                player.getPosition().x + (player.getScale().x / 2.0f),
                player.getPosition().y - 4);

        //System.out.println("player is at " + player.getPosition() + " and its raycasting to " + rayPos);

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(PhysicsSolver.rayVsRect
                            (player.getPosition(),
                                    rayPos,
                            boundingBoxList,
                            out)){
                System.out.println("contact!");
                if (out.contactTime >= 0.0f && out.contactTime < 1.0f) {


                    player.velocity.y = jumpPower;
                }
            }
        }

        if (player.velocity.x >= maxSpeed){
            player.velocity.x = maxSpeed;
        } else if (player.velocity.x <= -maxSpeed){
            player.velocity.x = -maxSpeed;
        }



        if (player.velocity.y >= jumpPower){
            player.velocity.y = jumpPower;
        } else if (player.velocity.y <= -gravity){
            player.velocity.y = -gravity;
        }

        PhysicsSolver.solveIteractionsInArea(player, boundingBoxList, delta);



    }

    public void renderTick(float delta) {
        playerEntity.setPosition(player.getPosition());

        if (elapsedTime >= 0.2f) {
            elapsedTime = 0;


            if(dir.x == -2 || dir.x == 2) {
                frame++;
                if (frame >= 6) frame = 0;
            }else{
                frame = 1;
            }

            currentMemory = (int) ((Gdx.app.getJavaHeap() / 1024) / 1024);
        }

        elapsedTime += Gdx.graphics.getDeltaTime();

        MainCore.camera.setPosition(player.getPosition().x, player.getPosition().y + (360 / 4));
    }

    public void render(float delta)  {

        MainCore.worldBatch.draw(AssetManager.entityAtlas.findRegions("Parallax/Capa").get(2),
                MainCore.camera.getPosition().x - ((1280 / 2) / 2),
                MainCore.camera.getPosition().y - (360 / 4),
                1280,
                360);

        MainCore.worldBatch.draw(AssetManager.entityAtlas.findRegions("Parallax/Capa").get(1),
                MainCore.camera.getPosition().x - ((1280 / 2) / 2),
                MainCore.camera.getPosition().y - (360 / 4),
                1280,
                360);

        MainCore.worldBatch.draw(AssetManager.entityAtlas.findRegions("Parallax/Capa").get(0),
                MainCore.camera.getPosition().x - ((1280 / 2) / 2),
                MainCore.camera.getPosition().y - (360 / 4),
                1280,
                360);

        if(dir.x == -2|| dir.x == 2)
            if (dir.x == -2)
                playerEntity.render("Entities/Human/Player/run/Run", frame, 18, 1, true);
            else
                playerEntity.render("Entities/Human/Player/run/Run", frame, 18, 1);
        else if(dir.x == -1|| dir.x == 1)
            if (dir.x == -1)
                playerEntity.render("Entities/Human/Player/run/Run", 0, 18, 1, true);
            else
                playerEntity.render("Entities/Human/Player/run/Run", 0, 18, 1);
    }

    public void renderUI(float delta)  {

        //DEBUG DRAWS!!!
        AssetManager.white.draw(MainCore.screenBatch, "GameCore ticks: " + GameCore.ticks, 8,32);
        AssetManager.white.draw(MainCore.screenBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 8,64);
        AssetManager.white.draw(MainCore.screenBatch, "Memory usage: " + currentMemory + " MB", 8,96);

        AssetManager.white.draw(MainCore.screenBatch, "Current A mover Velocity: " + player.velocity , 8,96 + 32);
    }

    @Override
    public void dispose() {

    }

}
