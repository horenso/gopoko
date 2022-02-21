package horenso.endpoint.http;

import horenso.endpoint.http.dto.TokenDto;
import horenso.endpoint.http.dto.UserDto;
import horenso.exceptions.InvalidCredentialsException;
import horenso.persistence.entity.User;
import horenso.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserEndpoint {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody UserDto userDto) {
        try {
            User user = userService.registerNewUser(userDto.getUsername(), userDto.getPassword());
            return new UserDto(user.getName(), user.getPassword());
        } catch (InvalidCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @GetMapping
    public TokenDto login(@RequestBody UserDto userDto) {
        try {
            String token = userService.getToken(userDto.getUsername(), userDto.getPassword());
            return new TokenDto(userDto.getUsername(), token);
        } catch (InvalidCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
