package util.exception;

public class UserUsernameExistException extends Exception {

    public UserUsernameExistException() {
    }

    public UserUsernameExistException(String msg) {
        super(msg);
    }
}
