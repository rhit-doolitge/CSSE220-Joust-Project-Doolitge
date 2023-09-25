package mainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class: GameAdvanceListener
 * 
 * @author WS23-A-105 <br>
 *         Purpose: updates the game with each tick after a set delay
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         GameAdvanceListener a = new GameAdvanceListenr(x);
 *         </pre>
 */
public class GameAdvanceListener implements ActionListener {

	// Instantiated Variables
	private GameComponent gameComponent;
	
	/**
	 * ensures: Constructs a game advance listener object 
	 *@param gameComponent the component of a game that is being advanced
	 * <br>
	 * 
	 */
	public GameAdvanceListener(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}
	
	/**
	 * ensures: advances the game one tick
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	/**
	 * ensures: The game is properly update each on each tick
	 *
	 * <br>
	 * requires: a game to update
	 */
	private void advanceOneTick() {
		this.gameComponent.update();
		this.gameComponent.drawScreen();
	}

}
