package chrisbrn.iqs_api.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logs {

	private static Logger LOGGER = Logger.getLogger("InfoLogging");

	public static void databaseConnectionError(){
		LOGGER.log(Level.WARNING, "Database Connection Error");
	}

	public static void tokenGenerationError(){
		LOGGER.log(Level.WARNING, "Token Generation Error");
	}

	public static void tokenVerificationError(){
		LOGGER.log(Level.INFO, "Token Validation Error");
	}
}
