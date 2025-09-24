package school.sorokin.javacore;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        CatalogManager catalogManager = new CatalogManager();
        while (true) {
            try {
                catalogManager.bookMenu();
            } catch (NoSuchElementException | NoAvailableCopiesException | NumberFormatException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
