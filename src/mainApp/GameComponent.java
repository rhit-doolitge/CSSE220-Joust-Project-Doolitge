package mainApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class: GameComponent
 * 
 * @author WS23-105 <br>
 *         Purpose: Used to Create and update the game objects and draw them on
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         GameComponent gameComponent = new GameComponent();
 *         </pre>
 */
public class GameComponent extends JComponent {

	// Constants
	private static final int FONT_SIZE = 36;
	private static final int STARTING_LIVES = 3;

	private static final int SCREEN_W = 1200;
	private static final int SCREEN_H = 800;

	private static final int SCOREBOARD_X = 550;
	private static final int SCOREBOARD_Y = 740;
	private static final int SCOREBOARD_BORDER_X = 540;
	private static final int SCOREBOARD_BORDER_Y = 725;
	private static final int SCOREBOARD_BORDER_W = 140;
	private static final int SCOREBOARD_BORDER_H = 20;

	private static final int LAVA_X = 0;
	private static final int LAVA_Y = 730;
	private static final int LAVA_W = 1200;
	private static final int LAVA_H = 70;

	private static final int JOUST_X = 350;
	private static final int JOUST_Y = 100;
	private static final int JOUST_W = 500;
	private static final int JOUST_H = 250;

	private static final int RESTART_X = 80;
	private static final int RESTART_Y = 80;
	private static final int RESTART_W = 180;
	private static final int RESTART_H = 200;

	private static final int MOVEMENT_X = 800;
	private static final int MOVEMENT_Y = 430;
	private static final int MOVEMENT_W = 350;
	private static final int MOVEMENT_H = 250;

	private static final int CHANGE_LEVEL_X = 100;
	private static final int CHANGE_LEVEL_Y = 430;
	private static final int CHANGE_LEVEL_W = 250;
	private static final int CHANGE_LEVEL_H = 250;

	private static final int STRING_Y = 350;
	private static final int BEAT_GAME_X = 380;
	private static final int BEAT_LEVEL_X = 360;
	private static final int GAME_OVER_X = 515;

	// Instantiated Variables
	private int score;
	private int lives;
	private int levelTracker;

	private Hero hero;
	private GameLevel level;
	private BufferedImage lava;
	private BufferedImage joust;
	private BufferedImage restart;
	private BufferedImage movement;
	private BufferedImage changeLevel;

	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Projectile> projectilesToRemove = new ArrayList<Projectile>();
	private ArrayList<Turret> turrets = new ArrayList<Turret>();

	private boolean start;
	private boolean gameOver;
	private boolean beatlevel;

	/**
	 * ensures: the creation of the Game Component and it's fields
	 * 
	 * @param frame
	 */
	public GameComponent() {
		this.score = 0;
		this.levelTracker = 0;
		this.lives = STARTING_LIVES;
		start = false;
		gameOver = false;
		beatlevel = false;
		this.level = new GameLevel();
		this.createLevel();
		try {
			lava = ImageIO.read(new File("images/lava.png"));
			joust = ImageIO.read(new File("images/joust.png"));
			restart = ImageIO.read(new File("images/restart.png"));
			movement = ImageIO.read(new File("images/arrows.png"));
			changeLevel = ImageIO.read(new File("images/Level Change.png"));
		} catch (IOException e) {
			System.err.println("Image Does Not Exist");
		}

	}

	/**
	 * ensures: draws all the game objects on the screen in their respective
	 * locations and controls the different game states;
	 * 
	 * @param g Graphics object
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, SCREEN_W, SCREEN_H);

		if (!gameOver && !start) {
			g2.drawImage(lava, LAVA_X, LAVA_Y, LAVA_W, LAVA_H, null);

			hero.draw(g2);
			for (Projectile proj : projectiles) {
				proj.draw(g2);
			}
			for (Turret turret : turrets) {
				turret.draw(g2);
			}
			for (Platform plat : platforms) {
				plat.draw(g2);
			}
			for (Enemy enemy : enemies) {
				enemy.draw(g2);
			}

			g2.setColor(new Color(160, 120, 56));
			g2.fillRect(SCOREBOARD_BORDER_X, SCOREBOARD_BORDER_Y, SCOREBOARD_BORDER_W, SCOREBOARD_BORDER_H);
			g2.setColor(Color.BLACK);
			g2.drawRect(SCOREBOARD_BORDER_X, SCOREBOARD_BORDER_Y, SCOREBOARD_BORDER_W, SCOREBOARD_BORDER_H);
			g2.drawString("Lives: " + this.hero.getLives() + "    Score: " + this.score, SCOREBOARD_X, SCOREBOARD_Y);
			if (this.beatlevel) {
				g2.setColor(Color.RED);
				Font gameOverFont = new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE);
				String beatLevel = "";
				g2.setFont(gameOverFont);
				if (this.levelTracker == 0) {
					g2.drawImage(joust, JOUST_X, JOUST_Y, JOUST_W, JOUST_H, null);
					g2.drawImage(restart, RESTART_X, RESTART_Y, RESTART_W, RESTART_H, null);
					g2.drawImage(changeLevel, CHANGE_LEVEL_X, CHANGE_LEVEL_Y, CHANGE_LEVEL_W, CHANGE_LEVEL_H, null);
					g2.drawImage(movement, MOVEMENT_X, MOVEMENT_Y, MOVEMENT_W, MOVEMENT_H, null);
				} else if (this.levelTracker == GameLevel.getFiles().length - 1) {
					beatLevel = "You Beat The Whole Game";
					g2.drawString(beatLevel, BEAT_GAME_X, STRING_Y);
				} else {

					beatLevel = "You Beat The Level Nice Job!";
					g2.drawString(beatLevel, BEAT_LEVEL_X, STRING_Y);

				}

			}
		}
		if (gameOver) {
			String gameOverString = "Game Over!";
			Font gameOverFont = new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE);
			g2.setColor(Color.RED);
			g2.setFont(gameOverFont);
			g2.drawString(gameOverString, GAME_OVER_X, STRING_Y);
		}
	}

	/**
	 * ensures: The game state updates every frame <br>
	 * requires: a running timer
	 */
	public void update() {
		if (!beatlevel) {
			hero.move();
			hero.accelerate();
		}

		for (Projectile projectile : projectiles) {
			projectile.move();
			if (projectile.dead) {
				projectilesToRemove.add(projectile);
			}
		}

		for (Enemy enemy : enemies) {
			enemy.move();
			enemy.changeMovement();
			if (enemy.dead) {
				enemiesToRemove.add(enemy);
				score += 100;
			}
		}

		for (Projectile proj : projectilesToRemove) {
			this.projectiles.remove(proj);
		}
		for (Enemy enemy : enemiesToRemove) {
			this.enemies.remove(enemy);
		}
		for (Turret turret : turrets) {
			turret.fire(projectiles);
		}

		this.collisions();

		if (enemies.size() == 0) {
			beatlevel = true;
		} else {
			beatlevel = false;
		}
	}

