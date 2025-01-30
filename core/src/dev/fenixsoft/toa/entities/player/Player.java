package dev.fenixsoft.toa.entities.player;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.entities.Actor;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.physics.BoundingBox;
import dev.fenixsoft.toa.stats.Stats;

public class Player extends Actor {

    public boolean inLevel = true;

    protected Vector2 overworldPosition;
    protected Vector2 levelPosition;

    protected Stats playerStats;

    protected Entity overworldSprite;
    protected Entity levelSprite;

    protected BoundingBox levelAABB;
    protected Vector2 levelMoveDir = new Vector2(1,0);

    public Player(Vector2 overworldPosition, Vector2 levelPosition, Stats playerStats, Entity overworldSprite, Entity levelSprite, BoundingBox AABB) {
        super(levelPosition, levelSprite.getScale(), AABB, "");
        this.overworldPosition = overworldPosition;
        this.levelPosition = levelPosition;
        this.playerStats = playerStats;
        this.overworldSprite = overworldSprite;
        this.levelSprite = levelSprite;
        this.levelAABB = AABB;

        levelAABB.setPosition(levelPosition);
    }

    public Vector2 getOverworldPosition() {
        return overworldPosition;
    }

    public void setOverworldPosition(Vector2 overworldPosition) {
        this.overworldPosition = overworldPosition;
    }

    public Vector2 getLevelPosition() {
        return levelPosition;
    }

    public void setLevelPosition(Vector2 levelPosition) {
        this.levelPosition = levelPosition;
    }

    public Stats getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(Stats playerStats) {
        this.playerStats = playerStats;
    }

    public Entity getOverworldSprite() {
        return overworldSprite;
    }

    public void setOverworldSprite(Entity overworldSprite) {
        this.overworldSprite = overworldSprite;
    }

    public Entity getLevelSprite() {
        return levelSprite;
    }

    public void setLevelSprite(Entity levelSprite) {
        this.levelSprite = levelSprite;
    }

    public BoundingBox getLevelAABB() {
        return levelAABB;
    }

    public void setLevelAABB(BoundingBox levelAABB) {
        this.levelAABB = levelAABB;
    }

    public Vector2 getLevelMoveDir() {
        return levelMoveDir;
    }

    public void setLevelMoveDir(Vector2 levelMoveDir) {
        this.levelMoveDir = levelMoveDir;
    }
}
