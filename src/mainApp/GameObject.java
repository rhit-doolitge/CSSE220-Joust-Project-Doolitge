package mainApp;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: GameObject
 * 
 * @author WS23-A-105 <br>
 *         Purpose: creates a object for a game
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         GameObject a = new GameObject(x,y);
 *         </pre>
 */
public abstract class GameObject {

	//constants
	private static final int BASE_WIDTH = 5;
	private static final int BASE_HEIGHT = 1;
	protected static final int LAVA_HEIGHT = 730;

	//instance variables
	protected int width;
	protected int height;
	protected int xPosition;
	protected int yPosition;

	protected Rectangle2D.Double hitbox;

	/**
	 * ensures: Creation of a game object
	 *
	 * <br>
	 * requires: a game
	 */
	public GameObject() {
		this.xPosition = 0;
		this.yPosition = 0;
	}

	/**
	 * ensures: Creation of a game object
	 @param xPosition the object x position to start at
	 @param yPosition the object y position to start at
	 *@param width width of the object
	 *@param height height of the object
	 * <br>
	 * requires: a game
	 */
	public GameObject(int xPosition, int yPosition, int width, int height) {
		this.width = width;
		this.height = height;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.hitbox = new Rectangle2D.Double(xPosition, yPosition, width, height);
	}

	/**
	 * ensures: Creation of a game object
	 @param xPosition the object x position to start at
	 @param yPosition the object y position to start at
	 * <br>
	 * requires: a game
	 */
	public GameObject(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = BASE_WIDTH;
		this.height = BASE_HEIGHT;
		this.hitbox = new Rectangle2D.Double(xPosition, yPosition, width, height);
	}
	/**
	 * ensures: draws the object on the screen
	 *
	 * <br>
	 * requires: a frame to draw the object on
	 * @param g the graphics that the object is being drawn on
	 */
	public abstract void draw(Graphics2D g);
}
