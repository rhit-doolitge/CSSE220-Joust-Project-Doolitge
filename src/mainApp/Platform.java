package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: platform
 * 
 * @author WS23-A-105 <br>
 *         Purpose: platform for a game that things hit and stop moving
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         Platform plat = new Platform(x,y,z,a);
 *         </pre>
 */
public class Platform extends GameObject {

	Line2D top;
	Line2D left;
	Line2D right;
	Line2D bottom;

	BufferedImage platImage;

	/**
	 * ensures: Creation of a game object
	 @param xPosition the object x position to start at
	 @param yPosition the object y position to start at
	 *@param width width of the object
	 *@param height height of the object
	 * <br>
	 * requires: a game
	 */
	public Platform(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
		top = new Line2D.Double(xPos + 1, yPos, xPos + width - 1, yPos);
		bottom = new Line2D.Double(xPos + 1, yPos + height, xPos + width - 1, yPos + height);
		left = new Line2D.Double(xPos, yPos + 1, xPos, yPos + height - 1);
		right = new Line2D.Double(xPos + width, yPos + 1, xPos + width, yPos + height - 1);
		try {
			platImage = ImageIO.read(new File("images/psychadelic.png"));
		} catch (IOException e) {
			System.err.println("Image Does Not Exist");
		}
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
		g.drawImage(platImage, xPosition, yPosition, width, height, null);
	}

}
