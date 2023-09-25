package mainApp;

import java.awt.Graphics2D;

/**
 * Abstract Class: MovingObject
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a moving object for a game that needs to be drawn
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         MovingObject a = new Enemy(x,y);
 *         </pre>
 */
public abstract class MovingObject extends GameObject {

	//constants
	protected static final double ACCEL = .1;
	protected static final double MAX_SPEED = 9;
	protected static final int SCREEN_RIGHT_EDGE = 1200;
	protected static final int SCREEN_LEFT_EDGE = 0;
	protected static final int SCREEN_BOTTOM_EDGE = 760;

	//instance variables
	protected boolean dead;
	protected double changeInX;
	protected double changeInY;

	/**
	 * ensures: Creation of a game object that moves
	 @param xPosition the object x position to start at
	 @param yPosition the object y position to start at
	 *@param width width of the object
	 *@param height height of the object
	 * <br>
	 * requires: a game
	 */
	public MovingObject(int xPosition, int yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
		this.changeInX = 0;
		this.changeInY = 0;
		this.dead = false;
	}

	/**
	 * ensures: Creation of a game object that moves
	 * <br>
	 * requires: a game
	 */
	public MovingObject() {
		super();
		this.changeInX = 0;
		this.changeInY = 0;
		this.dead = false;
	}

	/**
	 * ensures: Creation of a game object
	 @param xPosition the object x position to start at
	 @param yPosition the object y position to start at
	 *@param width width of the object
	 *@param height height of the object
	 *@param changeInX the initial x velocity
	 *@param changeInY the initial y velocity
	 * <br>
	 * requires: a game
	 */
	public MovingObject(int xPosition, int yPosition, int width, int height, int changeInX, int changeInY) {
		super(xPosition, yPosition, width, height);
		this.changeInX = changeInX;
		this.changeInY = changeInY;
		this.dead = false;
	}

	/**
	 * ensures: Creation of a game object
	 @param xPosition the object x position to start at
	 @param yPosition the object y position to start at
	 * <br>
	 * requires: a game
	 */
	public MovingObject(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	}

	/**
	 * ensures: draws the object on the screen
	 *
	 * <br>
	 * requires: a frame to draw the object on
	 * @param g the graphics that the object is being drawn on
	 */
	public abstract void draw(Graphics2D g);

	/**
	 * ensures: changes the x and y position of the object based on its x and y velocities
	 *
	 * <br>
	 * requires: an object
	 */
	public void move() {
		xPosition += changeInX;
		if (xPosition <= SCREEN_LEFT_EDGE - this.width) {
			xPosition = SCREEN_RIGHT_EDGE;
		} else if (xPosition >= SCREEN_RIGHT_EDGE + this.width) {
			xPosition = SCREEN_LEFT_EDGE;
		}
		yPosition += changeInY;
		if (yPosition >= SCREEN_BOTTOM_EDGE - this.height) {
			changeInY = 0;
			yPosition = SCREEN_BOTTOM_EDGE - this.height;
		} else if (yPosition < 0) {
			yPosition = 0;
			changeInY = 0;
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
	public void collidesWithPlatformXDirection(Platform plat) {
		if (plat.left.intersects(this.hitbox)) {
			this.changeInX = -this.changeInX;
			this.xPosition = plat.xPosition - this.width - 1;
			this.hitbox.x = xPosition;

		} else if (plat.right.intersects(this.hitbox)) {
			this.changeInX = -this.changeInX;
			this.xPosition = plat.xPosition + plat.width + 1;
			this.hitbox.x = xPosition;
		}
	}

	/**
	 * ensures: checks for a collision with a platform in the y direction and handles it appropriately
	 *
	 * <br>
	 * requires: a platform
	 * @param plat the plat that the hero could potentially collide with
	 */
	public void collidesWithPlatformYDirection(Platform plat) {
		if (plat.top.intersects(this.hitbox) && this.changeInY > 0) {
			this.changeInY = 0;
			this.yPosition = plat.yPosition - (int) this.hitbox.height;
			this.hitbox.y = yPosition;

		} else if (plat.bottom.intersects(this.hitbox) && this.changeInY < 0) {
			this.changeInY = 0;
			this.yPosition = plat.yPosition + plat.height;
			this.hitbox.y = yPosition;
		}
	}
}
