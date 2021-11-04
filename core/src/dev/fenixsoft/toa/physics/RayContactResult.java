package dev.fenixsoft.toa.physics;

import com.badlogic.gdx.math.Vector2;

public class RayContactResult {

    public Vector2 contactPoint, contactNormal;
    public float contactTime = 0;

    public RayContactResult(){
        contactPoint = new Vector2(0,0);
        contactNormal = new Vector2(0,0);
    }

    public void setContactPoint(Vector2 _position) {
        this.contactPoint.x = _position.x;
        this.contactPoint.y = _position.y;
    }

}
