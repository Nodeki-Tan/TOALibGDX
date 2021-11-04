package dev.fenixsoft.toa.core.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.GameCore;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.entities.DebugSquareEntity;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.EntityManager;
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

    List<BoundingBox> boundingBoxList = new ArrayList<BoundingBox>();

    public PhysicsTestState() {
    }

    public void init() {

        playerEntity = new Entity(new Vector2(10, 10), new Vector2(4, 4));
        player = new BoundingBox(new Vector2(10,80), new Vector2(10,24), AssetManager.COLOR_RED, true);

        boundingBoxList.add(player);


        for (int i = 0; i < 2500; i++) {
            BoundingBox temp = new BoundingBox(
                    new Vector2(8 * i,0), new Vector2(8,8), AssetManager.COLOR_WHITE, true);
            boundingBoxList.add(temp);
        }

        for (int i = 0; i < 2500; i++) {
            BoundingBox temp = new BoundingBox(
                    new Vector2(8 * i,64), new Vector2(8,8), AssetManager.COLOR_WHITE, true);
            boundingBoxList.add(temp);
        }


        done = true;

    }

    public void tick(float delta) {

        float speed = 10;
        float maxSpeed = 100;
        float gravity = 300;
        float jumpPower = 200;

        player.velocity.y -= gravity * delta;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.velocity.x += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.velocity.x -= speed;
        }

        RayContactResult out = new RayContactResult();

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(PhysicsSolver.rayVsRect
                    (player.getPosition(),
                            Maths.sub(
                                    new Vector2(player.getPosition().x, player.getPosition().y + 4),
                                    player.getPosition()
                            ),
                            boundingBoxList,
                            out)){
                if (out.contactTime <= 1.0f) player.velocity.y = jumpPower;
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

        if (elapsedTime >= 1) {
            elapsedTime = 0;
            currentMemory = (int) ((Gdx.app.getJavaHeap() / 1024) / 1024);
        }

        elapsedTime += Gdx.graphics.getDeltaTime();

    }

    public void renderTick(float delta) {
        playerEntity.setPosition(player.getPosition());
    }

    public void render(float delta)  {
        playerEntity.render("Entities/Human/Walk", 0, 12, 0);
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
