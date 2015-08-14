package utils;

import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.SlickException;

public class ShipCPU extends ShipBuild{
	
	
	float level;
	float badAngle=0;
	Random r = new Random();
	Vec2 lastPosition = new Vec2(0,0);			//If it weren't for these,
	ArrayList<Vec2> lastAllyPosition = new ArrayList<Vec2>(); //It would be perfect
	boolean firstTime = true;
	

	public ShipCPU(int[][] blocks, World w, jbox2slick converter, float l)
			throws SlickException {
		super(blocks, w, converter);
		level = l;
		// TODO Auto-generated constructor stub
	}
	
	public void calcForceCPU(Vec2 newOtherPos, ArrayList<Body> asteroidField, ArrayList<Vec2> newOtherAllies, int delta){
		
		addTime(delta);
		time=0;
		
		angle = player.getAngle();
		position = player.getPosition();
		mass = player.getMass();
		
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
		
		if(firstTime){
			lastPosition = new Vec2(newOtherPos.x, newOtherPos.y);
			
			for(Vec2 alp: newOtherAllies){
				lastAllyPosition.add(new Vec2(alp.x, alp.y));
			}
		}
		firstTime= false;
		
		if(refreshTime>level){
			lastPosition = new Vec2(newOtherPos.x, newOtherPos.y);
			lastAllyPosition = new ArrayList<Vec2>();  //This was part of the reason it was lagging
			for(Vec2 alp: newOtherAllies){
				lastAllyPosition.add(new Vec2(alp.x, alp.y));
			}
		}
		
		Vec2 otherPos = lastPosition;
		ArrayList<Vec2> otherAllies = lastAllyPosition;
		
		float angToOther = findAng(position, otherPos);
		
		while(angle<0){
			angle += 2*Math.PI;
		}
		while(angle>2*Math.PI){
			angle -= 2*Math.PI;
		}
		
		if(angle > 3 * Math.PI / 2 && angToOther < Math.PI / 2){
			angToOther += 2 * Math.PI;
		}
		if(angle < Math.PI / 2 && angToOther > 3 * Math.PI / 2){
			angToOther -= 2 * Math.PI;
		}
		
		boolean asteroidInWay = false;
		float playerDist = (float) Math.sqrt(Math.pow(otherPos.x - position.x,2) +
				Math.pow(otherPos.y - position.y, 2));
		
		ArrayList<float[]> possiblePaths = new ArrayList<float[]>();
		ArrayList<Float> goodPaths = new ArrayList<Float>();
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		
		for(Body ast: asteroidField){
			
			float radius = ast.getFixtureList().m_shape.m_radius;
			
			float dist = (float) Math.sqrt(Math.pow(ast.getPosition().x - position.x,2) +
					Math.pow(ast.getPosition().y - position.y, 2));
			
			float angToAst = findAng(position, ast.getPosition());
			float angToEdge = (float) Math.asin(radius / dist);

			
			float side1 = angToAst+angToEdge+0.3f;
			float side2 = angToAst-angToEdge-0.3f;
			
			if(side1 > 3 * Math.PI / 2 && angToOther < Math.PI / 2){
				side1 -= 2 * Math.PI;
			}
			if(side1 < Math.PI / 2 && angToOther > 3 * Math.PI / 2){
				side1 += 2 * Math.PI;
			}
			
			if(side2 > 3 * Math.PI / 2 && angToOther < Math.PI / 2){
				side2 -= 2 * Math.PI;
			}
			if(side2 < Math.PI / 2 && angToOther > 3 * Math.PI / 2){
				side2 += 2 * Math.PI;
			}
			
			if(angToOther < side1 && angToOther > side2 && playerDist > dist){
				asteroidInWay = true;
			}
			
			float[] toAdd = {side1, side2};
			
			possiblePaths.add(toAdd);
			
		}
		
		for(Vec2 ally: otherAllies){
			
			float dist = (float) Math.sqrt(Math.pow(ally.x - position.x,2) +
					Math.pow(ally.y - position.y, 2));
			
			float angToAst = findAng(position, ally);
			float angToEdge = (float) Math.asin(10 / dist);

			
			float side1 = angToAst+angToEdge+0.3f;
			float side2 = angToAst-angToEdge-0.3f;
			
			if(side1 > 3 * Math.PI / 2 && angToOther < Math.PI / 2){
				side1 -= 2 * Math.PI;
			}
			if(side1 < Math.PI / 2 && angToOther > 3 * Math.PI / 2){
				side1 += 2 * Math.PI;
			}
			
			if(side2 > 3 * Math.PI / 2 && angToOther < Math.PI / 2){
				side2 -= 2 * Math.PI;
			}
			if(side2 < Math.PI / 2 && angToOther > 3 * Math.PI / 2){
				side2 += 2 * Math.PI;
			}
			
			if(angToOther < side1 && angToOther > side2 && playerDist > dist){
				asteroidInWay = true;
			}
			
			float[] toAdd = {side1, side2};
			
			possiblePaths.add(toAdd);
			
		}
		
		if(asteroidInWay){
			
			for(int pathGroup=0; pathGroup<possiblePaths.size(); pathGroup++){
				for(int path=0; path<2; path++){
					
					float pathAngle = possiblePaths.get(pathGroup)[path];
					
					for(int pathGroupToCheck=0; pathGroupToCheck<possiblePaths.size(); pathGroupToCheck++){
						
						if(pathAngle < possiblePaths.get(pathGroupToCheck)[0] && 
								pathAngle > possiblePaths.get(pathGroupToCheck)[1] &&
								!toRemove.contains(pathGroup*2 + path)){
							
							toRemove.add(pathGroup*2 + path);
						}
						
					}
				}
			}
			
			for(int pathGroup=0; pathGroup<possiblePaths.size(); pathGroup++){
				for(int path=0; path<2; path++){
					
					if(!toRemove.contains(pathGroup*2 + path)){
						goodPaths.add(possiblePaths.get(pathGroup)[path]);
					}
					
				}
			}
			
			float bestPath = 100f;
			
			for(int path=0; path<goodPaths.size(); path++){
				
				float pathAngle = goodPaths.get(path);
				if(Math.abs(angToOther-pathAngle) < Math.abs(angToOther-bestPath)){
					bestPath = pathAngle;
				}
				
			}
			
			angToOther = bestPath;
			
		}
		else {
			
			angToOther += badAngle;
			
		}
		
		
		if(angToOther > angle){
			angle+=0.03f*turnBlocks.size()/mass;
			batteryLeft -= 0.0004f * turnBlocks.size();
		}
		if(angToOther < angle){
			angle-=0.03f*turnBlocks.size()/mass;
			batteryLeft -= 0.0004f * turnBlocks.size();
		}
		
		player.setTransform(position, angle);
		
		if(Math.abs(angToOther-angle)<Math.PI/8){		//equivalent of going forward
			float xv = (float)(0.125f * Math.sin(angle) * thrustBlocks.size() / mass);
			float yv = (float)(0.125f * Math.cos(angle) * thrustBlocks.size() / mass);
			Vec2 curVel = player.getLinearVelocity();
			xv = -1 * xv + curVel.x;
			yv = yv + curVel.y;
			batteryLeft -= 0.0006f * thrustBlocks.size();
			player.setLinearVelocity(new Vec2(xv, yv));
		}
		
		
		if(refreshTime>level){ // && Math.abs(angToOther-angle)<Math.PI/8){			//equivalent of shooting
			batteryLeft -= 0.04f * allShootBlocks.size();
			if(allShootBlocks.size()>0){shoot.play();}
			refreshTime=0;
			
			for(int s=0; s<shootUpBlocks.size(); s++){
			
				Vec2 spawn = player.getWorldPoint(
						new Vec2(shootUpBlocks.get(s)[0], shootUpBlocks.get(s)[1]+1));
				
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
		
		checkDeath();
		
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
