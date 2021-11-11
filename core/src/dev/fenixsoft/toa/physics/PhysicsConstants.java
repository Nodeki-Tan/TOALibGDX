package dev.fenixsoft.toa.physics;

import java.util.ArrayList;
import java.util.List;

public class PhysicsConstants {

    public static ByteMask[] COLLISION_MASKS = new ByteMask[16];

    public static byte FULL_BLOCK = 0;
    public static byte TOP_BLOCK = 1;
    public static byte BOTTOM_BLOCK = 2;
    public static byte RIGHT_BLOCK = 3;
    public static byte LEFT_BLOCK = 4;


    public static void init (){

        List<ByteMask> temp = new ArrayList<>();

        // Full 0
        temp.add(new ByteMask(true, true, true, true));

        // Top 1
        temp.add(new ByteMask(true, false, false, false));

        // Bottom 2
        temp.add(new ByteMask(false, true, false, false));

        // Right 3
        temp.add(new ByteMask(false, false, true, false));

        // Left 4
        temp.add(new ByteMask(false, false, false, true));

        COLLISION_MASKS = new ByteMask[temp.size()];

        temp.toArray(COLLISION_MASKS);

    }

}

class ByteMask{

    public boolean north, south, east, west;

    public ByteMask(boolean _north, boolean _south, boolean _east, boolean _west){

        north = _north;
        south = _south;
        east = _east;
        west = _west;

    }

}
