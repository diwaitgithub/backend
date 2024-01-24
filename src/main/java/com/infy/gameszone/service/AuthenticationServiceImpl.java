package com.infy.gameszone.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.infy.gameszone.api.responses.AuthResponse;
import com.infy.gameszone.constants.UserStatus;
import com.infy.gameszone.exception.GameszoneException;
import com.infy.gameszone.security.CustomUserDetails;
import com.infy.gameszone.security.jwt.JwtService;

@Service(value = "AuthenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        public AuthResponse authenticate(String userName, String password)
                        throws GameszoneException, UsernameNotFoundException {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(userName, password));

                CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userName);
                //

                String authToken = jwtService.generateToken(userDetails);
                ;
                AuthResponse authResponse = new AuthResponse(
                                userDetails.getUserId(),
                                userDetails.getUsername(),
                                authToken,
                                userDetails.getAuthorities().stream().map(role -> role.getAuthority())
                                                .collect(Collectors.toList()),
                                (userDetails.isEnabled()) ? UserStatus.ACTIVE : UserStatus.INACTIVE);

                return authResponse;
        }

}
