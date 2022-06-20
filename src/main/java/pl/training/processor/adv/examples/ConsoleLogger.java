package pl.training.processor.adv.examples;

import java.util.logging.Logger;

public enum ConsoleLogger {

    INSTANCE;

    private final Logger logger = Logger.getLogger(getClass().getName());

    public void log(String entry) {
        logger.info(entry);
    }

    public static ConsoleLogger getLogger() {
        return INSTANCE;
    }

}
