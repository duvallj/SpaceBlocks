package utils;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Polygon;

import java.util.ArrayList;
import java.util.Calendar;

public class ShipBuild {
	
	protected FixtureDef fixture;
	private World world;
	public Body player;
	public Vec2 position;
	public Vec2 centerBlock = null;
	public float angle;
	protected float mass;
	public float xLimit;
	public float yLimit;
	public float batteryLeft=0;
	protected boolean addT = true;
	
	public int inputForward = Input.KEY_UP;//SEMICOLON;
	public int inputLeft = Input.KEY_LEFT;//L;
	public int inputRight = Input.KEY_RIGHT;//APOSTROPHE;
	public int inputShoot = Input.KEY_ENTER;//COMMA;
	public int inputBackward = Input.KEY_DOWN;
	
	public ArrayList<int[]> turnBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> thrustBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> batteryBlocks = new ArrayList<int[]>();
	
	public ArrayList<int[]> massBlocks = new ArrayList<int[]>();
	public ArrayList<Integer> shieldLeft = new ArrayList<Integer>();
	
	public ArrayList<int[]> shootUpBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> shootLeftBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> shootRightBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> shootDownBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> allShootBlocks = new ArrayList<int[]>();
	
	public ArrayList<int[]> homShootUpBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> homShootLeftBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> homShootRightBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> homShootDownBlocks = new ArrayList<int[]>();
	public ArrayList<int[]> allHomShootBlocks = new ArrayList<int[]>();
	
	public ArrayList<Fixture> shipBlocks = new ArrayList<Fixture>();
	public ArrayList<ShipBlast> blasts = new ArrayList<ShipBlast>();
	public ArrayList<Fixture> blastLasers= new ArrayList<Fixture>();
	public ArrayList<HomBlast> homBlasts = new ArrayList<HomBlast>();
	public ArrayList<Fixture> homBlastLasers= new ArrayList<Fixture>();
	
	protected jbox2slick tr;
	protected Sound shoot;
	public boolean isLongHeld=false;
	public boolean dead = false;
	
	protected Calendar c = Calendar.getInstance();
	protected float lastTime = c.get(Calendar.MILLISECOND);
	protected float time = 0;
	protected float refreshTime=0;		//need for subclass
	
