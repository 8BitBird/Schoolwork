package assign5;

import java.io.IOException;
import java.util.logging.*;


/**
 * Logger Class.  Will Log all log calls to "Logging.txt" in folder executed.
 *
 * @author Laura webber
 * @author metsis
 * @version 4/19/19
 */

public class MyGUILogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    private Logger logger;

    public void setup() throws IOException {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        //logger = Logger.getLogger("MyLog");
        
        logger.setLevel(Level.INFO);

        fileTxt = new FileHandler("Logging.txt");
        fileTxt.setFormatter(new SimpleFormatter());
        logger.addHandler(fileTxt);
    }
    public Logger getLogger(){
        return logger;
    }
}
