package dev.fenixsoft.toa.core.states;

public abstract class State {

	public boolean done = false;
	public abstract void init();
	public abstract void tick(float delta);
	public abstract void renderTick(float delta);
	public abstract void render(float delta);
	public abstract void renderUI(float delta);
	public abstract void dispose();

}
