package io.github.cloudgyb.springsecurity01.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cloudgyb
 * 2021/7/11 11:30
 */
@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome!";
    }

    @GetMapping("/welcome/json")
    @PreAuthorize("hasRole('USER')")
    public Map<String,Object> welcomeJson() {
        return Map.of("greeting","welcome!");
    }
}
