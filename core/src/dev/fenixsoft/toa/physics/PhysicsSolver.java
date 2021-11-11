package dev.fenixsoft.toa.physics;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.toolbox.Maths;
import dev.fenixsoft.toa.toolbox.Utils;

import java.util.*;

public class PhysicsSolver {

    public static boolean pointVsRect(Vector2 pos, BoundingBox rect){

        return (pos.x >= rect.getPosition().x && pos.y >= rect.getPosition().y
                && pos.x < (rect.getPosition().x + rect.getScale().x)
                && pos.y < (rect.getPosition().y + rect.getScale().y));

    }

    public static boolean rectVsRect(BoundingBox rectA, BoundingBox rectB){
        return (rectA.getPosition().x < (rectB.getPosition().x + rectB.getScale().x)
                && (rectA.getPosition().x + rectA.getScale().x) > rectB.getPosition().x
                && rectA.getPosition().y < (rectB.getPosition().y + rectB.getScale().y)
                && (rectA.getPosition().y + rectA.getScale().y) > rectB.getPosition().y);
    }

    public static List<BoundingBox> simpleFirstSearch(BoundingBox in, List<BoundingBox> bodies, float delta){
        List<BoundingBox> result = new ArrayList<BoundingBox>();

        BoundingBox expandedArea = boundingMovedArea(in, delta);

        BoundingBox body = null;

        for (int i = 0; i < bodies.size(); i++) {

            body = bodies.get(i);

            if (rectVsRect(expandedArea, body) && body != in) result.add(body);

        }

        return result;
    }

    static BoundingBox boundingMovedArea(BoundingBox in, float delta){

        Vector2 pos = new Vector2(0,0);

        Vector2 movedPos = Maths.add(in.getPosition(), Maths.mul(in.velocity, delta));

        pos.x = Math.min(in.getPosition().x, movedPos.x);
        pos.y = Math.min(in.getPosition().y, movedPos.y);

        Vector2 size = new Vector2(0,0);

        size.x = (Math.max(in.getPosition().x + in.getScale().x, movedPos.x + in.getScale().x)) - pos.x;
        size.y = (Math.max(in.getPosition().y + in.getScale().y, movedPos.y + in.getScale().y)) - pos.y;

        BoundingBox expandedArea = new BoundingBox(pos, size);

        //System.out.println("Pos " + pos + " Size " + size);

        return expandedArea;
    }

    public static boolean rayVsRect(Vector2 rayOrig, Vector2 rayDir, BoundingBox target, RayContactResult out){

        Vector2 contactPoint = new Vector2(0,0);
        Vector2 contactNormal = new Vector2(0,0);

        Vector2 t_near  = new Vector2(0,0);
        Vector2 t_far   = new Vector2(0,0);

        Vector2 invdir   = new Vector2(0,0);

        invdir = Maths.divInverse(rayDir, 1.0f);

        t_near.set(Maths.mul(Maths.sub(target.getPosition(), rayOrig), invdir));
        t_far.set(Maths.mul(Maths.add(target.getPosition(), Maths.sub(target.getScale(), rayOrig)), invdir));


        if (Float.isNaN(t_far.y)     || Float.isNaN(t_far.x))    return false;
        if (Float.isNaN(t_near.y)    || Float.isNaN(t_near.x))   return false;


        if (t_near.x > t_far.x) {
            float tempA = 0;
            float tempB = 0;

            tempA = t_near.x;
            tempB = t_far.x;

            t_near.x = tempB;
            t_far.x = tempA;
        }

        if (t_near.y > t_far.y) {
            float tempA = 0;
            float tempB = 0;

            tempA = t_near.y;
            tempB = t_far.y;

            t_near.y = tempB;
            t_far.y = tempA;
        }


        if (t_near.x > t_far.y || t_near.y > t_far.x) return false;

        float t_hit_near = Math.max(t_near.x, t_near.y);
        float t_hit_far = Math.min(t_far.x, t_far.y);

        if (t_hit_far < 0) return false;

        contactPoint.set(Maths.add(rayOrig, Maths.mul(rayDir, t_hit_near)));

        if(t_near.x > t_near.y){

            if (invdir.x < 0)
                contactNormal.set(1,0);
            else
                contactNormal.set(-1,0);

        } else if(t_near.x < t_near.y){

            if (invdir.y < 0)
                contactNormal.set(0,1);
            else
                contactNormal.set(0,-1);

        }else{
            contactNormal.set(0,0);
        }

        out.contactPoint.set(contactPoint);
        out.contactNormal.set(contactNormal);
        out.contactTime = t_hit_near;

        return true;

    }