	public ShipBuild(int[][] blocks, World w, jbox2slick converter) throws SlickException{
		
		shoot = new Sound("Laser_Shoot.ogg");
		
		world=w;
		
		BodyDef bodyDef;
		bodyDef= new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(new Vec2(0,0));
		bodyDef.bullet = true;
		
		player = world.createBody(bodyDef);
		position = player.getPosition();
		player.setLinearDamping(0.2f);
		player.setAngularDamping(0.2f);
		
		Vec2[] verticies = new Vec2[4];
		verticies[0] = new Vec2(0.5f, 0.5f);
		verticies[1] = new Vec2(-0.5f, 0.5f);
		verticies[2] = new Vec2(-0.5f, -0.5f);
		verticies[3] = new Vec2(0.5f, -0.5f);
		
		fixture = new FixtureDef();
		
		fixture.density = 1.0f;
		fixture.friction = 0.5f;
		fixture.restitution = 0.2f;
		
		for( int a=0; a<blocks.length; a++){
			for(int b=0; b<blocks[a].length; b++){
				if(blocks[a][b] == 4){
					centerBlock = new Vec2(b,a);
					
					PolygonShape boxDef = new PolygonShape();
					boxDef.set(verticies, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					int[] coords = {0,0};
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);
					batteryLeft += 100;
				}
			}
		}
		
		for( int a=0; a<blocks.length; a++){
			for(int b=0; b<blocks[a].length; b++){
				
				int type = blocks[a][b];
				
				Vec2[] thisVert = new Vec2[4];
				int[] coords = new int[2];
				
				if (type != 0){
					float transX = centerBlock.x-b;
					float transY = centerBlock.y-a;
					for(int q=0; q<4; q++){
						thisVert[q] = new Vec2(transX+verticies[q].x,transY+verticies[q].y);
					}
					
					/*PolygonShape boxDef = new PolygonShape();
					boxDef.set(thisVert, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);*/
					
					int[] temp = {(int)centerBlock.x-b,(int)centerBlock.y-a};
					coords = temp;
				}
				
				if (type == 1){
					turnBlocks.add(coords);
					
					PolygonShape boxDef = new PolygonShape();
					boxDef.set(thisVert, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);
					
				}
				
				if (type == 2){
					thrustBlocks.add(coords);
					
					PolygonShape boxDef = new PolygonShape();
					boxDef.set(thisVert, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);
					
				}
				
				if (type == 3){
					massBlocks.add(coords);
					shieldLeft.add(3);
					
					PolygonShape boxDef = new PolygonShape();
					boxDef.set(thisVert, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);
					
				}
				
				if (type == 5){
					
					if(a!=0){
						if(blocks[a-1][b]==0){
							shootUpBlocks.add(coords);
						}
					} else{shootUpBlocks.add(coords);}
					
					if(a!=9){
						if(blocks[a+1][b]==0){
							shootDownBlocks.add(coords);
						}
					} else{shootDownBlocks.add(coords);}
					
					if(b!=0){
						if(blocks[a][b-1]==0){
							shootRightBlocks.add(coords);
						}
					} else{shootRightBlocks.add(coords);}
					
					if(b!=9){
						if(blocks[a][b+1]==0){
							shootLeftBlocks.add(coords);
						}
					} else{shootLeftBlocks.add(coords);}
					
					allShootBlocks.add(coords);
					
					
					PolygonShape boxDef = new PolygonShape();
					boxDef.set(thisVert, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);
				}
				
				if(type==6){
					
					if(a!=0){
						if(blocks[a-1][b]==0){
							homShootUpBlocks.add(coords);
						}
					} else{homShootUpBlocks.add(coords);}
					
					if(a!=9){
						if(blocks[a+1][b]==0){
							homShootDownBlocks.add(coords);
						}
					} else{homShootDownBlocks.add(coords);}
					
					if(b!=0){
						if(blocks[a][b-1]==0){
							homShootRightBlocks.add(coords);
						}
					} else{homShootRightBlocks.add(coords);}
					
					if(b!=9){
						if(blocks[a][b+1]==0){
							homShootLeftBlocks.add(coords);
						}
					} else{homShootLeftBlocks.add(coords);}
					
					allHomShootBlocks.add(coords);
					
					
					PolygonShape boxDef = new PolygonShape();
					boxDef.set(thisVert, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);
				}
				
				if(type==7){
					batteryBlocks.add(coords);
					batteryLeft += 100;
					
					PolygonShape boxDef = new PolygonShape();
					boxDef.set(thisVert, 4);
					
					FixtureDef box = fixture;
					box.shape = boxDef;
					shipBlocks.add(player.createFixture(box));
					shipBlocks.get(shipBlocks.size()-1).setUserData(coords);
					
				}
			}
			
		}
		tr = converter;
		xLimit = tr.width/tr.xscale/2;
		yLimit = tr.height/tr.yscale/2;
		
	}
	
