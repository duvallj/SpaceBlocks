package utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class SlickButton {
	
	private int x;
	private int y;
	private int w;
	private int h;
	private int bW;
	private Color ns1;
	private Color ns2;
	private Color s1;
	private Color s2;
	public String text;
	
	public boolean selected=false;
	public boolean clicked=false;
	public boolean shown=true;
	
	public SlickButton(int locX, int locY, int width, int height, Color nonSel1, Color nonSel2, Color sel1, Color sel2, int boundaryWidth, String t){
		
		x=locX;
		y=locY;
		w=width;
		h=height;
		ns1=nonSel1;
		ns2=nonSel2;
		s1=sel1;
		s2=sel2;
		bW=boundaryWidth;
		text=t;
		
	}
	
	public void update(Input in){
		if(shown){
		float mX = in.getMouseX();
		float mY = in.getMouseY();
		
		if(mX > x && mY > y &&
				mX < x+w && mY < y+h){
			selected=true;
		} else {
			selected=false;
		}
		
		
		if(selected && in.isMousePressed(0)){
			clicked=true;
		} else {
			clicked=false;
		}
		}else{
			selected=false;
			clicked=false;
		}
	}
	
	public void draw(Graphics g){
		if(shown){
		if(selected){
			g.setColor(s2);
		} else {
			g.setColor(ns2);
		}
		
		g.fillRect(x, y, w, h);
		
		if(selected){
			g.setColor(s1);
		} else {
			g.setColor(ns1);
		}
		
		g.fillRect(x+bW, y+bW, w-2*bW, h-2*bW);
		}
	}
	public void drawText(Graphics g){
		if(shown){
			g.drawString(text, x+w/2-text.length()*5, y+h/2-10);
		}
	}
}
