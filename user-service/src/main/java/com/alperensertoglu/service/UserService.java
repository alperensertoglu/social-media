package com.alperensertoglu.service;

import com.alperensertoglu.dto.request.AuthUpdateRequestDto;
import com.alperensertoglu.dto.request.UserSaveRequestDto;
import com.alperensertoglu.dto.request.UserUpdateRequestDto;
import com.alperensertoglu.exception.ErrorType;
import com.alperensertoglu.exception.UserManagerException;
import com.alperensertoglu.manager.IAuthManager;
import com.alperensertoglu.mapper.IUserMapper;
import com.alperensertoglu.repository.IUserRepository;
import com.alperensertoglu.repository.entity.UserProfile;
import com.alperensertoglu.repository.enums.EStatus;
import com.alperensertoglu.utility.JwtTokenManager;
import com.alperensertoglu.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService extends ServiceManager<UserProfile, Long> {

    private final IUserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IAuthManager authManager;

    public UserService(IUserRepository userRepository, JwtTokenManager jwtTokenManager, IAuthManager authManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
    }


    public Boolean create(UserSaveRequestDto dto) {
        UserProfile userProfile = IUserMapper.INSTANCE.toUserProfile(dto);
        try {
            save(userProfile);
            return true;
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }


    }

    public String activateStatus(String token) {
        Optional<Long> authId = jwtTokenManager.getAuthIdFromToken(token);
        if (authId.isEmpty())
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = userRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return "Hesap Aktive Edildi";

    }

    @Transactional
    public String updateUserProfile(@RequestBody UserUpdateRequestDto dto) {
        Optional<Long> authId = jwtTokenManager.getAuthIdFromToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = userRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);


        if (!userProfile.get().getEmail().equals(dto.getEmail())
                || !userProfile.get().getUsername().equals(dto.getUsername())) {

            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            authManager.updateAuth(AuthUpdateRequestDto.builder()
                    .email(dto.getEmail())
                    .username(dto.getUsername())
                    .id(userProfile.get().getAuthId())
                    .build());
        }
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setName(dto.getName());
        userProfile.get().setBirthDate(dto.getBirthDate());
        userProfile.get().setSurName(dto.getSurName());
        userProfile.get().setPassword(dto.getPassword());

        update(userProfile.get());

        return "Güncelleme başarılı";


    }
}