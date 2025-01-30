package dev.fenixsoft.toa.mapData;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import dev.fenixsoft.toa.core.MapCore;
import dev.fenixsoft.toa.managers.AssetManager;

public class Tile {

    boolean special;
    boolean isSolid;
    boolean isCliff;
    boolean autoTile;
    byte height;

    int itemDropID;
    int expDrop;

    float resistance;

    Vector3 luminocity;

    AtlasRegion frame;

    public Tile(boolean special,
                boolean isSolid,
                boolean isCliff,
                boolean autoTile,
                byte height,
                int itemDropID,
                int expDrop,
                float resistance,
                Vector3 luminocity,
                AtlasRegion frame) {
        this.special = special;
        this.isSolid = isSolid;
        this.isCliff = isCliff;
        this.autoTile = autoTile;
        this.height = height;
        this.itemDropID = itemDropID;
        this.expDrop = expDrop;
        this.resistance = resistance;
        this.luminocity = luminocity;
        this.frame = frame;
    }

    public boolean isSpecial() {
        return special;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public boolean isCliff() {
        return isCliff;
    }

    public boolean isAutoTile() {
        return autoTile;
    }

    public byte getHeight() {
        return height;
    }

    public int getItemDropID() {
        return itemDropID;
    }

    public int getExpDrop() {
        return expDrop;
    }

    public float getResistance() {
        return resistance;
    }

    public Vector3 getLuminocity() {
        return luminocity;
    }

    public AtlasRegion getFrame() {
        return frame;
    }

}
