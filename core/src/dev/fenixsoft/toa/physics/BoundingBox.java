package dev.fenixsoft.toa.physics;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.entities.DebugSquareEntity;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.toolbox.Maths;

public class BoundingBox extends Entity {

    public Vector2 velocity = new Vector2(0,0);

    public byte COLOR = AssetManager.COLOR_WHITE;
    public byte COLLISION_MASK = 0;

    DebugSquareEntity debugSprite;

    public BoundingBox(Vector2 _position, Vector2 _scale, byte _COLOR, boolean debug, byte _COLLISION_MASK){
        super(_position, _scale, false);

        COLOR = _COLOR;

        COLLISION_MASK = _COLLISION_MASK;

        debugSprite = new DebugSquareEntity(debug);
        debugSprite.setData(COLOR, position, scale);

        isRenderable = false;
    }

    // This one is used for temporal disposable stuff such as calculations
    public BoundingBox(Vector2 _position, Vector2 _scale){
        super(_position, _scale);
    }

    public void setVelocity(Vector2 _velocity) {
        this.velocity.x = _velocity.x;
        this.velocity.y = _velocity.y;
    }

    @Override
    public void tick(float delta) {
        if(velocity != Vector2.Zero) {
            setPosition(Maths.add(getPosition(), Maths.mul(velocity, delta)));

            debugSprite.setData(COLOR, position, scale);
        }

    }

}