	public void calcForce(Input input, int delta, Vec2 enemyPos){
		
		angle = player.getAngle();
		mass = player.getMass();
		position = player.getPosition();
		
		if(position.x > xLimit){
			position.x = xLimit;
		}
		if(position.x < -1 * xLimit){
			position.x = -xLimit;
		}
		if(position.y > yLimit){
			position.y = yLimit;
		}
		if(position.y < -1 * yLimit){
			position.y = -yLimit;
		}
		
		if(batteryLeft>0){
		
			if(input.isKeyDown(inputLeft)){
				angle+=0.03f*turnBlocks.size()/mass;
				batteryLeft -= 0.0004f * turnBlocks.size();
			} else if(input.isKeyDown(inputRight)){
				angle-=0.03f*turnBlocks.size()/mass;
				batteryLeft -= 0.0004f * turnBlocks.size();
			}
			
			player.setTransform(position, angle);
			
			if(input.isKeyDown(inputForward)){
				float xv = (float)(0.25f * Math.sin(angle) * thrustBlocks.size() / mass);
				float yv = (float)(0.25f * Math.cos(angle) * thrustBlocks.size() / mass);
				Vec2 curVel = player.getLinearVelocity();
				xv = -1 * xv + curVel.x;
				yv = yv + curVel.y;
				player.setLinearVelocity(new Vec2(xv, yv));
				
				batteryLeft -= 0.0004f * thrustBlocks.size();
			}
			
			if(input.isKeyDown(inputBackward)){
				float xv = (float)(-0.25f * Math.sin(angle) * thrustBlocks.size() / mass);
				float yv = (float)(-0.25f * Math.cos(angle) * thrustBlocks.size() / mass);
				Vec2 curVel = player.getLinearVelocity();
				xv = -1 * xv + curVel.x;
				yv = yv + curVel.y;
				player.setLinearVelocity(new Vec2(xv, yv));
				
				batteryLeft -= 0.0004f * thrustBlocks.size();
			}
			
			if(input.isKeyPressed(inputShoot)){
				
				shootReg();
				
			}
			
			if(isLongHeld){
				shootHom();
				time=0;
				isLongHeld = false;
			}
			
			addTime(delta);
			
			if(input.isKeyDown(inputShoot)){
				addT = true;
				if(time>1000){
					isLongHeld=true;
					time=1000;
				}
			} else{
				time=0;
				isLongHeld=false;
			}
			batteryLeft += 0.004f;
		}
		
		for( int s=0; s<blasts.size(); s++){
			Vec2 blastPos = blasts.get(s).blast.getPosition();
			boolean outOfBounds = blastPos.x > xLimit || 
					blastPos.x < -1 * xLimit || 
					blastPos.y > yLimit ||
					blastPos.y < -1 * yLimit;
			
			if(outOfBounds){
				deleteBlast(s);
			}
		}
		
		for( int s=0; s<homBlasts.size(); s++){
			Vec2 blastPos = homBlasts.get(s).blast.getPosition();
			
			boolean outOfBounds = blastPos.x > xLimit || 
					blastPos.x < -1 * xLimit || 
					blastPos.y > yLimit ||
					blastPos.y < -1 * yLimit;
					
			homBlasts.get(s).update(enemyPos, delta);
			
			if(outOfBounds){
				deleteHomBlast(s);
			}
		}
		
		this.checkDeath();
		
	}
	
	private void shootReg(){

		if(allShootBlocks.size()>0){
			shoot.play();
			batteryLeft -= 0.04f * allShootBlocks.size();
		}
		
		for(int s=0; s<shootUpBlocks.size(); s++){
		
			Vec2 spawn = player.getWorldPoint(
					new Vec2(shootUpBlocks.get(s)[0], 
							shootUpBlocks.get(s)[1]+1));
			
			blasts.add(new ShipBlast(player.getWorld(), angle, spawn, tr, blasts.size()));
			blastLasers.add(blasts.get(blasts.size()-1).b);
			
		}
		
		for(int s=0; s<shootRightBlocks.size(); s++){
			
			Vec2 spawn = player.getWorldPoint(
					new Vec2(shootRightBlocks.get(s)[0]+1, shootRightBlocks.get(s)[1]));
			
			blasts.add(new ShipBlast(player.getWorld(), angle-(float)Math.PI/2, spawn, tr, blasts.size()));
			blastLasers.add(blasts.get(blasts.size()-1).b);
			
		}
		
		for(int s=0; s<shootDownBlocks.size(); s++){
			
			Vec2 spawn = player.getWorldPoint(
					new Vec2(shootDownBlocks.get(s)[0], shootDownBlocks.get(s)[1]-1));
			
			blasts.add(new ShipBlast(player.getWorld(), angle+(float)Math.PI, spawn, tr, blasts.size()));
			blastLasers.add(blasts.get(blasts.size()-1).b);
			
		}
		
		for(int s=0; s<shootLeftBlocks.size(); s++){
			
			Vec2 spawn = player.getWorldPoint(
					new Vec2(shootLeftBlocks.get(s)[0]-1, shootLeftBlocks.get(s)[1]));
			
			blasts.add(new ShipBlast(player.getWorld(), angle+(float)Math.PI/2, spawn, tr, blasts.size()));
			blastLasers.add(blasts.get(blasts.size()-1).b);
			
		}
	}
	
