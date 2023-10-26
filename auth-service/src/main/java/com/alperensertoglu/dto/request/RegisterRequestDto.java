package com.alperensertoglu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotBlank(message = "Kullanıcı adı boş geçilemez")
    @Size(min = 3, message = "Kullanıcı adı 3 harften az olamaz")
    private String username;
    @NotBlank(message = "Email boş geçilemez")
    @Email
    private String email;
    @NotBlank(message = "Şifre boş geçilemez")
    @Size(min = 5, max = 32, message = "Şifre en az 5 en fazla 32 karakter olabilir")
    private String password;
}
