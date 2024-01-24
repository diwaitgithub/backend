package com.infy.gameszone.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.gameszone.api.requests.RegisterUserRequest;
import com.infy.gameszone.dto.UserDTO;
import com.infy.gameszone.exception.GameszoneException;
import com.infy.gameszone.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/user")
@Validated
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> postNewUser(@RequestBody @Valid RegisterUserRequest registerUserRequest)
            throws GameszoneException {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(registerUserRequest.getUserName());
        userDTO.setPassword(registerUserRequest.getPassword());
        userDTO.setStatus(registerUserRequest.getStatus());
        userDTO.setEmail(registerUserRequest.getEmail());

        String result = userService.registerNewUser(userDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<UserDTO> getUser(
            @RequestParam(required = false, name = "userId") Integer userId,
            @RequestParam(required = false, name = "userName") String userName,
            @RequestParam(required = false, name = "email") @Email(message = "{user.email.format.invalid}") String email)
            throws GameszoneException {
        //
        UserDTO userDTO = new UserDTO();
        if (userId != null) {
            userDTO = userService.getUser(userId);
        } else if (userName != null) {
            userDTO = userService.getUser(userName);
        } else if (email != null) {
            userDTO = userService.getUserByEmail(email);
        } else {
            throw new GameszoneException("UserAPI.QUERY_NOT_PROVIDED");
        }

        userDTO.setPassword(null);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }
}
