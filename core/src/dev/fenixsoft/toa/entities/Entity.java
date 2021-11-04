package dev.fenixsoft.toa.entities;


import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.EntityManager;

public class Entity {

	protected Vector2 position;
	protected Vector2 scale;

	public boolean isRenderable = true;

	public Entity(Vector2 _position, Vector2 _scale, boolean isTemp)  {
		this.position = _position;
		this.scale = _scale;

		if (!isTemp)
			EntityManager.entityList.add(this);
	}

	public Entity(Vector2 _position, Vector2 _scale)  {
		this.position = _position;
		this.scale = _scale;
	}

	public void increasePosition(float dx, float dy) {
		this.position.x += dx;
		this.position.y += dy;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 _position) {
		this.position.x = _position.x;
		this.position.y = _position.y;
	}

	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	public Vector2 getScale() {
		return scale;
	}
	
	public void setScale(Vector2 _scale) {
		this.scale.x = _scale.x;
		this.scale.y = _scale.y;
	}
	
	public void tick(float delta) {

	}

	public void render(String spriteName, int frame, float offsetX, float offsetY) {

		MainCore.worldBatch.draw(AssetManager.entityAtlas.findRegions(spriteName).get(frame),
				position.x - offsetX,
				position.y - offsetY,
				scale.x * MapCore.LEVEL_TILE_SIZE,
				scale.y * MapCore.LEVEL_TILE_SIZE);

	}

	public void dispose(){
		if(EntityManager.entityList.contains(this))
			EntityManager.entityList.remove(this);
	}

}
