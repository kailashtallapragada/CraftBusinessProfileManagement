package com.ktcraft.pvs.service.profile.mapper;

import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.vo.ProfileUpdateRequestDto;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import com.ktcraft.pvs.service.security.util.AuthUtil;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class CustomProfileMapperImpl implements ProfileMapper {

    @Autowired
    public AuthUtil authUtil;

    @Override
    public ProfileUpdateRequestDto profileUpdateRequestToProfileUpdateRequestDto(ProfileUpdateRequest profileUpdateRequest) {
        if ( profileUpdateRequest == null ) {
            return null;
        }

        ProfileUpdateRequestDto profileUpdateRequestDto = new ProfileUpdateRequestDto();

        profileUpdateRequestDto.organizationId = profileUpdateRequest.organizationId;
        profileUpdateRequestDto.companyName = profileUpdateRequest.companyName;
        profileUpdateRequestDto.legalName = profileUpdateRequest.legalName;
        profileUpdateRequestDto.taxIdentifier = profileUpdateRequest.taxIdentifier;
        profileUpdateRequestDto.email = profileUpdateRequest.email;
        profileUpdateRequestDto.website = profileUpdateRequest.website;
        profileUpdateRequestDto.businessAddress = profileUpdateRequest.businessAddress;
        profileUpdateRequestDto.legalAddress = profileUpdateRequest.legalAddress;
        profileUpdateRequestDto.status = profileUpdateRequest.status;
        if (profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus())
                .contains(authUtil.getCurrentAccount())) {
            profileUpdateRequestDto.setMyStatus(ProfileValidationStatus.IN_PROGRESS);
        } else if (profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus())
                .contains(authUtil.getCurrentAccount())) {
            profileUpdateRequestDto.setMyStatus(ProfileValidationStatus.ACCEPTED);
        } else if (profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.REJECTED.getProfileValidationStatus())
                .contains(authUtil.getCurrentAccount())) {
            profileUpdateRequestDto.setMyStatus(ProfileValidationStatus.REJECTED);
        }
        profileUpdateRequestDto.createdBy = profileUpdateRequest.createdBy;

        return profileUpdateRequestDto;
    }
}
