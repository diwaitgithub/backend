package com.infy.gameszone.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.infy.gameszone.exception.GameszoneException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/api-test")
public class TestAPI {

    @GetMapping(value = "/t1/{id}")
    public ResponseEntity<String> testOne(@PathVariable String id) throws Exception {

        // throw new GameszoneException("Test.EXCEPTION_MESSAGE");
        return new ResponseEntity<String>("Given string is : " + id, HttpStatus.OK);
    }

}
