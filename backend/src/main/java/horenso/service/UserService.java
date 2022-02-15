package horenso.service;

import horenso.exceptions.InvalidCredentialsException;
import horenso.persistence.entity.User;

public interface UserService {
    User registerNewUser(String username, String password) throws InvalidCredentialsException;

    String getToken(String username, String password) throws InvalidCredentialsException;

    boolean verifyToken(String token);
}
