package dev.fenixsoft.toa.core;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.TimeUtils;
import dev.fenixsoft.toa.core.states.*;
import dev.fenixsoft.toa.managers.EntityManager;
import dev.fenixsoft.toa.managers.StateManager;

public class GameCore implements Runnable{

	public Thread gameCore;
	
	public boolean paused = false;
	public static boolean running = false;

	public static boolean debugMode = true;

	public static int ticks = 0;

	//States
	public static State splashState;
	public static State menuState;
	public static State gameState;
	public static State physicsDebugState;
	
	private void init() {

		splashState = new SplashScreenState();
		menuState = new MenuState();
		gameState = new GameState();
		physicsDebugState = new PhysicsTestState();
		StateManager.setCurrentState(splashState);
		
	}
	
	@Override
	public void run() {
        init();
        
		long lastTime = TimeUtils.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int counter = 0;

        long lastTimer = TimeUtils.millis();
        double delta = 0;

        while (running) {
        	
        	if(!paused) {
        		
	            long now = TimeUtils.nanoTime();
	            delta += (now - lastTime) / nsPerTick;
	            lastTime = now;
	
	            while (delta >= 1) {
					counter++;
	                tick();
	                delta -= 1;
	            }
	
	            try {
	                Thread.sleep(2);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	
	            if (TimeUtils.millis() - lastTimer >= 1000) {
	                lastTimer += 1000;
	                //System.out.println(ticks + " GameCore ticks");
					ticks = counter;
					counter = 0;
	            }
        	}
        }
		
		stop();
		
	}
	
	private void tick() {

		float delta = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			terminateApp();
		}

		MainCore.camera.tick();

		if (StateManager.getCurrentState() != null && StateManager.getCurrentState().done) {
			StateManager.getCurrentState().tick(delta);
			EntityManager.tick(delta);
		}



	}
	
	public synchronized void start(){
		gameCore = new Thread(this);
		running = true;
		gameCore.start();
	}

	public synchronized void stop(){

		try {
			gameCore.interrupt();
			gameCore.join();
		} catch (InterruptedException e) {}
	}

	public static void terminateApp(){
		running = false;
		Gdx.app.exit();
	}

}
