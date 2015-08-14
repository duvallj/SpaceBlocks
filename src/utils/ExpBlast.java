package utils;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class ExpBlast extends ShipBlast{

	public ExpBlast(World world, float angle, Vec2 startPos, jbox2slick trans,
			int index) {
		super(world, angle, startPos, trans, index);
		// TODO Auto-generated constructor stub
	}
	public void drawSelf(Graphics g){
		int[] pos = tr.toSlick(blast.getPosition());
		g.setColor(Color.pink);
		Circle circle = new Circle(pos[0], pos[1], tr.xscale/2);
		g.fill(circle);
	}
	public boolean isInRadius(Vec2 pos){
		float xdif = blast.getPosition().x-pos.x;
		float ydif = blast.getPosition().y-pos.y;
		return Math.sqrt(xdif*xdif+ydif*ydif)<10.5;
	}
	public void explode(ShipBuild pl){
		for(double i=0; i<2*Math.PI; i+=0.1f){
			Vec2 spawn = new Vec2(-3*(float)Math.sin(i),3*(float)Math.cos(i));
			spawn.x += blast.getPosition().x;
			spawn.y += blast.getPosition().y;
			pl.blasts.add(new ShipBlast(pl.player.getWorld(), (float) i, spawn, tr, pl.blasts.size()));
			pl.blastLasers.add(pl.blasts.get(pl.blasts.size()-1).b);
		}
	}

}
