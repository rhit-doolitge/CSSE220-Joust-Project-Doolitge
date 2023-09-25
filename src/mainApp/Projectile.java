package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class: Projectile
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a projectile for a game
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         Projectile proj = new Projectile(x,y,z);
 *         </pre>
 */
public class Projectile extends MovingObject {

	protected static final int PROJECTILE_SPEED = 12;

	int direction;

	/**
	 * ensures: Creation of a game object
	 @param xPosition the object x position to start at
	 @param yPosition the object y position to start at
	 *@param direction the direction of travel of the projectile + is right - is right
	 * <br>
	 * requires: a game
	 */
	public Projectile(int xPosition, int yPosition, int direction) {
		super(xPosition, yPosition);
		this.changeInX = PROJECTILE_SPEED;
		this.direction = direction;
	}

	/**
	 * ensures: draws the object on the screen
	 *
	 * <br>
	 * requires: a frame to draw the object on
	 * @param g the graphics that the object is being drawn on
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.draw(hitbox);
	}
	
	/**
	 * ensures: changes the x and y position of the object based on its x and y velocities
	 *
	 * <br>
	 * requires: an object
	 */
	@Override
	public void move() {
		xPosition += changeInX * direction;
		if (xPosition <= -10 || xPosition >= 1210) {
			this.dead = true;
		}

		this.hitbox.x = xPosition;
		this.hitbox.y = yPosition;
	}

	/**
	 * ensures: checks for a collision with a platform in the x direction and handles it appropriately
	 *
	 * <br>
	 * requires: a platform
	 * @param plat the plat that the hero could potentially collide with
	 */
	@Override
	public void collidesWithPlatformXDirection(Platform plat) {

		if (plat.hitbox.intersects(this.hitbox)) {
			this.dead = true;
		}
	}
}
