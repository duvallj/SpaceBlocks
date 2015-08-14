package utils;

import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;

public class AsteroidSpawner {
	
	public ArrayList<Body> asteroids = new ArrayList<Body>();
	public ArrayList<Integer> asteroidDamage = new ArrayList<Integer>();
	public ArrayList<Color> asteroidColors = new ArrayList<Color>();
	private jbox2slick tr;
	private World world;
	
	
	public AsteroidSpawner(World w, jbox2slick trans){
		
		world = w;
		tr = trans;
		
	}
	
	public void genAsteroids(float xLimit, float yLimit){
		
		asteroids.removeAll(asteroids);
		asteroidDamage.removeAll(asteroidDamage);
		
		Random r = new Random();
		int numOfAsteroids = (int) (Math.random() * 4 + 4);
		
		for(int asteroid=0; asteroid<numOfAsteroids; asteroid++){
			
			CircleShape randomCircle = new CircleShape();
			randomCircle.m_radius = (float) (Math.random() * 8 + 3);
			float x = Math.abs(r.nextFloat()) * (2 * xLimit) - xLimit;
			float y = Math.abs(r.nextFloat()) * (2 * yLimit) - yLimit;
			
			FixtureDef asteroidCircle = new FixtureDef();
			asteroidCircle.shape = randomCircle;
			asteroidCircle.density = 5f;
			
			BodyDef dBody = new BodyDef();
			dBody.type = BodyType.DYNAMIC;
			
			asteroids.add(world.createBody(dBody));
			int index = asteroids.size()-1;
			asteroids.get(index).createFixture(asteroidCircle);
			asteroids.get(index).setTransform(new Vec2(x,y), 0);
			asteroids.get(index).setLinearDamping(0.2f);
			asteroids.get(index).setAngularDamping(0.2f);
			
			asteroidDamage.add((int)randomCircle.m_radius * 20);
			
			asteroidColors.add(new Color(randint(),randint(),randint()));
			
		}
	}
	
	public void drawAsteroids(Graphics g){
		
		for(int a=0; a<asteroids.size(); a++){
			
			Body asteroid = asteroids.get(a);
			float radius = asteroid.getFixtureList().m_shape.m_radius;
			float xRad = tr.xscale * radius;
			float yRad = tr.yscale * radius;
			int[] slickPos = tr.toSlick(asteroid.getPosition());
			
			Ellipse e = new Ellipse(slickPos[0], slickPos[1], xRad, yRad);
			g.setColor(asteroidColors.get(a));
			g.fill(e);
			
		}
		
	}
	
	public void damageAsteroid(Fixture fix){
		
		if(asteroids.contains(fix.getBody())){
			int index = asteroids.indexOf(fix.getBody());
			asteroidDamage.set(index, asteroidDamage.get(index)-1);
			if(asteroidDamage.get(index)<0){
				world.destroyBody(asteroids.get(index));
				asteroids.remove(index);
				asteroidDamage.remove(index);
				asteroidColors.remove(index);
			}
		}
		
	}
	
	private int randint(){
		return (int)(Math.random()*255);
	}
	
}
