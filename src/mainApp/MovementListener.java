package mainApp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class: MovementListener
 * 
 * @author WS23-A-105 <br>
 *         Purpose: moves the hero based off of key inputs as well as changing level
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         new MovementListener(a);
 *         </pre>
 */
public class MovementListener implements KeyListener {

	// Instantiated Variables
	private static final int KEY_LEFT = 37;
	private static final int KEY_RIGHT = 39;
	private static final int KEY_UP = 38;
	private static final int KEY_U = 85;
	private static final int KEY_D = 68;
	private static final int KEY_ESC = 27;
	private GameComponent gameComponent;

	/**
	 * ensures: creation of the movement listener
	 * @param gameComponent the component that the action are going to happen to
	 */
	public MovementListener(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

	/**
	 * ensures: the correct thing happens with the correct key typed
	 *
	 * <br>
	 * requires: a keyboard
	 * @param e a key being typed
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * ensures: the correct thing happens with the correct key presses
	 *
	 * <br>
	 * requires: a keyboard
	 * @param e a key being pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KEY_LEFT) {
			this.gameComponent.getHero().setLeft(true);
		} else if (e.getKeyCode() == KEY_RIGHT) {
			this.gameComponent.getHero().setRight(true);
		}
		if (e.getKeyCode() == KEY_UP) {
			this.gameComponent.getHero().setUp(true);
		}
		if (e.getKeyCode() == KEY_U && this.gameComponent.isBeatlevel()) {
			try {
				this.gameComponent.getLevel().changeLevel(1);
				this.gameComponent.setLevelTracker(this.gameComponent.getLevel().getBackgroundIterator());
			} catch (InvalidLevelFormatException e1) {
				System.err.println("Incorrect File format");
			}
			this.gameComponent.createLevel();
		} else if (e.getKeyCode() == KEY_D) {
			try {
				this.gameComponent.getLevel().changeLevel(-1);
				this.gameComponent.setLevelTracker(this.gameComponent.getLevel().getBackgroundIterator());
			} catch (InvalidLevelFormatException e1) {
				System.err.println("Incorrect File format");
			}
			this.gameComponent.createLevel();
		}
		if (e.getKeyCode() == KEY_ESC) {
			this.gameComponent.restart();
		}

		gameComponent.repaint();
	}

	/**
	 * ensures: the correct thing happens with the correct key releases
	 *
	 * <br>
	 * requires: a keyboard key being pressed
	 * @param e a key being released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KEY_LEFT) {
			this.gameComponent.getHero().setLeft(false);
		} else if (e.getKeyCode() == KEY_RIGHT) {
			this.gameComponent.getHero().setRight(false);
		}
		if (e.getKeyCode() == KEY_UP) {
			this.gameComponent.getHero().setUp(false);
		}
	}

}
