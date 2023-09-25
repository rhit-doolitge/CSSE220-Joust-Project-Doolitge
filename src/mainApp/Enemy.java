package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.WildcardType;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Class: Enemy
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a enemy object that moves and can be drawn for a game
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         Enemy enemy = new Enemy(x,y);
 *         </pre>
 */
public class Enemy extends MovingObject {

	//constants
	private static final int WIDTH = 30;
	private static final int HEIGHT = 40;
	protected static final int EGG_WIDTH = 15;
	protected static final int EGG_HEIGHT = 20;
	protected static final int RESPAWN_WAIT = 240;
	protected static final int MAX_MOVE_SPEED = 5;
	protected static final int RESPAWN_OFFSET = 10;
	protected static final int CHANGE_MOVEMENT_WAIT = 120;
	private static final int START_VELOCITY = 5;
	
	//instance variables
	BufferedImage eggImage;
	BufferedImage enemyImage;

	int count;
	boolean egg;

	/**
	 * ensures: the creation of a new basic enemy
	 *
	 * <br>
	 * @param xPosition the enemies x position to start at
	 * @param yPosition the enemies y position to start at
	 */
	public Enemy(int xPosition, int yPosition) {
		super(xPosition, yPosition, WIDTH, HEIGHT, START_VELOCITY, START_VELOCITY);
		Random rand = new Random();
		count = rand.nextInt(120,160);
		try {
			enemyImage = ImageIO.read(new File("images/enemy.png"));
			eggImage = ImageIO.read(new File("images/egg.png"));
		} catch (IOException e) {
			System.err.println("Image Does Not Exist");
		}
	}

	/**
	 * ensures: draws the enemy on the screen
	 *
	 * <br>
	 * requires: a frame to draw the object on
	 * @param g the graphics that the enemy is being drawn on
	 */
	@Override
	public void draw(Graphics2D g) {
		if (egg) {
			this.hitbox.width = EGG_WIDTH;
			this.hitbox.height = EGG_HEIGHT;
			this.height = EGG_HEIGHT;
			this.width = EGG_WIDTH;
			g.drawImage(eggImage, xPosition, yPosition, width, height, null);
		} else {
			this.hitbox.width = WIDTH;
			this.hitbox.height = HEIGHT;
			this.height = HEIGHT;
			this.width = WIDTH;
			if (changeInX < 0) {
				g.drawImage(enemyImage, xPosition, yPosition, width, height, null);
				}else {
					g.drawImage(enemyImage, xPosition+width, yPosition, -width, height, null);
				}
		}
		
		
	}

	/**
	 * ensures: the velocity of the enemy is changed based on a certain interval
	 *
	 * <br>
	 * requires: a enemy that has a certain x and y
	 */
	public void changeMovement() {
		Random rand = new Random();
		count++;
		if (egg) {
			this.changeInX = 0;
			this.changeInY = MAX_MOVE_SPEED;
			if (count >= RESPAWN_WAIT) {
				egg = false;
				this.yPosition -= RESPAWN_OFFSET;
				this.hitbox.y -= RESPAWN_OFFSET;
			}
		} else {
			if (count >= CHANGE_MOVEMENT_WAIT) {
				this.changeInX = rand.nextInt(-MAX_MOVE_SPEED, MAX_MOVE_SPEED);
				this.changeInY = rand.nextInt(-MAX_MOVE_SPEED, MAX_MOVE_SPEED);
				count = 0;
			}
		}
	}
	
	/**
	 * ensures: changes the x and y position of the enemy based on its x and y velocities
	 *
	 * <br>
	 * requires: an enemy
	 */
	@Override
	public void move() {
		super.move();
		if(this.yPosition > LAVA_HEIGHT - this.height) {
			if (this.egg) {
			this.dead = true;
			}else {
				this.changeInY = -this.changeInY;
			}
		}
	}

}
