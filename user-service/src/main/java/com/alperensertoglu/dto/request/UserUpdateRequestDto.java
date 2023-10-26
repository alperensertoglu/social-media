package com.alperensertoglu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto {
    private String token;
    @NotBlank(message = "Kullanıcı adı boş geçilemez")
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String avatar;
    private String name;
    private String surName;
    private LocalDate birthDate;
}
