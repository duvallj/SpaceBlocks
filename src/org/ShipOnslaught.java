package org;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utils.AsteroidSpawner;
import utils.CollideTest;
import utils.ShipBuild;
import utils.ShipCPU;

public class ShipOnslaught extends ShipGame{

	private Input input;
	
	public static int x;
	public static int y;
	
	private World world;
	private float timeStep = 1.0f/60.0f;
	private int velocityIterations = 8;
	private int positionIterations = 3;
	
	//public static jbox2slick tr;
	
	private ShipBuild player1;
	private ShipCPU player2;
	private AsteroidSpawner asteroids;
	
	private CollideTest cDetect;
	//private Sound hit;
	
	//public int[][] player1Ship;
	//public int[][] player2Ship;
	
	//private boolean firstDeath=true;
	//public int winner=0;
	//public int level=0;
	
	public ShipOnslaught() {
		super("SPACEBLOCKS");
		// TODO Auto-generated constructor stub
	}

	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		arg1.setColor(Color.white);
		arg1.fillRect(0, 0, tr.width, tr.height);
		
		asteroids.drawAsteroids(arg1);
		
		if(!player1.dead){
			player1.drawShip(arg1);
		}
		if(!player2.dead){
			player2.drawShip(arg1);
		}
		
		
	}

	
	public void init(GameContainer container) throws SlickException {
		if (container instanceof AppGameContainer) {
			app = (AppGameContainer) container;
		}
		
		over = false;

		input = container.getInput();
		
		Vec2 gravity = new Vec2(0.0f,0.0f);
		world = new World(gravity, true);
		
		player1 = new ShipBuild(player1Ship, world, tr);
		player2 = new ShipCPU(player2Ship, world, tr, level); 	//thats the time in ms for shooting
		
		float jX = tr.width/tr.xscale;
		float jY = tr.height/tr.yscale;
		
		float max = jY/2-15;
		float min = -jY/2+15;
		
		asteroids = new AsteroidSpawner(world, tr);
		asteroids.genAsteroids(jX/2-40, max-25);
		
		float p1Y = (float) (Math.random()*(max-min) + min);
		float p2Y = (float) (Math.random()*(max-min) + min);
		player1.player.setTransform(new Vec2(-jX/2+15, p1Y), 3*(float)Math.PI/2);
		player2.player.setTransform(new Vec2(jX/2-15, p2Y), (float)Math.PI/2);
		
		player1.inputForward = Input.KEY_W;//E;
		player1.inputLeft = Input.KEY_A;//W;
		player1.inputRight = Input.KEY_D;//R;
		player1.inputShoot = Input.KEY_SPACE;//A;
		player1.inputBackward = Input.KEY_S;
		
		cDetect = new CollideTest(player1, player2, world);
		
		world.setContactListener(cDetect);
		
	}

	
	public void update(GameContainer arg0, int arg1) throws SlickException {
		world.step(timeStep, velocityIterations, positionIterations);
		
		Fixture fa = cDetect.fa;
		Fixture fb = cDetect.fb;
		
		if(fa != null && fb != null){
			
			if(!(player1.isBlast(fa) && player1.isBlast(fb)) &&
					!(player2.isBlast(fa) && player2.isBlast(fb))){
				
				player1.deleteFixture(fa);
				player1.deleteFixture(fb);
				
				player2.deleteFixture(fa);
				player2.deleteFixture(fb);
				
				asteroids.damageAsteroid(fa);
				asteroids.damageAsteroid(fb);
				
			}
		
		}
		
		player1.batteryLeft = 100f;
		
		player1.calcForce(input, arg1, player2.player.getPosition());
		player2.calcForceCPU(player1.player.getPosition(), asteroids.asteroids, new ArrayList<Vec2>(), arg1);
		
		player1.checkDeath();
		player2.checkDeath();
		
		if(player1.dead){ 
			over = true;
		}
		if(player2.dead){
			winner += 1;			//In this case, winner will be used to determine score
			world.destroyBody(player2.player);
			player2 = new ShipCPU(player2Ship, world, tr, level);
			float jX = tr.width/tr.xscale;
			float jY = tr.height/tr.yscale;
			
			float max = jY/2-15;
			float min = -jY/2+15;
			float p2Y = (float) (Math.random()*(max-min) + min);
			
			player2.player.setTransform(new Vec2(jX/2-15, p2Y), (float)Math.PI/2);
			
		}
		
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			over = true;
		}
	}

}
