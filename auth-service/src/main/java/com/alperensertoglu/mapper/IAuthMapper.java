package com.alperensertoglu.mapper;

import com.alperensertoglu.dto.request.RegisterRequestDto;
import com.alperensertoglu.dto.request.UserSaveRequestDto;
import com.alperensertoglu.dto.response.RegisterResponseDto;
import com.alperensertoglu.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth toAuthFromRegisterRequest(RegisterRequestDto dto);

    RegisterResponseDto toResponseDto(Auth auth);

    @Mapping(source = "id", target = "authId")
    UserSaveRequestDto toUserRequestFromAuth(Auth auth);
//    Authdaki id yi SaveRequestdeki authIdye eşlemiş olduk
}
