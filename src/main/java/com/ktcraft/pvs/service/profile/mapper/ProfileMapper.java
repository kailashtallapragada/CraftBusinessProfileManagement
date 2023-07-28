package com.ktcraft.pvs.service.profile.mapper;

import com.ktcraft.pvs.service.model.Profile;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.vo.ProfileDto;
import com.ktcraft.pvs.service.profile.vo.ProfileUpdateRequestDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(CustomProfileMapperImpl.class)
public interface ProfileMapper {

    Profile profileDtoToProfile(ProfileDto profileDto);

    ProfileDto profileToProfileDto(Profile profile);

    @Mappings({
            @Mapping(target = "validationStatus", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "createdBy", ignore = true)
    })
    ProfileUpdateRequest profileDtoToProfileUpdateRequest(ProfileDto profileDto);

    ProfileDto profileUpdateRequestToProfileDto(ProfileUpdateRequest profileUpdateRequest);

    Profile profileUpdateRequestToProfile(ProfileUpdateRequest profileUpdateRequest);

    ProfileUpdateRequestDto profileUpdateRequestToProfileUpdateRequestDto(ProfileUpdateRequest profileUpdateRequest);
}
