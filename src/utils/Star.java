package utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Star {
	private int xpos;
	private int ypos;
	private Color c;
	public Star(int x, int y, Color color){
		xpos = x;
		ypos = y;
		c=color;
	}
	public void render(Graphics g){
		g.setColor(c);
		g.fillRoundRect(xpos, ypos, 4, 4, 1);
	}
}