	/**
	 * ensures: A new level is create when the player presses the U or D keys on the
	 * keyboard
	 * 
	 * <br>
	 * requires: the level being create exists and has the correct format.
	 */
	public void createLevel() {
		this.platforms.clear();
		this.enemies.clear();
		this.turrets.clear();
		this.projectiles.clear();
		this.hero = new Hero(this.getLevel().getHero()[0], this.getLevel().getHero()[1], this.lives);
		for (int[] plat : this.getLevel().getPlatforms()) {
			Platform platform = new Platform(plat[0], plat[1], plat[2], plat[3]);
			platforms.add(platform);
		}
		for (int[] turret : this.getLevel().getTurrets()) {
			Turret newTurret = new Turret(turret[0], turret[1]);
			turrets.add(newTurret);
		}
		for (int i = 0; i < this.getLevel().getEnemies().size(); i++) {
			Enemy newEnemy;
			if (i == this.getLevel().getEnemies().size() - 1) {
				newEnemy = new TrackingEnemy(this.getLevel().getEnemies().get(i)[0],
						this.getLevel().getEnemies().get(i)[1], hero);
			} else {
				Random rand = new Random();
				int version = rand.nextInt(1, 4);
				if (version == 1) {
					newEnemy = new Enemy(this.getLevel().getEnemies().get(i)[0],
							this.getLevel().getEnemies().get(i)[1]);
				} else if (version == 2) {
					newEnemy = new BetterMovingEnemy(this.getLevel().getEnemies().get(i)[0],
							this.getLevel().getEnemies().get(i)[1]);
				} else {
					newEnemy = new ShootingEnemy(this.getLevel().getEnemies().get(i)[0],
							this.getLevel().getEnemies().get(i)[1], projectiles);
				}
			}
			enemies.add(newEnemy);
		}

	}

	/**
	 * ensures: All the collisions are handled and the objects are update properly
	 *
	 * <br>
	 * requires: different objects that can collide
	 */
	public void collisions() {
		for (Platform plat : platforms) {
			hero.collidesWithPlatformXDirection(plat);
			hero.collidesWithPlatformYDirection(plat);
			for (Enemy enemy : enemies) {
				enemy.collidesWithPlatformXDirection(plat);
				enemy.collidesWithPlatformYDirection(plat);
			}
			for (Projectile proj : projectiles) {
				proj.collidesWithPlatformXDirection(plat);
			}
		}
		for (Enemy enemy : enemies) {
			hero.CollideWithEnemy(enemy);
		}
		for (Projectile proj : projectiles) {
			hero.CollideWithProjectile(proj);
		}
		this.lives = this.hero.getLives();
		if (this.lives <= 0) {
			gameOver = true;
			enemies.clear();
			platforms.clear();
			hero.xPosition = SCREEN_W / 2 - hero.width;
			hero.yPosition = SCREEN_H / 2;
		}
	}

	/**
	 * ensures: The Game is restarted correctly setting values to start values
	 *
	 * <br>
	 * requires: the game must be started already
	 */
	public void restart() {
		this.lives = STARTING_LIVES;
		this.score = 0;
		this.gameOver = false;
		this.levelTracker = 0;
		this.getLevel().restart();
		this.createLevel();

	}

	/**
	 * ensures: the screen is redrawn after the game state is updated <br>
	 * requires: A running timer
	 */
	public void drawScreen() {
		this.repaint();

	}

	
	public Hero getHero() {
		return hero;
	}

	public GameLevel getLevel() {
		return level;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isBeatlevel() {
		return beatlevel;
	}

	public void setBeatlevel(boolean beatlevel) {
		this.beatlevel = beatlevel;
	}

	public int getLevelTracker() {
		return levelTracker;
	}

	public void setLevelTracker(int levelTracker) {
		this.levelTracker = levelTracker;
	}
}
