package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Class: Turret
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a turret that shoots projectiles
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         Turret turret = new Turret(x,y);
 *         </pre>
 */
public class Turret extends GameObject {

	private static final int WIDTH = 20;
	private static final int HEIGHT = 30;
	private static final int OFFSET = 10;
	private static final int SHOOT_DELAY = 150;

	private int shootingCount;

	BufferedImage turretImage;

	/**
	 * ensures: Creation of a game object
	 * @param xPosition the object x position to start at
	 * @param yPosition the object y position to start at

	 * <br>
	 * requires: a game
	 */
	public Turret(int xPosition, int yPosition) {
		super(xPosition, yPosition, WIDTH, HEIGHT);
		shootingCount = 0;
		try {
			turretImage = ImageIO.read(new File("images/turret.png"));
		} catch (IOException e) {
			System.err.println("Image Does Not Exist");
		}
	}

	/**
	 * ensures: draws the turret on the screen
	 *
	 * <br>
	 * requires: a frame to draw the object on
	 * @param g the graphics that the turret is being drawn on
	 */
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(turretImage, xPosition, yPosition - OFFSET, WIDTH, HEIGHT, null);

	}
	
	/**
	 * ensures: the turret shoots a projectile
	 *
	 * <br>
	 * requires: a list of projectiles
	 */
	public void fire(ArrayList<Projectile> projectiles) {
		if (shootingCount >= SHOOT_DELAY) {
			shootingCount = 0;
			Projectile projLeft = new Projectile(xPosition + width / 2, yPosition + height * 1 / 3, -1);
			Projectile projRight = new Projectile(xPosition + width / 2, yPosition + height * 1 / 3, 1);
			projectiles.add(projLeft);
			projectiles.add(projRight);

		}
		shootingCount++;
	}

}
