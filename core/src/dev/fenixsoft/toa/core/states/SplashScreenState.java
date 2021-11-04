package dev.fenixsoft.toa.core.states;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dev.fenixsoft.toa.core.GameCore;
import dev.fenixsoft.toa.core.MainCore;
import dev.fenixsoft.toa.managers.AssetManager;
import dev.fenixsoft.toa.managers.StateManager;
import dev.fenixsoft.toa.toolbox.SpriteAccessor;

public class SplashScreenState extends State{

    Sprite splashScreenSprite;
    TweenManager tweenManager;

    public void init() {
        splashScreenSprite = new Sprite(AssetManager.entityAtlas.findRegion("GUI/NAOSoftLogo"));

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Tween.set(splashScreenSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splashScreenSprite, SpriteAccessor.ALPHA, 1).target(1).repeatYoyo(1, 1).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                StateManager.setCurrentState(GameCore.menuState);
            }
        }).start(tweenManager);

        //TODO: PUT THE DONE TRIGGER AS AN UNIVERSAL THING TO HAPPEN BEFORE ALL INIT FINISHES IN STATES
        done = true;
    }

    public void tick(float delta) {

    }

    @Override
    public void renderTick(float delta) {

    }

    public void render(float delta) {

    }

    public void renderUI(float delta) {
        tweenManager.update(delta);
        MainCore.screenBatch.setColor(splashScreenSprite.getColor());
        MainCore.screenBatch.draw(splashScreenSprite, 0, 0, 1280, 720);
    }

    @Override
    public void dispose() {}

}
