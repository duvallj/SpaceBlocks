package org;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;

public interface ShipGameTemplate {

	AppGameContainer app = null;
	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException;
	public void init(GameContainer arg0) throws SlickException;
	public void update(GameContainer arg0, int arg1) throws SlickException;
	public boolean closeRequested();
	public void destroyOpenAL(Audio... buffers) throws SlickException;
	public void destroyOpenAL() throws SlickException;
	public void clearBuffer(Audio audio) throws SlickException;
	public void detachBuffer(int sourceID);
	
}
