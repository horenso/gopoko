package horenso.service;

import horenso.exceptions.UnknownUserException;
import horenso.exceptions.UsernameTakenException;

public interface LoginService {


    String register(String username) throws UsernameTakenException;
    String getToken(String username) throws UnknownUserException;
}
