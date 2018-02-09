package com.faas.function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class Handler {

    @RequestMapping("/")
    public ResponseEntity<String> handle(@RequestBody @Valid byte[] payload) {
        String response = String.format("Hello, SpringBoot, You said: %s",  new String(payload));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
