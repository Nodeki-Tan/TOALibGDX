package dev.fenixsoft.toa.uiSystem;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.toolbox.EventListener;
import dev.fenixsoft.toa.toolbox.Timer;

abstract class UiEntity extends Sprite {


    // todo: hold-in-click function
    protected EventListener clickFunction;
    protected EventListener mouseHoverFunction;
    protected EventListener keyboardHoverFunction;

    protected Timer mouseHoverTimer = new Timer(null, null, 0f,1f, true);
    protected Timer keyboardHoverTimer = new Timer(null, null, 0f,1f, true);

    protected float textOffsetX;
    protected float textOffsetY;

    public UiEntity(TextureAtlas.AtlasRegion texture){ super(texture); }

    protected BitmapFont font;
    protected Vector2 sizeCache = new Vector2(0,0);
    protected String text;

    protected Vector2 aimedSize = new Vector2(0,0);
    protected Vector2 paddingSize = new Vector2(0,0);

    abstract void drawText(SpriteBatch batch, BitmapFont font, Vector2 padding);

    abstract void drawBody(SpriteBatch batch, Vector2 padding);

    abstract void centerText();

    abstract void unCenterText();

    abstract void resizeByText();

}
