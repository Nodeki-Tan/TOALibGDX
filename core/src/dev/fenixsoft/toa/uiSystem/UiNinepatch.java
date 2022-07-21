package dev.fenixsoft.toa.uiSystem;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.toolbox.EventListener;

public class UiNinepatch extends UiSprite{

    NinePatch patch;

    public UiNinepatch( TextureAtlas.AtlasRegion texture,
       float aimedWidth, float aimedHeight,
       EventListener clickFunction,
       EventListener mouseHoverFunction,
       EventListener keyboardHoverFunction,
       BitmapFont font,
       Vector2 _padding){

        super(texture,
                aimedWidth, aimedHeight,
                clickFunction,
                mouseHoverFunction,
                keyboardHoverFunction,
                font,
                _padding);

        //this.patch = texture. patch;

    }

    public void drawBody(SpriteBatch batch){
        // don't use offsets for the blocks
        batch.draw(this, getX(), getY());
    }

}
