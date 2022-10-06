package VTTP.FinalProject.exceptions;

public class UserNotCreatedException extends Exception {

    public UserNotCreatedException() {
        super("Internal error! Please try again.");
    }
    public UserNotCreatedException(String message) {
        super(message);
    }
}
