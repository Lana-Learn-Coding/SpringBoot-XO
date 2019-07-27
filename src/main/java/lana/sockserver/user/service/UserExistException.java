package lana.sockserver.user.service;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserExistException extends Exception {
    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistException(String message) {
        super(message);
    }
}
