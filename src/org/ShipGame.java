package org;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;

import utils.jbox2slick;

public class ShipGame extends BasicGame implements ShipGameTemplate{
	
	AppGameContainer app;
	public static int[][] player1Ship = null;
	public static int[][] player2Ship = null;
	public static jbox2slick tr = null;
	public static int level = 15000;
	public static boolean over = false;
	public int winner;
	public int p1Orbs=0;
	public int p2Orbs=0;

	public ShipGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	
	public void init(GameContainer arg0) throws SlickException {
		if(arg0 instanceof AppGameContainer){
			app = (AppGameContainer) arg0;
		}
		
	}

	
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public boolean closeRequested()
    {
      try {
		destroyOpenAL();
      } catch (SlickException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
      //app.destroy();
      app.exit();
      return false;
    }
	
	/**
     * Stops and releases all sources, clears each of the specified Audio
     * buffers, destroys the OpenAL context, and resets SoundStore for future use. 
     * 
     * Calling SoundStore.get().init() will re-initialize the OpenAL context
     * after a call to destroyOpenAL (Note: AudioLoader.getXXX calls init for you).
     * 
     * @param buffers any loaded Audio data to also clear; or null
     * @throws SlickException if there was an OpenAL error 
     */
    public void destroyOpenAL(Audio... buffers) throws SlickException {
        //first clear the sources allocated by SoundStore
        int max = SoundStore.get().getSourceCount();
        IntBuffer buf = BufferUtils.createIntBuffer(max);
        for (int i=0; i<max; i++) {
            int source = SoundStore.get().getSource(i);
            buf.put(source);
            //stop and detach any buffers at this source
            detachBuffer(source);
        }
        buf.flip();
        AL10.alDeleteSources(buf);
        int exc = AL10.alGetError();
        if (exc != AL10.AL_NO_ERROR) {
            throw new SlickException("Could not clear SoundStore sources, err: "+exc);
        }
        
        //delete any buffer data stored in memory, too...
        if (buffers!=null) {
            for (Audio a : buffers) {
                clearBuffer(a);
            }
        }
        
        //clear OpenAL
        AL.destroy();
        
        //reset SoundStore so that next time we create a Sound/Music, it will reinit
        SoundStore.get().clear();
    }
    
    /**
     * Included for convenience to destroy the OpenAL context without specifically
     * clearing any Audio buffers.
     * 
     * @throws SlickException 
     */
    public void destroyOpenAL() throws SlickException {
        destroyOpenAL((Audio[])null);
    }
    
    /** 
     * Releases the given Audio data from memory (this makes the Audio instance
     * unusable -- reload the sound with AudioLoader in order to use it again).
     * 
     * Be sure to detach the buffer from its source before releasing it from
     * memory -- use detachBuffer(int).
     * 
     * @audio the audio instance holding the buffer
     * @throws SlickException if there was an OpenAL error 
     */
    public void clearBuffer(Audio audio) throws SlickException {
        if (audio==null || audio.getBufferID()==0)
            return;
        IntBuffer buf = BufferUtils.createIntBuffer(1).put(audio.getBufferID());
        buf.flip();
        AL10.alDeleteBuffers(buf);
        int exc = AL10.alGetError();
        if (exc != AL10.AL_NO_ERROR) {
            throw new SlickException("Could not clear buffer "
                        + audio.getBufferID()
                        + ", err: "+exc);
        }
    }
    
    /** 
     * Stops the source and detaches the associated buffer (if one exists).
     * 
     * @param sourceID the OpenAL id for the source (see SoundStore.getSource)
     */
    public void detachBuffer(int sourceID) {
        //detach a buffer if it exists
        AL10.alSourceStop(sourceID);
        AL10.alSourcei(sourceID, AL10.AL_BUFFER, 0);
    }

}
