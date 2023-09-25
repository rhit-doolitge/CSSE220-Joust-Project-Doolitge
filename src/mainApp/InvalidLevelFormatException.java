package mainApp;

/**
 * Class: InvalidLevelFormatException
 * 
 * @author WS23-A-105 <br>
 *         Purpose: a exception for a file not having the right format
 *         the screen <br>
 *         Restrictions: <br>
 *         For example:
 * 
 *         <pre>
 *         Throws new InvalidLevelFormatException
 *         </pre>
 */
public class InvalidLevelFormatException extends Exception {

	/**
	 * ensures: creation of the exception
	 */
	public InvalidLevelFormatException() {

	}

	/**
	 * ensures: returns information about the exception
	 */
	public String getInformation() {
		return "Wrong format!";
	}
}
