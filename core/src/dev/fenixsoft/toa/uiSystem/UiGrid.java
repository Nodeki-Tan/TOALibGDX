package dev.fenixsoft.toa.uiSystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.*;

public class UiGrid {

    private Map<Vector2, UiSprite> ItemsList= new HashMap<>();

    private int cursorX = 0;
    private int cursorY = 0;
    private float bottomleftX;
    private float bottomleftY;
    private float width = 8.0F;
    private float height = 8.0F;
    private float distanceX = 1.0F;
    private float distanceY = 1.0F;

    public Vector2 position = new Vector2(0,0);

    public int getCursorX() {
        return this.cursorX;
    }

    public void setCursorX(int value) {
        this.cursorX = value;
    }

    public int getCursorY() {
        return this.cursorY;
    }

    public void setCursorY(int value) {
        this.cursorY = value;
    }

    public float getBottomleftX() {
        return this.bottomleftX;
    }

    public void setBottomleftX(float value) {
        this.bottomleftX = value;
    }

    public float getBottomleftY() {
        return this.bottomleftY;
    }

    public void setBottomleftY(float value) {
        this.bottomleftY = value;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float value) {
        this.width = value;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float value) {
        this.height = value;
    }

    public float getDistanceX() {
        return this.distanceX;
    }

    public void setDistanceX(float value) {
        this.distanceX = value;
    }

    public float getDistanceY() {
        return this.distanceY;
    }

    public void setDistanceY(float value) {
        this.distanceY = value;
    }

    public void addBlock(Vector2 pos, UiSprite block) {
        ItemsList.put(pos, block);
    }

    public final void removeBlock(Vector2 pos) {
        ItemsList.remove(pos);
    }

    public final void positionBlocks() {
        float maxWidth = 0.0F;
        float maxHeight = 0.0F;
        int maxY = 0;
        int maxX = 0;

        try {

            Vector2 operator = new Vector2(0,0);

            for (Vector2 vec: ItemsList.keySet()) {

                if(vec.x > operator.x) operator.x = vec.x;

                if(vec.y > operator.y) operator.y = vec.y;
            }

            maxY = (int)operator.y;
            maxX = (int)operator.x;

            Vector2 sizeOperator = new Vector2(0,0);

            for (UiSprite sprite: ItemsList.values()) {

                if(sprite.aimedSize.x > sizeOperator.x) sizeOperator.x = sprite.aimedSize.x;

                if(sprite.aimedSize.y > sizeOperator.y) sizeOperator.y = sprite.aimedSize.y;
            }

            maxWidth = sizeOperator.x;
            maxHeight = sizeOperator.y;

        } catch (Exception var) {
            try {
                throw (Throwable)(new Error("no blocks were added\n\n ...or something weird happened"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        for(Vector2 vec: ItemsList.keySet())
        {
            UiSprite sprite = ItemsList.get(vec);

            sprite.setX((vec.x * maxWidth) + bottomleftX);
            sprite.setY((vec.y * maxHeight) + bottomleftY);
        }

    }

    public final void resizeAllByTextSize() {
        Vector2 holder = new Vector2(0.0F, 0.0F);

        for(Vector2 vec: ItemsList.keySet())
        {
            UiSprite sprite = ItemsList.get(vec);

            sprite.resizeByText();

            if (holder.x < sprite.aimedSize.x || holder.y < sprite.aimedSize.y){
                holder = sprite.aimedSize;
            }
        }

        for(Vector2 vec: ItemsList.keySet())
        {
            UiSprite sprite = ItemsList.get(vec);

            sprite.aimedSize = holder;
            sprite.centerText();
        }

    }

    public final void draw(SpriteBatch batch) {

        for(Vector2 vec: ItemsList.keySet())
        {
            UiSprite sprite = ItemsList.get(vec);

            sprite.drawBody(batch, position);
            sprite.drawText(batch, sprite.font, position);
        }

    }

    public final void updateKeyboardHeld(float delta) {
        getBlockAt(cursorX, cursorY).keyboardHoverTimer.tick(delta);
    }

    public final void press() {

        getBlockAt(cursorX, cursorY).press();

    }

    /*public final void moveCursorVertical(boolean up, dev.minimallogic.uisystem.ui.Options options) {

    }

    public final void moveCursorHorizontal(boolean right, dev.minimallogic.uisystem.ui.Options options) {

    }*/

    public UiSprite getBlockAt(int x, int y) {
        UiSprite sprite = ItemsList.get(new Vector2(x,y));

        if(sprite == null) return null;

        return sprite;
    }

    public final void rePositionSingleBlock(UiSprite block, Vector2 position) {

    }

    public UiGrid(float width, float height, float bottomleftX, float bottomleftY, Vector2 _position) {
        this.width = width;
        this.height = height;
        this.bottomleftX = bottomleftX;
        this.bottomleftY = bottomleftY;

        position = _position;
    }
}
