package scheduler;

import model.User;

public interface ChildPaneController {

    /**
     * Takes and stores the current user of the application for logging purposes.
     * @param user the current user of the application
     */
     void setUser(User user);
}
