package com.alperensertoglu.mapper;

import com.alperensertoglu.dto.request.UserSaveRequestDto;
import com.alperensertoglu.dto.request.UserUpdateRequestDto;
import com.alperensertoglu.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(UserSaveRequestDto dto);

    UserProfile toUserFromUpdateRequest(UserUpdateRequestDto dto);


}
