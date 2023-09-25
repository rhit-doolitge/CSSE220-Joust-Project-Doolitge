package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Class: BetterMovingEnemy
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a enemy object that moves and follows the hero that can be drawn for a game
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         TrackingEnemy enemy = new TrackingEnemy(x,y,z);
 *         </pre>
 */
public class TrackingEnemy extends Enemy {

	private static final int MAX_SPEED_ENEMY = 5;
	private static final int TRACKER_HEIGHT = 30;
	private static final int TRACKER_WIDTH = 40;

	Hero hero;

	/**
	 * ensures: the creation of a new tracking enemy
	 *
	 * <br>
	 * @param xPosition the enemies x position to start at
	 * @param yPosition the enemies y position to start at
	 * @param hero the thing being tracked
	 */
	public TrackingEnemy(int xPosition, int yPosition, Hero hero) {
		super(xPosition, yPosition);
		this.hero = hero;
		this.count = 0;
		try {
			enemyImage = ImageIO.read(new File("images/pter.png"));
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
			this.hitbox.width = TRACKER_WIDTH;
			this.hitbox.height = TRACKER_HEIGHT;
			this.height = TRACKER_HEIGHT;
			this.width = TRACKER_WIDTH;
			if (this.changeInX > 0) {
				g.drawImage(enemyImage, xPosition, yPosition, width, height, null);
			} else {
				g.drawImage(enemyImage, xPosition + width, yPosition, -width, height, null);
			}
		}

	}

	/**
	 * ensures: the velocity of the enemy is changed based on a certain interval
	 *
	 * <br>
	 * requires: a enemy that has a certain x and y
	 */
	@Override
	public void changeMovement() {
		if (egg) {
			this.changeInX = 0;
			this.changeInY = MAX_MOVE_SPEED;
			if (count >= RESPAWN_WAIT) {
				egg = false;
				this.yPosition -= RESPAWN_OFFSET;
				this.hitbox.y -= RESPAWN_OFFSET;
				count = 0;
			}
		} else {

			if (this.hero.xPosition > this.xPosition) {
				this.changeInX += ACCEL;
			} else if (this.hero.xPosition < this.xPosition) {
				this.changeInX -= ACCEL;
			}
			if (this.hero.yPosition > this.yPosition) {
				this.changeInY += ACCEL;
			} else if (this.hero.yPosition < this.yPosition) {
				this.changeInY -= ACCEL;
			}

			if (changeInX > MAX_SPEED_ENEMY) {
				changeInX = MAX_SPEED_ENEMY;
			} else if (changeInX < -MAX_SPEED_ENEMY) {
				changeInX = -MAX_SPEED_ENEMY;
			}
			if (changeInY > MAX_SPEED_ENEMY / 2) {
				changeInY = MAX_SPEED_ENEMY / 2;
			} else if (changeInX < -MAX_SPEED_ENEMY / 2) {
				changeInX = -MAX_SPEED_ENEMY / 2;
			}
		}
		count++;
	}
}
