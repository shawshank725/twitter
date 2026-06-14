package com.social.twitter.authentication.internal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscController {

    @GetMapping("/misc")
    public String misc() {
        return "OK";
    }
}
