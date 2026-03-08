package io.og4dev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String message(){
        return "Welcome Admin";
    }
}
