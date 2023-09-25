package mainApp;

import java.util.ArrayList;

/**
 * Class: ShootingEnemy
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a enemy object that moves and shoots that can be drawn for a game
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         ShootingEnemy enemy = new ShootingEnemy(x,y,z);
 *         </pre>
 */
public class ShootingEnemy extends Enemy {

	protected static final int SHOOT_DELAY = 75;

	ArrayList<Projectile> projectiles;
	int shootingCount;

	/**
	 * ensures: Creation of a game object
	 * @param xPosition the object x position to start at
	 * @param yPosition the object y position to start at
	 *@param projectiles a list of projectile objects
	 * <br>
	 * requires: a game
	 */
	public ShootingEnemy(int xPosition, int yPosition, ArrayList<Projectile> projectiles) {
		super(xPosition, yPosition);
		shootingCount = 0;
		this.projectiles = projectiles;
	}

	/**
	 * ensures: the enemy shoots a projectile
	 *
	 * <br>
	 * requires: a list of projectiles
	 */
	public void fire() {
		Projectile proj;
		if (shootingCount >= SHOOT_DELAY) {
			shootingCount = 0;
			if (changeInX < 0) {
				proj = new Projectile(this.xPosition, this.yPosition + height / 2, -1);
			} else {
				proj = new Projectile(this.xPosition + this.width, this.yPosition + height / 2, 1);
			}
			projectiles.add(proj);
		}
		shootingCount++;
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
		if (!egg) {
			this.fire();
		}
	}

}
