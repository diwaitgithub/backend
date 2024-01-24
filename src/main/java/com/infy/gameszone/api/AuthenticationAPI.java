package com.infy.gameszone.api;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.gameszone.GameszoneApplication;
import com.infy.gameszone.api.requests.AuthRequest;
import com.infy.gameszone.api.responses.AuthResponse;
import com.infy.gameszone.exception.GameszoneException;
import com.infy.gameszone.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/auth")
public class AuthenticationAPI {

        @Autowired
        private AuthenticationService authenticationService;

        Logger logger = LoggerFactory.getLogger(GameszoneApplication.class);

        @PostMapping(value = "/generate-token")
        public ResponseEntity<AuthResponse> generateToken(@RequestBody @Valid AuthRequest authRequest)
                        throws Exception, GameszoneException, UsernameNotFoundException {

                logger.info("dfsdfsdfsdf 379 " + TimeZone.getDefault().toString());

                AuthResponse authResponse = authenticationService.authenticate(authRequest.getUserName(),
                                authRequest.getPassword());

                return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

        }

}
