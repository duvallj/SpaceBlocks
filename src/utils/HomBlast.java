package utils;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Transform;

public class HomBlast {
	
	public Body blast;
	public Fixture b;
	private jbox2slick tr;
	private int time;
	
	public HomBlast(World world, float angle, Vec2 startPos, jbox2slick trans, int index){
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(startPos);
		bodyDef.bullet = true;
		
		blast = world.createBody(bodyDef);
		double xv = -20*Math.sin(angle);
		double yv = 20*Math.cos(angle);
		Vec2 vel = new Vec2((float)xv, (float)yv);
		blast.setLinearVelocity(vel);
		blast.setTransform(bodyDef.position, angle);
		
		CircleShape circleDef = new CircleShape();
		circleDef.m_radius = 0.25f;
		
		b = blast.createFixture(circleDef, 0.1f);
		int[] usr = {11,0};
		b.setUserData(usr);
		tr = trans;
		
	}
	
	public void drawSelf(Graphics g){
		int[] pos = tr.toSlick(blast.getPosition());
		g.setColor(Color.orange);
		Circle circle = new Circle(pos[0], pos[1], tr.xscale/2);
		g.fill(circle.transform(new Transform()));
	}
	
	public void update(Vec2 enemy, int delta){
		time += delta;
		if(time>300){
			time=301;
			float desAngle = findAng(blast.getPosition(), enemy);
			
			blast.setTransform(blast.getPosition(), desAngle);
			double xv = -20*Math.sin(desAngle);
			double yv = 20*Math.cos(desAngle);
			Vec2 vel = new Vec2((float)xv, (float)yv);
			blast.setLinearVelocity(vel);
		}
		
	}
	
	private float findAng(Vec2 originPoint, Vec2 objectPoint){
		
		//This function works with
		//counterclockwise radians
		
		float deltaY = objectPoint.y - originPoint.y;
		float deltaX = objectPoint.x - originPoint.x;
		
		float angToObj = (float) Math.atan(deltaY / deltaX);
		
		if(deltaX < 0){
			angToObj += Math.PI / 2;
		} else{
			angToObj += 3 * Math.PI / 2;
		}
		
		return angToObj;
		
	}

}
