package utils;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class StarField {
	private static List<Star> theStars;
	
	public static void randomize(int width, int height){
		theStars = new ArrayList<Star>();
		int len = randint(50,150);
		for(int i=0; i<len; i++){
			int clr = randint(100,255);
			int x = randint(0,width);
			int y = randint(0,height);
			theStars.add(new Star(x,y,new Color(clr,clr,clr)));
		}
	}
	
	public static void render(Graphics g){
		for(Star s: theStars){
			s.render(g);
		}
	}
	
	private static int randint(int min, int max){
		return (int)(Math.random() * (max-min) + min);
	}
}
