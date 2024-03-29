package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("auth_service")
    public ResponseEntity<String> authServiceFallback(){
        return ResponseEntity.ok("Auth servis şuan servis dışı");
    }

    @GetMapping("user_service")
    public ResponseEntity<String> userServiceFallback() {
        return ResponseEntity.ok("User servis şuan servis dışı");
    }

}


