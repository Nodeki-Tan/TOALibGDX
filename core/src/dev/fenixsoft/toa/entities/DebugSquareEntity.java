package dev.fenixsoft.toa.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.EntityManager;

public class DebugSquareEntity extends Entity{

    byte color = AssetManager.COLOR_WHITE;

    public DebugSquareEntity(boolean debug){
        super(new Vector2(0,0), new Vector2(0,0));

        if (debug)
            EntityManager.debugSquareEntityList.add(this);
    }

    public void setData(byte _color, Vector2 _pos, Vector2 _size){
        color = _color;
        setPosition(_pos);
        setScale(_size);
    }

    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(AssetManager.COLORS[color]);
        shapeRenderer.rect(position.x, position.y, scale.x, scale.y);
    }

    public void dispose(){
        if(EntityManager.debugSquareEntityList.contains(this))
            EntityManager.debugSquareEntityList.remove(this);
    }

}
