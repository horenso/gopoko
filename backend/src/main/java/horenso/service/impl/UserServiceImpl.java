package horenso.service.impl;

import horenso.exceptions.InvalidCredentialsException;
import horenso.persistence.entity.User;
import horenso.persistence.repository.UserRepository;
import horenso.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Random random = new Random();

    @Override
    public User registerNewUser(String username, String password) throws InvalidCredentialsException {
        Optional<User> userWithSameName = userRepository.findFirstByName(username);
        if (userWithSameName.isPresent()) {
            throw new InvalidCredentialsException(String.format("username %s is already taken", username));
        }
        byte[] byteArray = new byte[12];
        random.nextBytes(byteArray);
        String token = Base64.getEncoder().encodeToString(byteArray);
        return userRepository.save(new User(username, password, token));
    }

    @Override
    public String getToken(String username, String password) throws InvalidCredentialsException {
        Optional<User> user = userRepository.findFirstByNameAndPassword(username, password);
        if (user.isEmpty()) {
            throw new InvalidCredentialsException(String.format("invalid credentials", username));
        }
        return user.get().getToken();
    }

    @Override
    public boolean verifyUserToken(String username, String token) {
        return userRepository.existsByNameAndToken(username, token);
    }
}
