package chrisbrn.iqs_api.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logs {

	private static Logger LOGGER = Logger.getLogger("InfoLogging");

	public static void databaseConnectionError(){
		LOGGER.log(Level.WARNING, "Database Connection Error");
	}
}
