package com.alperensertoglu.controller;

import com.alperensertoglu.constant.EndPoints;
import com.alperensertoglu.dto.request.ActivateRequestDto;
import com.alperensertoglu.dto.request.AuthUpdateRequestDto;
import com.alperensertoglu.dto.request.LoginRequestDto;
import com.alperensertoglu.dto.request.RegisterRequestDto;
import com.alperensertoglu.dto.response.RegisterResponseDto;
import com.alperensertoglu.repository.entity.Auth;
import com.alperensertoglu.service.AuthService;
import com.alperensertoglu.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;


    @PostMapping(EndPoints.SAVE)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(EndPoints.LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(EndPoints.ACTIVATE_STATUS)
    public ResponseEntity<String> login(@RequestBody ActivateRequestDto dto) {
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PutMapping(EndPoints.UPDATE)
    public ResponseEntity<String> updateAuth(@RequestBody AuthUpdateRequestDto dto) {
        return ResponseEntity.ok(authService.updateAuth(dto));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Auth>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }
}
