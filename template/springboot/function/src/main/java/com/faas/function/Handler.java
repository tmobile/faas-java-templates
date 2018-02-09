package com.faas.function;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Handler {

    @RequestMapping("/")
    public String handle(@RequestBody byte[] payload) {
        return String.format("Hello, SpringBoot, You said: %s",  new String(payload));
    }
}