	private void shootHom(){

		if(allHomShootBlocks.size()>0){
			shoot.play();
			batteryLeft -= 0.04f * allHomShootBlocks.size();
		}
		
		for(int s=0; s<homShootUpBlocks.size(); s++){
		
			Vec2 spawn = player.getWorldPoint(
					new Vec2(homShootUpBlocks.get(s)[0], 
							homShootUpBlocks.get(s)[1]+1));
			
			homBlasts.add(new HomBlast(player.getWorld(), angle, spawn, tr, homBlasts.size()));
			homBlastLasers.add(homBlasts.get(homBlasts.size()-1).b);
			
		}
		
		for(int s=0; s<homShootRightBlocks.size(); s++){
			
			Vec2 spawn = player.getWorldPoint(
					new Vec2(homShootRightBlocks.get(s)[0]+1, homShootRightBlocks.get(s)[1]));
			
			homBlasts.add(new HomBlast(player.getWorld(), angle-(float)Math.PI/2, spawn, tr, homBlasts.size()));
			homBlastLasers.add(homBlasts.get(homBlasts.size()-1).b);
			
		}
		
		for(int s=0; s<homShootDownBlocks.size(); s++){
			
			Vec2 spawn = player.getWorldPoint(
					new Vec2(homShootDownBlocks.get(s)[0], homShootDownBlocks.get(s)[1]-1));
			
			homBlasts.add(new HomBlast(player.getWorld(), angle+(float)Math.PI, spawn, tr, homBlasts.size()));
			homBlastLasers.add(homBlasts.get(homBlasts.size()-1).b);
			
		}
		
		for(int s=0; s<homShootLeftBlocks.size(); s++){
			
			Vec2 spawn = player.getWorldPoint(
					new Vec2(homShootLeftBlocks.get(s)[0]-1, homShootLeftBlocks.get(s)[1]));
			
			homBlasts.add(new HomBlast(player.getWorld(), angle+(float)Math.PI/2, spawn, tr, homBlasts.size()));
			homBlastLasers.add(homBlasts.get(homBlasts.size()-1).b);
			
		}
	}
	
	protected void addTime(int t){
		if(addT){
			time += t;
			refreshTime += t;
		}
	}
	
	public void drawShip(Graphics g){
		g.setColor(Color.cyan);
		
		for(int turn = 0; turn < turnBlocks.size(); turn++){
			drawBox(turnBlocks.get(turn), g);
		}
		
		g.setColor(Color.red);
		
		for(int thrust = 0; thrust < thrustBlocks.size(); thrust++){
			drawBox(thrustBlocks.get(thrust), g);
		}
		
		g.setColor(Color.gray);
		
		for(int mass = 0; mass < massBlocks.size(); mass++){
			drawBox(massBlocks.get(mass), g);
		}
		
		g.setColor(Color.green);
		
		for(int shoot = 0; shoot < allShootBlocks.size(); shoot++){
			drawBox(allShootBlocks.get(shoot), g);
		}
		
		g.setColor(Color.orange);
		
		for(int shoot = 0; shoot < allHomShootBlocks.size(); shoot++){
			drawBox(allHomShootBlocks.get(shoot), g);
		}
		
		g.setColor(Color.yellow);
		
		for(int shoot = 0; shoot < batteryBlocks.size(); shoot++){
			drawBox(batteryBlocks.get(shoot), g);
		}
		
		//This is just a variant on drawBox
		
		float bottomLeftX = position.x - 5;
		float bottomLeftY = position.y + 6;
		//I go directly to world b/c i want the energy
		//bar always on top
		float[] worldJBox = {bottomLeftX, bottomLeftY,
				bottomLeftX+batteryLeft/(10*(batteryBlocks.size()+1)), bottomLeftY,
				bottomLeftX+batteryLeft/(10*(batteryBlocks.size()+1)), bottomLeftY+1,
				bottomLeftX, bottomLeftY+1};
		
		float[] slickCoords = new float[worldJBox.length];
		
		for(int p=0; p<4; p++){
			Vec2 jPoint = new Vec2(worldJBox[p*2], worldJBox[p*2+1]);
			int[] slickPoint = tr.toSlick(jPoint);
			
			slickCoords[p*2] = (float)slickPoint[0];
			slickCoords[p*2+1] = (float)slickPoint[1];
		}
		
		Polygon box = new Polygon(slickCoords);
		g.fill(box);
		
		bottomLeftY++;
		
		float[] worldJBox2 = {bottomLeftX, bottomLeftY,
				bottomLeftX+time/100, bottomLeftY,
				bottomLeftX+time/100, bottomLeftY+1,
				bottomLeftX, bottomLeftY+1};
		
		for(int p=0; p<4; p++){
			Vec2 jPoint = new Vec2(worldJBox2[p*2], worldJBox2[p*2+1]);
			int[] slickPoint = tr.toSlick(jPoint);
			
			slickCoords[p*2] = (float)slickPoint[0];
			slickCoords[p*2+1] = (float)slickPoint[1];
		}
		
		g.setColor(Color.orange);
		g.fill(new Polygon(slickCoords));
		
		g.setColor(Color.black);
		
		drawBox(new int[2], g);
		
		for(ShipBlast b: blasts){
			b.drawSelf(g);
		}
		for(HomBlast h: homBlasts){
			h.drawSelf(g);
		}
		
	}
	
