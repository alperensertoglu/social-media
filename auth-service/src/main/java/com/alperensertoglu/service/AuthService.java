package com.alperensertoglu.service;

import com.alperensertoglu.dto.request.ActivateRequestDto;
import com.alperensertoglu.dto.request.AuthUpdateRequestDto;
import com.alperensertoglu.dto.request.LoginRequestDto;
import com.alperensertoglu.dto.request.RegisterRequestDto;
import com.alperensertoglu.dto.response.RegisterResponseDto;
import com.alperensertoglu.exception.AuthManagerException;
import com.alperensertoglu.exception.ErrorType;
import com.alperensertoglu.manager.IUserManager;
import com.alperensertoglu.mapper.IAuthMapper;
import com.alperensertoglu.repository.IAuthRepository;
import com.alperensertoglu.repository.entity.Auth;
import com.alperensertoglu.repository.enums.EStatus;
import com.alperensertoglu.utility.CodeGenerator;
import com.alperensertoglu.utility.JwtTokenManager;
import com.alperensertoglu.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;

    private final IUserManager userManager;

    public AuthService(IAuthRepository authRepository, JwtTokenManager jwtTokenManager, IUserManager userManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
    }

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.toAuthFromRegisterRequest(dto);
        auth.setActivationCode(CodeGenerator.genarateCode());
        if (authRepository.existsByUsername(dto.getUsername()))
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);

        save(auth);
// @Transactional -> Metod içinde bir hata olmazsa sorunsuz çalışıyor. Sorun oluşursa rollback yapar
//        iki mikroservis arası haberleşmek için metod yazılacak
        userManager.save(IAuthMapper.INSTANCE.toUserRequestFromAuth(auth));
        RegisterResponseDto responseDto = IAuthMapper.INSTANCE.toResponseDto(auth);
        String token = jwtTokenManager.createToken(auth.getId()).get();
        responseDto.setToken(token);
        return responseDto;
    }

    public String login(LoginRequestDto dto) {
        try {
            authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
            return "Giriş başarılı";
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

    }

    @Transactional
    public String activateStatus(ActivateRequestDto dto) {
        Optional<Long> id = jwtTokenManager.getIdFromToken(dto.getToken());
        if (id.isEmpty()) {
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Auth> optionalAuth = findById(id.get());
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ALREADY_ACTIVE);
        }
        if (dto.getActivationCode().equals(optionalAuth.get().getActivationCode())) {
            optionalAuth.get().setStatus(EStatus.ACTIVE);
            update(optionalAuth.get());
            userManager.activateStatus(dto.getToken()); // open feign ile haberleşme
            return "Hesabınız aktive edilmiştir";
        } else {
            throw new AuthManagerException(ErrorType.INVALID_CODE);
        }
    }

    public String updateAuth(AuthUpdateRequestDto dto) {
        Optional<Auth> auth = authRepository.findById(dto.getId());
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        update(auth.get());
        return "Update Successful";
    }

    public List<Auth> findAll() {
        return authRepository.findAll();
    }
}