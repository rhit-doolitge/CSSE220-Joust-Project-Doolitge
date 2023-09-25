package mainApp;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Class: MainApp
 * 
 * @author W23_A_105 <br>
 *         Purpose: Top level class for CSSE220 Project containing main method
 *         <br>
 *         Restrictions: None
 */
public class MainApp {

	// Instantiated Variables
	private static final int DELAY = 25;
	private static final int SCREEN_WIDTH = 1200;
	private static final int SCREEN_HEIGHT = 800;

	/**
	 * ensures: the game is run
	 *
	 * <br>
	 * requires: all the components of the game to be created properly
	 */
	private void runApp() {
		System.out.println("Now Running Joust");
		JFrame frame = new JFrame();
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		GameComponent gameComponent = new GameComponent();
		MovementListener listener = new MovementListener(gameComponent);
		GameAdvanceListener advanceListener = new GameAdvanceListener(gameComponent);
		Timer timer = new Timer(DELAY, advanceListener);

		frame.addKeyListener(listener);
		frame.add(gameComponent);
		timer.start();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} // runApp

	/**
	 * ensures: runs the application
	 * 
	 * @param args unused
	 * @throws InvalidLevelFormatException
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
	} // main

}