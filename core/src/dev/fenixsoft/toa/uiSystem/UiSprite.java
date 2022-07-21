package dev.fenixsoft.toa.uiSystem;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import dev.fenixsoft.toa.toolbox.EventListener;

public class UiSprite extends UiEntity{


    private boolean textCentered = false;

    public UiSprite(
            TextureAtlas.AtlasRegion texture,
            float aimedWidth, float aimedHeight,
            EventListener clickFunction,
            EventListener mouseHoverFunction,
            EventListener keyboardHoverFunction,
            BitmapFont font,
            Vector2 _padding){
        super(texture);

        this.keyboardHoverFunction = keyboardHoverFunction;
        this.mouseHoverFunction = mouseHoverFunction;
        this.clickFunction = clickFunction;

        aimedSize.set(aimedWidth, aimedHeight);
        paddingSize = _padding;

        this.font = font;
        centerText();
    }

    public void setText(String value){
        text = value;

        GlyphLayout glyp = new GlyphLayout();
        glyp.setText(font, value);

        sizeCache.x = glyp.width;
        sizeCache.y = glyp.height;

        if(textCentered) centerText();
    }

    public void drawText(SpriteBatch batch, BitmapFont font, Vector2 padding) {
        font.draw(batch, text, getX() + padding.x + textOffsetX, getY() + padding.y + textOffsetY);
    }

    public void drawBody(SpriteBatch batch, Vector2 padding){
        // don't use offsets for the blocks
        batch.draw(this, getX() + padding.x, getY() + padding.y);
    }

    public void resizeByText(){

        GlyphLayout glyp = new GlyphLayout();
        glyp.setText(font, text);

        aimedSize = new Vector2(glyp.width + (paddingSize.x * 2), glyp.height + (paddingSize.y * 2));

        centerText();
    }

    public void centerText(){

        textCentered = true;
        textOffsetX = (aimedSize.x / 2) - (sizeCache.x / 2);
        textOffsetY = (aimedSize.y / 2) + (font.getCapHeight() / 2);

    }

    public void unCenterText(){

        textCentered = false;
        textOffsetX = 0f;
        textOffsetY = 0f;

    }

    public void press()
    {
        clickFunction.TriggerEvent();
    }

}
