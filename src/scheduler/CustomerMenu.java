package scheduler;

import model.User;

public class CustomerMenu implements ChildPaneController{
    private User currentUser;

    /**
     * Takes and stores the current user of the application for logging purposes.
     * @param user the current user of the application
     */
    @Override
    public void setUser(User user) {
        currentUser = user;
    }
}
