package com.ktcraft.pvs.service.profile.dao;

import com.ktcraft.pvs.service.model.Profile;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;

public interface ProfileService {

    Profile createProfile(Profile profile);

    Profile updateProfile(Profile profile);

    Profile getProfileForOrganizationId(Long id);

    Profile getProfileForOrganizationId(Profile profile);

    void deleteProfileForOrganizationId(Long id);

    void deleteProfileforOrganizationId(Profile profile);

    ProfileUpdateRequest createProfileUpdateRequest(ProfileUpdateRequest profileUpdateRequest);

    ProfileUpdateRequest getProfileUpdateRequestForOrganizationId(Long id);

    ProfileUpdateRequest verifyProfileUpdateRequest(Long organizationId, Long accountId, Integer productId,
                                                    ProfileValidationStatus status);

    void deleteProfileUpdateRequest(Long organizationId);
}