    public static boolean rayVsRect(Vector2 rayOrig, Vector2 rayDir, List<BoundingBox> bodies, RayContactResult out){

        for (BoundingBox target: bodies) {

            Vector2 contactPoint = new Vector2(0, 0);
            Vector2 contactNormal = new Vector2(0, 0);

            Vector2 t_near = new Vector2(0, 0);
            Vector2 t_far = new Vector2(0, 0);

            Vector2 invdir = new Vector2(0, 0);

            invdir = Maths.divInverse(rayDir, 1.0f);

            t_near.set(Maths.mul(Maths.sub(target.getPosition(), rayOrig), invdir));
            t_far.set(Maths.mul(Maths.add(target.getPosition(), Maths.sub(target.getScale(), rayOrig)), invdir));


            if (Float.isNaN(t_far.y) || Float.isNaN(t_far.x)) continue;
            if (Float.isNaN(t_near.y) || Float.isNaN(t_near.x)) continue;


            if (t_near.x > t_far.x) {
                float tempA = 0;
                float tempB = 0;

                tempA = t_near.x;
                tempB = t_far.x;

                t_near.x = tempB;
                t_far.x = tempA;
            }

            if (t_near.y > t_far.y) {
                float tempA = 0;
                float tempB = 0;

                tempA = t_near.y;
                tempB = t_far.y;

                t_near.y = tempB;
                t_far.y = tempA;
            }


            if (t_near.x > t_far.y || t_near.y > t_far.x) continue;

            float t_hit_near = Math.max(t_near.x, t_near.y);
            float t_hit_far = Math.min(t_far.x, t_far.y);

            if (t_hit_far < 0) continue;

            contactPoint.set(Maths.add(rayOrig, Maths.mul(rayDir, t_hit_near)));

            if (t_near.x > t_near.y) {

                if (invdir.x < 0)
                    contactNormal.set(1, 0);
                else
                    contactNormal.set(-1, 0);

            } else if (t_near.x < t_near.y) {

                if (invdir.y < 0)
                    contactNormal.set(0, 1);
                else
                    contactNormal.set(0, -1);

            } else {
                contactNormal.set(0, 0);
            }

            out.contactPoint.set(contactPoint);
            out.contactNormal.set(contactNormal);
            out.contactTime = t_hit_near;

            return true;
        }

        return false;
    }

    public static boolean dinamycRectVsRect(BoundingBox in, BoundingBox target, RayContactResult out, float deltaTime){

        if(in.velocity.x == 0 && in.velocity.y == 0) return false;

        BoundingBox expandedTarget = new BoundingBox(
                Maths.sub(target.getPosition(), Maths.div(in.getScale(), 2)),
                Maths.add(target.getScale(), in.getScale()));

        if(rayVsRect(
                Maths.add(in.getPosition(), Maths.div(in.getScale(), 2)),
                Maths.mul(in.velocity, deltaTime),
                expandedTarget,
                out)){

            if (out.contactTime >= 0.0f && out.contactTime < 1.0f) return true;

        }

        return false;

    }

    public static void solveIteractionsInArea(BoundingBox mover, List<BoundingBox> rawData, float deltaTime){

        RayContactResult out = new RayContactResult();

        BoundingBox body = null;

        List<ResultPair> list = new ArrayList<ResultPair>();
        List<BoundingBox> bodies = simpleFirstSearch(mover, rawData, deltaTime);

       // System.out.println("Clean " + bodies.size() + " Raw " + rawData.size());

        for (int i = 0; i < bodies.size(); i++) {

            body = bodies.get(i);

            if (body != mover) {
                if (dinamycRectVsRect(mover, body, out, deltaTime)){
                    list.add(new ResultPair(i, out.contactTime));
                }
            }
        }

        Utils.sort(list);

        for (int i = 0; i < list.size(); i++){

            body = bodies.get(list.get(i).id);

            if (body != mover) {
                if (dinamycRectVsRect(mover, body, out, deltaTime)) {

                    float minMov = 0.25f;
                    float friction = 0.9f;

                    Vector2 comp = new Vector2(0, -1);

                    //System.out.println("comparator " + comp + ", Normal " + out.contactNormal);

                    if(compareNormalsToMask(out, body)) {

                        //System.out.println("making contact comparator " + comp + ", Normal " + out.contactNormal);

                        if (out.contactNormal.y != 0) {
                            mover.velocity.x += out.contactNormal.x * Math.abs(mover.velocity.x) * (1.0f - out.contactTime);
                            mover.velocity.x *= friction;

                            if (mover.velocity.x <= minMov && mover.velocity.x >= -minMov) {
                                mover.velocity.x = 0;
                            }
                        } else {
                            mover.velocity.x += out.contactNormal.x * Math.abs(mover.velocity.x) * (1.0f - out.contactTime);
                        }

                        if (out.contactNormal.x != 0) {
                            mover.velocity.y += out.contactNormal.y * Math.abs(mover.velocity.y) * (1.0f - out.contactTime);
                            mover.velocity.y *= friction;

                            if (mover.velocity.y <= minMov && mover.velocity.y >= -minMov) {
                                mover.velocity.y = 0;
                            }
                        } else {
                            mover.velocity.y += out.contactNormal.y * Math.abs(mover.velocity.y) * (1.0f - out.contactTime);
                        }

                    }

                }
            }
        }

    }

    static boolean compareNormalsToMask(RayContactResult out, BoundingBox in){

        if(in.COLLISION_MASK == PhysicsConstants.FULL_BLOCK) return true;

        if(PhysicsConstants.COLLISION_MASKS[in.COLLISION_MASK].north){
            if(out.contactNormal.x == 0 && out.contactNormal.y == 1)
                return true;
        }

        if(PhysicsConstants.COLLISION_MASKS[in.COLLISION_MASK].south){
            if(out.contactNormal.x == 0 && out.contactNormal.y == -1)
                return true;
        }

        if(PhysicsConstants.COLLISION_MASKS[in.COLLISION_MASK].east){
            if(out.contactNormal.x == 1 && out.contactNormal.y == 0)
                return true;
        }

        if(PhysicsConstants.COLLISION_MASKS[in.COLLISION_MASK].west){
            if(out.contactNormal.x == -1 && out.contactNormal.y == 0)
                return true;
        }


        return false;
    }

}