	public void checkDeath(){
		
		/*if( thrustBlocks.size() == 0 && turnBlocks.size() == 0){
			dead = true;
		}
		
		if( batteryLeft < 0){
			dead = true;
		} else */if(batteryLeft > batteryBlocks.size()*100+100){
			//so that the bar isn't super long
			//when one of your batteries
			//get destroyed
			batteryLeft = batteryBlocks.size()*100+100;
		}
		
	}
	
	public void deleteBlast(int index){
		world.destroyBody(blasts.get(index).blast);
		blasts.remove(index);
		blastLasers.remove(index);
	}
	
	public void deleteHomBlast(int index){
		world.destroyBody(homBlasts.get(index).blast);
		homBlasts.remove(index);
		homBlastLasers.remove(index);
	}
	
	public void calcForceCPU(Vec2 v, ArrayList<Body> b, ArrayList<Vec2> a, int d){
		//nothing
	}
	
	public void deleteFixture( Fixture fix ){
		
		int[] pos = (int[]) fix.getUserData();
		
		if(shipBlocks.contains(fix)){
			
			if(massBlocks.contains(pos)){
				int index = massBlocks.indexOf(pos);
				int shield = shieldLeft.get(index);
				shieldLeft.set(index, (shield - 1));
				shield -= 1;
				if(shield < 0){
					player.destroyFixture(fix);
					massBlocks.remove(pos);
					shipBlocks.remove(fix);
					shieldLeft.remove(index);
				}
			} else {
				player.destroyFixture(fix);
				
				if(pos[0] == 0 && pos[1] == 0){
					dead = true;
				}
				shipBlocks.remove(fix);
				
				turnBlocks.remove(pos);
				thrustBlocks.remove(pos);
				batteryBlocks.remove(pos);
					
				allShootBlocks.remove(pos);
				shootUpBlocks.remove(pos);
				shootDownBlocks.remove(pos);
				shootLeftBlocks.remove(pos);
				shootRightBlocks.remove(pos);
				
				allHomShootBlocks.remove(pos);
				homShootUpBlocks.remove(pos);
				homShootDownBlocks.remove(pos);
				homShootLeftBlocks.remove(pos);
				homShootRightBlocks.remove(pos);
			}
			
		}
		
		if(blastLasers.contains(fix)){
			deleteBlast(blastLasers.indexOf(fix));
		}
		
		if(homBlastLasers.contains(fix)){
			deleteHomBlast(homBlastLasers.indexOf(fix));
		}
		
	}
	
	private void drawBox(int[] coords, Graphics g){
		
		float[] localEndCoords = {coords[0]+0.5f, coords[1]+0.5f,
				coords[0]-0.5f, coords[1]+0.5f,
				coords[0]-0.5f, coords[1]-0.5f,
				coords[0]+0.5f, coords[1]-0.5f};
	
		float[] slickCoords = new float[localEndCoords.length];
		
		for(byte point = 0; point<localEndCoords.length/2; point++){
			Vec2 localPoint = new Vec2(localEndCoords[point*2], localEndCoords[point*2+1]);
			
			slickCoords[point*2] = (float)tr.toSlick(player.getWorldPoint(localPoint).x, player.getWorldPoint(localPoint).y)[0];
			slickCoords[point*2+1] = (float)tr.toSlick(player.getWorldPoint(localPoint).x, player.getWorldPoint(localPoint).y)[1];
		}
		
		Polygon box = new Polygon(slickCoords);
		g.fill(box);
		
	}
	
	public boolean isBlast(Fixture fix){
		if(blastLasers.contains(fix) || homBlastLasers.contains(fix)){
			return true;
		} else{
			return false;
		}
	}

}
