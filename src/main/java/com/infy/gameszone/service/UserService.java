package com.infy.gameszone.service;

import com.infy.gameszone.dto.UserDTO;
import com.infy.gameszone.exception.GameszoneException;

public interface UserService {

    String registerNewUser(UserDTO userDTO) throws GameszoneException;

    UserDTO getUser(String username) throws GameszoneException;

    UserDTO getUser(Integer userId) throws GameszoneException;

    UserDTO getUserByEmail(String email) throws GameszoneException;
}
