package utils;

import java.awt.Font;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class SlickLabel {
	
	public static final int LEFT_ALIGNED=0;
	public static final int RIGHT_ALIGNED=1;
	public static final int CENTER_ALIGNED=2;
	
	private int xPos;
	private int yPos;
	//private int height;
	private int width;
	public String text;
	private int allignment;
	private int size;
	private UnicodeFont font;
	
	public boolean shown = true;
	
	@SuppressWarnings("unchecked")
	public SlickLabel(int x, int y, int h, int w, String t, int a, int s) throws SlickException{
		xPos=x;
		yPos=y;
		//height=h;
		width=w;
		text=t;
		allignment=a;
		size = s;
		
		Font tempFont = new Font("Monospaced",0,size);
		font = new UnicodeFont(tempFont);
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect());
		font.loadGlyphs();
	}
	
	public void draw(Graphics g){
		if(shown){
		
		g.setFont(font);
		
		if(allignment==0){
			g.drawString(text, xPos, yPos);
		} else if(allignment == 1){
			g.drawString(text, xPos + (width - g.getFont().getWidth(text)), yPos);
		} else if(allignment == 2){
			g.drawString(text, xPos - (g.getFont().getWidth(text)-width) / 2, yPos);
		}
		
		}
		
	}
	
}
