package mainApp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Class: GameLevel
 * 
 * @author WS23-A-105 <br>
 *         Purpose: gets a game level and creates it based off of game level files
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         GameLevel a = new GameLevel();
 *         </pre>
 */
public class GameLevel {

	private static final int POSITION_MULTIPLIER = 40;

	// Instantiated Variables
	private static final String[] FILES = new String[] { "Level0", "Level1", "Level2", "Level3", "Level4", "Level5",
			"Level6", "Level7", "Level8", "LevelE" };

	private String levelFile;
	private BufferedImage levelBackground;
	private int backgroundIterator;
	private ArrayList<int[]> platforms = new ArrayList<int[]>();
	private ArrayList<int[]> enemies = new ArrayList<int[]>();
	private ArrayList<int[]> turrets = new ArrayList<int[]>();
	private int[] hero = new int[2];

	/**
	 * ensures: Creation of a new game level object to load levels from game files
	 *
	 * <br>
	 * requires: game level files
	 */
	public GameLevel() {
		this.levelFile = FILES[0];
		this.backgroundIterator = 0;
		try {
			this.getLevel(this.levelFile);
		} catch (FileNotFoundException e) {
			System.err.println("Error: File does not exist. ");
		}
	}

	/**
	 * ensures: gets all the information about a level from the characters stored in the level and stores them.
	 *
	 *@param LevelFile the name of the file without the .txt
	 *@throws FileNotFoundException
	 * <br>
	 * requires: a adequately made file to load
	 */
	public String getLevel(String levelFile) throws FileNotFoundException {
		platforms.clear();
		enemies.clear();
		turrets.clear();
		FileReader f1 = new FileReader("Levels/" + levelFile + ".txt");
		Scanner s1 = new Scanner(f1);
		int height = 0;
		int platStart = 0;
		while (s1.hasNext()) {
			String DataStr = s1.nextLine();
			for (int i = 0; i < DataStr.length(); i++) {
				if (DataStr.charAt(i) == '-' && DataStr.charAt(i - 1) == '-') {
					platforms.get(platforms.size() - 1)[2] += POSITION_MULTIPLIER;

				} else if (DataStr.charAt(i) == '-') {
					int[] platform = new int[4];
					platStart = i;
					platform[0] = (i - 1) * POSITION_MULTIPLIER;
					platform[1] = (height - 1) * POSITION_MULTIPLIER;
					platform[2] = POSITION_MULTIPLIER;
					platform[3] = POSITION_MULTIPLIER;
					platforms.add(platform);

				} else if (DataStr.charAt(i) == 'H') {
					hero[0] = (i - 1) * POSITION_MULTIPLIER;
					hero[1] = (height - 1) * POSITION_MULTIPLIER + 9;

				} else if (DataStr.charAt(i) == 'A') {
					int[] enemy = new int[2];
					enemy[0] = (i - 1) * POSITION_MULTIPLIER;
					enemy[1] = (height - 1) * POSITION_MULTIPLIER;
					enemies.add(enemy);
				} else if (DataStr.charAt(i) == 'T') {
					int[] turret = new int[2];
					turret[0] = (i - 1) * POSITION_MULTIPLIER;
					turret[1] = (height - 1) * POSITION_MULTIPLIER;
					turrets.add(turret);
				}

			}
			height++;
		} // end while
		return null;
	}

	/**
	 * ensures: the game is looking at the correct level file when loading a new file
	 *@param direction the direction the level is going positive is up negative is down
	 *@throws InvalidLevelFormatException
	 * <br>
	 * requires: the game is loaded to a good file
	 */
	public void changeLevel(int direction) throws InvalidLevelFormatException {
		int lastLevel = backgroundIterator;
		if (backgroundIterator + direction >= FILES.length) {
			backgroundIterator = FILES.length - 1;

		} else if (backgroundIterator + direction < 0) {
			backgroundIterator = 0;

		} else {
			backgroundIterator += direction;
		}

		if (!FILES[backgroundIterator].substring(0, 5).equals("Level") || FILES[backgroundIterator].length() > 7
				|| FILES[backgroundIterator].length() < 6) {
			backgroundIterator = lastLevel;
			throw new InvalidLevelFormatException();
		}

		try {
			this.getLevel(FILES[backgroundIterator]);
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
	}

	/**
	 * ensures: the game is set back the first level on a restart
	 *
	 * <br>
	 * requires: game level files
	 */
	public void restart() {
		this.backgroundIterator = 0;
		try {
			this.getLevel(FILES[backgroundIterator]);
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
	}
	
	public int getBackgroundIterator() {
		return backgroundIterator;
	}

	public ArrayList<int[]> getEnemies() {
		return enemies;
	}

	public static String[] getFiles() {
		return FILES;
	}

	public int[] getHero() {
		return hero;
	}

	public ArrayList<int[]> getPlatforms() {
		return platforms;
	}

	public ArrayList<int[]> getTurrets() {
		return turrets;
	}



}
