package dev.fenixsoft.toa.entities.player;

import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.entities.Entity;
import dev.fenixsoft.toa.stats.Stats;

public class Player{

    public boolean inLevel = true;

    private Vector2 overworldPosition;
    private Vector2 levelPosition;

    private Stats playerStats;

    private Entity overworldSprite;
    private Entity levelSprite;

    public Player(Vector2 overworldPosition, Vector2 levelPosition, Stats playerStats, Entity overworldSprite, Entity levelSprite) {
        this.overworldPosition = overworldPosition;
        this.levelPosition = levelPosition;
        this.playerStats = playerStats;
        this.overworldSprite = overworldSprite;
        this.levelSprite = levelSprite;
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

}
