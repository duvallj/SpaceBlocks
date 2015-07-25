package utils;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class CollideTest implements ContactListener{

	private Sound hit;
	public ShipBuild pl1;
	public ShipBuild pl2;
	
	public Fixture fa;
	public Fixture fb;
	
	public CollideTest(ShipBuild p1, ShipBuild p2, World w) throws SlickException{
		hit = new Sound("Hit.ogg");
		pl1=p1;
		pl2=p2;
	}

	@Override
	public void beginContact(Contact arg0) {
		hit.play();
		
		fa = arg0.m_fixtureA;
		fb = arg0.m_fixtureB;
		
		
	}

	@Override
	public void endContact(Contact arg0) {
		fa = null;
		fb = null;
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
