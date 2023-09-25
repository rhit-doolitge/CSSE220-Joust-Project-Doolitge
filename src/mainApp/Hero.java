package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Hero
 * 
 * @author WS23-A-105 <br>
 *         Purpose: creates a hero for a game that can move and be drawn
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         Hero h = new Hero(x,y,z);
 *         </pre>
 */
public class Hero extends MovingObject {

	//constants
	private static final int WIDTH = 30;
	private static final int HEIGHT = 40;
	private static final int BOUNCE_OFFSET = 5;
	private static final int STARTING_LIVES = 3;
	private static final int EGG_SPAWN_OFFSET = 20;

	//instance variables
	private int lives;
	private boolean up;
	private boolean left;
	private boolean right;
	protected int startXPosition;
	protected int startYPosition;

	BufferedImage heroImage;

	/**
	 * ensures: creation of a hero
	 */
	public Hero() {
		super();
		this.left = false;
		this.right = false;
		this.up = false;
		this.lives = STARTING_LIVES;
		this.startXPosition = 0;
		this.startYPosition = 0;
		try {
			heroImage = ImageIO.read(new File("images/hero.png"));
		} catch (IOException e) {
			System.err.println("Image Does Not Exist");
		}
	}

	/**
	 * ensures: draws the enemy on the screen
	 *@param xPostion starting x position of the hero
	 *@param yPostion starting y position of the hero
	 *@param lives the starting lives of the hero
	 */
	public Hero(int xPosition, int yPosition, int lives) {
		super(xPosition, yPosition, WIDTH, HEIGHT);
		this.left = false;
		this.right = false;
		this.up = false;
		this.lives = lives;
		this.startXPosition = xPosition;
		this.startYPosition = yPosition;
		try {
			heroImage = ImageIO.read(new File("images/hero.png"));
		} catch (IOException e) {
			System.err.println("Image Does Not Exist");
		}
	}

	/**
	 * ensures: draws the hero on the screen
	 *
	 * <br>
	 * requires: a frame to draw the object on
	 * @param g the graphics that the hero is being drawn on
	 */
	public void draw(Graphics2D g) {
		if (this.changeInX > 0) {
			g.drawImage(heroImage, xPosition, yPosition, width, height, null);
		} else {
			g.drawImage(heroImage, xPosition + width, yPosition, -width, height, null);
		}
	}

	/**
	 * ensures: the hero x and y positions change based on velocity values
	 */
	@Override
	public void move() {
		super.move();
		if (this.yPosition > LAVA_HEIGHT - this.height) {
			this.dead();
		}
	}

	/**
	 * ensures: the hero is accelerating in the right direction
	 */
	public void accelerate() {
		if (this.left) {
			changeInX -= ACCEL;
			if (changeInX < -MAX_SPEED) {
				changeInX = -MAX_SPEED;
			}
		}
		if (this.right) {
			changeInX += ACCEL;
			if (changeInX > MAX_SPEED) {
				changeInX = MAX_SPEED;
			}
		}
		if (this.up) {
			changeInY -= ACCEL;
			if (changeInY < -MAX_SPEED) {
				changeInY = -MAX_SPEED;
			}
		} else {
			changeInY += ACCEL;
			if (changeInY > MAX_SPEED) {
				changeInY = MAX_SPEED;
			}
		}

	}

	/**
	 * ensures: checks for a collision with an enemy and handles it appropriately
	 *
	 * <br>
	 * requires: an enemy
	 * @param enemy the enemy that the hero could potentially collide with
	 */
	public void CollideWithEnemy(Enemy enemy) {
		if (this.hitbox.intersects(enemy.hitbox)) {
			if (enemy.egg) {
				enemy.dead = true;

			} else {
				if (this.yPosition < enemy.yPosition) {
					enemy.egg = true;
					enemy.count = 0;
					enemy.yPosition += EGG_SPAWN_OFFSET;
					enemy.hitbox.y += EGG_SPAWN_OFFSET;

				} else if (this.yPosition > enemy.yPosition) {
					this.dead();

				} else {
					if (!(this.changeInX > 0 && enemy.changeInX > 0) && !(this.changeInX < 0 && enemy.changeInX < 0)) {
						enemy.changeInX = -enemy.changeInX;
					}

					this.changeInX = -this.changeInX;

					if (this.xPosition > enemy.xPosition) {
						enemy.hitbox.x -= BOUNCE_OFFSET;
						enemy.xPosition -= BOUNCE_OFFSET;
						this.xPosition += BOUNCE_OFFSET;
						this.hitbox.x += BOUNCE_OFFSET;
					} else {
						enemy.hitbox.x += BOUNCE_OFFSET;
						this.hitbox.x -= BOUNCE_OFFSET;
						enemy.xPosition += BOUNCE_OFFSET;
						this.xPosition -= BOUNCE_OFFSET;
					}
				}
			}
		}
	}

	/**
	 * ensures: checks for a collision between the hero and a projectile
	 *
	 * <br>
	 * requires: projectile
	 * @param proj the projectile that could possibly hit the hero
	 */
	public void CollideWithProjectile(Projectile proj) {
		if (this.hitbox.intersects(proj.hitbox)) {
			this.dead();
			proj.dead = true;
		}
	}
	/**
	 * ensures: updates everything about the hero when it dies
	 *
	 * <br>
	 * requires: a starting position of the hero
	 */
	public void dead() {
		this.lives -= 1;
		this.xPosition = startXPosition;
		this.yPosition = startYPosition;
		this.hitbox.x = startXPosition;
		this.hitbox.y = startYPosition;
		this.changeInX = 0;
		this.changeInY = 0;
	}

	
	public int getLives() {
		return lives;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

}
