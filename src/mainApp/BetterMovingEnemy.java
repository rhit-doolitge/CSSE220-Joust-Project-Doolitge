package mainApp;

import java.util.Random;
/**
 * Class: BetterMovingEnemy
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a enemy object that moves more cleanly that can be drawn for a game
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         BetterMovingEnemy enemy = new BetterMovingEnemy(x,y);
 *         </pre>
 */
public class BetterMovingEnemy extends Enemy {

	// constants
	private static final int MAX_SPEED_ENEMY = 5;
	private static final int MOVEMENT_CHANGE_DELAY = 160;

	// instance variables
	private double accelY = 0;
	private double accelX = 0;

	/**
	 * ensures: the creation of a new better moving enemy.
	 *
	 * <br>
	 * @param xPosition the enemies x position to start at
	 * @param yPosition the enemies y position to start at
	 */
	public BetterMovingEnemy(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	}

	/**
	 * ensures: The enemy changes movement after a certain amount of time
	 *
	 * <br>
	 * requires: an enemy
	 */
	@Override
	public void changeMovement() {
		count++;
		this.accelerate();

		changeInX += accelX;
		changeInY += accelY;
		if (changeInX < -MAX_SPEED_ENEMY) {
			changeInX = -MAX_SPEED_ENEMY;
		}

		if (changeInX > MAX_SPEED_ENEMY) {
			changeInX = MAX_SPEED_ENEMY;
		}
		if (changeInY < -MAX_SPEED_ENEMY) {
			changeInY = -MAX_SPEED_ENEMY;
		}
		if (changeInY > MAX_SPEED_ENEMY) {
			changeInY = MAX_SPEED_ENEMY;
		}

		if (egg) {
			this.changeInX = 0;
			this.changeInY = MAX_MOVE_SPEED;
			if (count >= RESPAWN_WAIT) {
				egg = false;
				this.yPosition -= RESPAWN_OFFSET;
				this.hitbox.y -= RESPAWN_OFFSET;
				count = 0;
			}
		}

	}

	/**
	 * ensures: the enemies acceleration randomly changes
	 *
	 * <br>
	 * requires: a enemy
	 */
	public void accelerate() {
		Random rand = new Random();
		if (count >= MOVEMENT_CHANGE_DELAY) {
			if (rand.nextDouble() < .5) {
				accelX = ACCEL;
			} else {
				accelX = -ACCEL;
			}

			if (rand.nextDouble() < .5) {
				accelY = ACCEL;
			} else {
				accelY = -ACCEL;
			}
			count = 0;
		}
	}

}
