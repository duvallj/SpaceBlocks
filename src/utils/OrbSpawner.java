package utils;

import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;

public class OrbSpawner {
	
	public ArrayList<Body> asteroids;
	private jbox2slick tr;
	private World world;
	public Body orb;
	
	public OrbSpawner(World w, jbox2slick j, ArrayList<Body> a){
		world = w;
		tr = j;
		asteroids = a;
	}
	
	public void genOrb(float xLimit, float yLimit){
		
		Random r = new Random();
		
		CircleShape randomCircle = new CircleShape();
		randomCircle.m_radius = 1f;
		boolean inWay = true;
		float x=0;
		float y=0;
		while(inWay){
			inWay=false;
			
			x = Math.abs(r.nextFloat()) * (2 * xLimit) - xLimit;
			y = Math.abs(r.nextFloat()) * (2 * yLimit) - yLimit;
			
			for(Body ast: asteroids){
				float dist = (float) Math.sqrt(
						Math.pow(ast.getPosition().x-x, 2)+
						Math.pow(ast.getPosition().y-y, 2));
				float radius = ast.getFixtureList().m_shape.m_radius;
				
				if(dist <= radius + 2){
					inWay = true;
				}
			}
		}
		
		FixtureDef orbCircle = new FixtureDef();
		orbCircle.shape = randomCircle;
		
		orb = world.createBody(new BodyDef());
		orb.createFixture(orbCircle);
		orb.setTransform(new Vec2(x,y), 0);
		
	}
	
	public void drawOrb(Graphics g){
		
		g.setColor(Color.yellow);
		
		int[] slickPos = tr.toSlick(orb.getPosition());
		
		Ellipse e = new Ellipse(slickPos[0], slickPos[1], tr.xscale, tr.yscale);
		
		g.fill(e);
		
	}
	
	public boolean deleteOrb( Fixture fix ){
		Body testBody = fix.getBody();
		
		if(orb.equals(testBody)){
			world.destroyBody(orb);
			return true;
		} else{
			return false;
		}
		
		// You should probably call this next thing to avoid an error
		//this.genOrb(xl, yl);
	}
	
}
