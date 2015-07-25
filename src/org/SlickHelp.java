package org;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utils.SlickButton;
import utils.jbox2slick;

public class SlickHelp extends ShipGame {
	
	public SlickButton leftButton;
	public SlickButton rightButton;
	public SlickButton closeButton;
	
	private static Input in;
	private static Image[] pictures;
	private byte index;

	public SlickHelp(String title, Image[] p) {
		super(title);
		pictures = p;
	}
	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		arg1.drawImage(pictures[index], 0, 0, arg0.getWidth(), arg0.getHeight(), 
				0, 0, pictures[index].getWidth(), pictures[index].getHeight());
		
		leftButton.draw(arg1);
		rightButton.draw(arg1);
		closeButton.draw(arg1);
		
		arg1.setColor(Color.black);
		
		leftButton.drawText(arg1);
		rightButton.drawText(arg1);
		closeButton.drawText(arg1);
		
		
	}

	
	public void init(GameContainer arg0) throws SlickException {
		if(arg0 instanceof AppGameContainer){
			app = (AppGameContainer) arg0;
		}
		
		in = arg0.getInput();
		
		jbox2slick tr = ShipGame.tr;
		
		Color lightblue1 = new Color(51,153,255);
		Color lightblue2 = new Color(0,102,204);
		
		int[] loc = tr.toSlick(-119, -51);
		int[] wid = tr.wToSlick(15, 15);
		
		leftButton = new SlickButton(loc[0],loc[1],wid[0],wid[1],
				lightblue1,Color.blue,Color.cyan,lightblue2,10,
				"<");
		
		loc = tr.toSlick(103, -51);
		
		rightButton = new SlickButton(loc[0],loc[1],wid[0],wid[1],
				lightblue1,Color.blue,Color.cyan,lightblue2,10,
				">");
		
		loc = tr.toSlick(103, 63);
		
		closeButton =  new SlickButton(loc[0],loc[1],wid[0],wid[1],
				lightblue1,Color.blue,Color.cyan,lightblue2,10,
				"X");
		
		
	}

	
	public void update(GameContainer arg0, int arg1) throws SlickException {
		leftButton.update(in);
		rightButton.update(in);
		closeButton.update(in);
		
		if(leftButton.clicked){
			index--;
			if(index<0){
				index = (byte) (pictures.length-1);
			}
		} else if(rightButton.clicked){
			index++;
			if(index>pictures.length-1){
				index=0;
			}
		}
		
		
	}

}
