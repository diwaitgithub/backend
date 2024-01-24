package com.infy.gameszone.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.infy.gameszone.api.responses.AuthResponse;
import com.infy.gameszone.exception.GameszoneException;

public interface AuthenticationService {

    AuthResponse authenticate(String userName, String password) throws GameszoneException, UsernameNotFoundException;

}
