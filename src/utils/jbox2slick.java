package utils;

import org.jbox2d.common.Vec2;

public class jbox2slick {
	
	public int width;
	public int height;
	public float xscale = 1f;
	public float yscale = 1f;
	
	public jbox2slick(int x, int y){
		width = x;
		height = y;
	}
	
	public jbox2slick(int x, int y, float s){
		width = x;
		height = y;
		xscale = s;
		yscale = s;
	}
	
	public jbox2slick(int slickx, int slicky, float jboxx, float jboxy){
		width = slickx;
		height = slicky;
		xscale = slickx/jboxx;
		yscale = slicky/jboxy;
	}
	
	public int[] toSlick(float x, float y){
		int [] output = new int[2];
		output[0] = (int)(x*xscale+width/2);
		output[1] = (int)(-1*y*yscale+height/2);
		return output;
	}
	
	public int[] toSlick(Vec2 pos){
		float x = pos.x;
		float y = pos.y;
		return toSlick(x,y);
	}
	
	public Vec2 toJBox(int x, int y){
		return new Vec2((width+2*x)/(2*xscale), (height+2*y)/(2*yscale));
	}
	
	public int[] wToSlick(float w, float h){
		int [] output = new int[2];
		
		output[0] = (int) (w*xscale);
		output[1] = (int) (h*yscale);
		
		return output;
	}
	
}
