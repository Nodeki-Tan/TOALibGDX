package dev.fenixsoft.toa.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MapCore;

public class Camera {
	
	private Vector2 position = new Vector2(0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float cameraSpeed = 4f * MapCore.LEVEL_TILE_SIZE;

	public Camera(){}
	
	public void tick() {

		move();

	}
	
	public void move(){
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			position.y-= cameraSpeed * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			position.y+= cameraSpeed * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			position.x+= cameraSpeed * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			position.x-= cameraSpeed * Gdx.graphics.getDeltaTime();
		}
		
	}

    public void setPosition(Vector2 position) {
        this.position = position;
    }

	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
}
