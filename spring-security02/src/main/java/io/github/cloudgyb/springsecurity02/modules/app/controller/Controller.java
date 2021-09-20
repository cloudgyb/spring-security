package io.github.cloudgyb.springsecurity02.modules.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cloudgyb
 * 2021/9/4 21:46
 */

@RestController
public class Controller {
    @GetMapping("/test1")
    @PreAuthorize("hasAnyRole('admin')")
    public Object test(){
        return "ok";
    }

    @GetMapping("/test2")
    public Object test2(){
        return "ok";
    }


    @GetMapping("/test3")
    @PreAuthorize("ups.hasPermission()")
    public Object test3(){
        return "ok";
    }
}
