package scheduler;

import model.User;

public interface ChildPaneController {

    /**
     * Stores a reference to the calling window's controller.
     * @param menuController Controller for main window, needed for communication.
     */
    void setMenuController(MainWindow menuController );

    /**
     * Takes and stores the current user of the application for logging purposes.
     * @param user the current user of the application
     */
    void setUser(User user);
}
