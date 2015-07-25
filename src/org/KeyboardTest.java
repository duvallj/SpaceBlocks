package org;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class KeyboardTest extends BasicGame {

	private Input input;

	public KeyboardTest(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		try {
			AppGameContainer container = new AppGameContainer(new KeyboardTest("test"));
			container.setDisplayMode(10,10,false);    		//last arg = fullscreen
			container.start();
			container.exit();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		input = container.getInput();

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		System.out.print("up= ");
		System.out.println(input.isKeyDown(Input.KEY_UP));
		System.out.print("left= ");
		System.out.println(input.isKeyDown(Input.KEY_LEFT));
		System.out.print("down= ");
		System.out.println(input.isKeyDown(Input.KEY_DOWN));
		System.out.print("right= ");
		System.out.println(input.isKeyDown(Input.KEY_RIGHT));
		System.out.println(Input.KEY_A);

	}

}
