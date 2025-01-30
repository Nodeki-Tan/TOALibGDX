package dev.fenixsoft.toa.entities;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.physics.BoundingBox;

public class Actor extends Entity{

    protected BoundingBox levelAABB;
    protected String sprite;

    public Actor(Vector2 _position, Vector2 _scale, BoundingBox levelAABB, String sprite) {
        super(_position, _scale);
        this.levelAABB = levelAABB;
        this.sprite = sprite;
    }

    public BoundingBox getLevelAABB() {
        return levelAABB;
    }

    public void setLevelAABB(BoundingBox levelAABB) {
        this.levelAABB = levelAABB;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void render(){

        render(sprite, 0, 0,  4, false);

    }

}
