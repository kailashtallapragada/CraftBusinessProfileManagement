package com.ktcraft.pvs.service.profile.bo;

import com.ktcraft.pvs.service.account.dao.AccountService;
import com.ktcraft.pvs.service.account.mapper.AccountMapper;
import com.ktcraft.pvs.service.exceptions.GeneralException;
import com.ktcraft.pvs.service.model.Account;
import com.ktcraft.pvs.service.model.Profile;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.dao.ProfileService;
import com.ktcraft.pvs.service.profile.mapper.ProfileMapper;
import com.ktcraft.pvs.service.profile.vo.ProfileDto;
import com.ktcraft.pvs.service.profile.vo.ProfileUpdateRequestDto;
import com.ktcraft.pvs.service.profile.vo.ProfileValidationDto;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import com.ktcraft.pvs.service.security.util.AuthUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfileBO {

    private final Logger logger;
    private final ProfileMapper profileMapper;
    private final ProfileService profileService;
    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final AuthUtil authUtil;

    public ProfileBO(final Logger logger, final ProfileMapper profileMapper, final ProfileService profileService,
                     final AccountMapper accountMapper, final AccountService accountService, final AuthUtil authUtil) {
        this.logger = logger;
        this.profileMapper = profileMapper;
        this.profileService = profileService;
        this.accountMapper = accountMapper;
        this.accountService = accountService;
        this.authUtil = authUtil;
    }

    public ProfileDto createProfileFromDto(final ProfileDto profileDto) {
        final Profile profile = profileMapper.profileDtoToProfile(profileDto);
        profileService.createProfile(profile);
        return profileMapper.profileToProfileDto(profile);
    }

    public ProfileDto getProfileDto(final Long id) {
        final Profile profile = profileService.getProfileForOrganizationId(id);
        return profileMapper.profileToProfileDto(profile);
    }

    public ProfileDto updateProfileFromDto(final ProfileDto profileDto) {
        getProfileDto(profileDto.getOrganizationId());
        final ProfileUpdateRequest profileUpdateRequest =
                profileMapper.profileDtoToProfileUpdateRequest(profileDto);
        initProfileUpdateRequest(profileUpdateRequest);
        profileService.createProfileUpdateRequest(profileUpdateRequest);
        if (profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).size() == 0) {
            verifyProfileUpdateRequest(new ProfileValidationDto(ProfileValidationStatus.ACCEPTED));
        }
        return profileMapper.profileUpdateRequestToProfileDto(profileUpdateRequest);
    }

    public void deleteProfileDto(final Long id) {
        profileService.deleteProfileForOrganizationId(id);
        try {
            deleteProfileUpdateRequest(id);
        } catch (GeneralException e) {}
    }

    public void verifyProfileUpdateRequest(ProfileValidationDto profileValidationDto) {
        final Account currentAccount = authUtil.getCurrentAccount();
        final ProfileUpdateRequest profileUpdateRequest =
                profileService.verifyProfileUpdateRequest(currentAccount.getOrganizationId(),
                        currentAccount.getAccountId(), currentAccount.getProductId(),
                        profileValidationDto.getProfileValidationStatus());
        if (!profileUpdateRequest.getStatus().equals(ProfileValidationStatus.IN_PROGRESS)) {
            processProfileUpdateRequest(profileUpdateRequest.getOrganizationId());
        }
    }

    public void deleteProfileUpdateRequest(final Long id) {
        profileService.deleteProfileUpdateRequest(id);
    }

    public ProfileUpdateRequestDto getProfileUpdateRequestDto() {
        final Account currentAccount = authUtil.getCurrentAccount();
        final ProfileUpdateRequest profileUpdateRequest =
                profileService.getProfileUpdateRequestForOrganizationId(currentAccount.getOrganizationId());
        return profileMapper.profileUpdateRequestToProfileUpdateRequestDto(profileUpdateRequest);
    }

    private void processProfileUpdateRequest(final Long id) {
        final ProfileUpdateRequest profileUpdateRequest = profileService.getProfileUpdateRequestForOrganizationId(id);
        if (ProfileValidationStatus.REJECTED.equals(profileUpdateRequest.getStatus())) {
            deleteProfileUpdateRequest(id);
        } else if (ProfileValidationStatus.ACCEPTED.equals(profileUpdateRequest.getStatus())) {
            final Profile profile = profileMapper.profileUpdateRequestToProfile(profileUpdateRequest);
            profileService.updateProfile(profile);
            deleteProfileUpdateRequest(id);
        }
    }

    private Map<String, List<Account>> generateProfileValidationStatus() {
        final Account currentAccount = authUtil.getCurrentAccount();
        Map<String, List<Account>> validationStatus = new HashMap<>();
        validationStatus.put(ProfileValidationStatus.REJECTED.getProfileValidationStatus(), new ArrayList<>());
        validationStatus.put(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus(),
                new ArrayList<>(Arrays.asList(currentAccount)));
        validationStatus.put(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus(),
                new ArrayList<>(accountMapper.getAccountFromAccountDto(accountService.getAccountsForOrganizationId(
                                        currentAccount.getOrganizationId(), currentAccount.getAccountId()))));
        validationStatus.get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).remove(currentAccount);
        return validationStatus;
    }

    private void initProfileUpdateRequest(ProfileUpdateRequest profileUpdateRequest) {
        if (profileUpdateRequest != null) {
            profileUpdateRequest.setStatus(ProfileValidationStatus.IN_PROGRESS);
            profileUpdateRequest.setCreatedBy(authUtil.getCurrentAccount());
            profileUpdateRequest.setValidationStatus(generateProfileValidationStatus());
        }
    }
}
