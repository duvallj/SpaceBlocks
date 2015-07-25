package org;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import utils.AsteroidSpawner;
import utils.CollideTest;
import utils.OrbSpawner;
import utils.ShipBuild;

public class ShipCollect extends ShipGame{
	
	public ShipCollect() {
		super("SPACEBLOCKS");
		// TODO Auto-generated constructor stub
	}
	private Input input;
	//private AppGameContainer app;
	
	public static int x;
	public static int y;
	
	private World world;
	private float timeStep = 1.0f/60.0f;
	private int velocityIterations = 8;
	private int positionIterations = 3;
	
	//public static jbox2slick tr;
	
	private ShipBuild player1;
	private ShipBuild player2;
	private AsteroidSpawner asteroids;
	private OrbSpawner orbs;
	
	private CollideTest cDetect;
	private Sound collect;
	
	//public int[][] player1Ship;
	//public int[][] player2Ship;
	
	//public int p1Orbs=0;
	//public int p2Orbs=0;
	
	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		arg1.setColor(Color.white);
		arg1.fillRect(0, 0, tr.width, tr.height);
		
		asteroids.drawAsteroids(arg1);
		
		if(!player1.dead){
			player1.drawShip(arg1);
		} else{
			over = true;
		}
		if(!player2.dead){
			player2.drawShip(arg1);
		} else{
			over = true;
		}
		
		
		orbs.drawOrb(arg1);
		
	}
	
	public void init(GameContainer container) throws SlickException {
		
		collect = new Sound("Pickup_Coin.ogg");
		
		if (container instanceof AppGameContainer) {
			app = (AppGameContainer) container;
		}
		
		over = false;
		
		input = container.getInput();
		
		Vec2 gravity = new Vec2(0.0f,0.0f);
		world = new World(gravity, true);
		
		player1 = new ShipBuild(player1Ship, world, tr);
		player2 = new ShipBuild(player2Ship, world, tr);
		
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
		
		orbs = new OrbSpawner(world, tr, asteroids.asteroids);
		orbs.genOrb(jX/2-15, max);
		
	}
	
	public void update(GameContainer arg0, int arg1) throws SlickException {
		world.step(timeStep, velocityIterations, positionIterations);
		
		Fixture fa = cDetect.fa;
		Fixture fb = cDetect.fb;
		
		if(fa != null && fb != null){
			
			if(orbs.deleteOrb(fb)){
				
				//I need to make a sound for this
				if(player1.shipBlocks.contains(fa)){
					p1Orbs++;
					player1.batteryLeft = (player1.batteryBlocks.size()+1) * 100;
					collect.play();
				} else if(player2.shipBlocks.contains(fa)){
					p2Orbs++;
					player2.batteryLeft = (player2.batteryBlocks.size()+1) * 100;
					collect.play();
				}
				orbs.genOrb(tr.width/tr.xscale/2-15, tr.height/tr.yscale/2-15);
			} else{
			
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
			
		}
		
		player1.calcForce(input, arg1, player2.player.getPosition());
		player2.calcForce(input, arg1, player1.player.getPosition());
		
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			over = true;
		}
		
	}


}
