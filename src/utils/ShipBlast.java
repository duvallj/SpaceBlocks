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

public class ShipBlast {
	
	public Body blast;
	public Fixture b;
	private jbox2slick tr;
	
	public ShipBlast(World world, float angle, Vec2 startPos, jbox2slick trans, int index){
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(startPos);
		bodyDef.bullet = true;
		
		blast = world.createBody(bodyDef);
		double xv = -20*Math.sin(angle);
		double yv = 20*Math.cos(angle);
		Vec2 vel = new Vec2((float)xv, (float)yv);
		blast.setLinearVelocity(vel);
		
		CircleShape circleDef = new CircleShape();
		circleDef.m_radius = 0.25f;
		
		b = blast.createFixture(circleDef, 0.1f);
		int[] usr = {11,0};
		b.setUserData(usr);
		tr = trans;
		
	}
	
	public void drawSelf(Graphics g){
		int[] pos = tr.toSlick(blast.getPosition());
		g.setColor(Color.green);
		Circle circle = new Circle(pos[0], pos[1], tr.xscale/2);
		g.fill(circle.transform(new Transform()));
	}
	
}
