package com.alperensertoglu.controller;

import com.alperensertoglu.dto.request.UserSaveRequestDto;
import com.alperensertoglu.dto.request.UserUpdateRequestDto;
import com.alperensertoglu.repository.entity.UserProfile;
import com.alperensertoglu.service.UserService;
import com.alperensertoglu.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.alperensertoglu.constant.EndPoints.*;

/*
    findbystatus metodu yazıp cacheleme yapalım

 */
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody UserSaveRequestDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<String> activateStatus(@RequestParam String token) {
        return ResponseEntity.ok(userService.activateStatus(token));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> updateUserProfile(@RequestBody @Valid UserUpdateRequestDto dto) {
        return ResponseEntity.ok(userService.updateUserProfile(dto));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<UserProfile>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }


}
