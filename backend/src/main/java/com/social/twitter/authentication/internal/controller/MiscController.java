package com.social.twitter.authentication.internal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscController {

    @GetMapping("/misc")
    public String misc() {
        return "OK";
    }
}
