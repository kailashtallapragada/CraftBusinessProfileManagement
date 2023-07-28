package com.ktcraft.pvs.service.profile.util;

import com.ktcraft.pvs.service.model.Account;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfileUtil {

    public ProfileUpdateRequest handleProfileUpdateRequest(ProfileUpdateRequest profileUpdateRequest,
                                                           Long organizationId, Long accountId, Integer productId,
                                                           ProfileValidationStatus status) {
        if (profileUpdateRequest.getStatus() != null
                && !profileUpdateRequest.getStatus().equals(ProfileValidationStatus.IN_PROGRESS)) {
            return profileUpdateRequest;
        }

        Map<String, List<Account>> validationStatus = profileUpdateRequest.getValidationStatus();
        if (validationStatus == null) {
            validationStatus = new HashMap<>();
            profileUpdateRequest.setValidationStatus(validationStatus);
        }
        final Account account = new Account(organizationId, accountId, productId);
        if (validationStatus.get(ProfileValidationStatus.REJECTED.getProfileValidationStatus()) == null) {
            validationStatus.put(ProfileValidationStatus.REJECTED.getProfileValidationStatus(), new ArrayList<>());
        }
        if (validationStatus.get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()) == null) {
            validationStatus.put(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus(), new ArrayList<>());
        }
        if (validationStatus.get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()) == null) {
            validationStatus.put(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus(), new ArrayList<>());
        }
        if (ProfileValidationStatus.REJECTED.equals(status)) {
            if(validationStatus.get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).contains(account)) {
                validationStatus.get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).remove(account);
            } else if(validationStatus.get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).contains(account)) {
                validationStatus.get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).remove(account);
            }
            validationStatus.get(ProfileValidationStatus.REJECTED.getProfileValidationStatus()).add(account);
        } else if (ProfileValidationStatus.ACCEPTED.equals(status)) {
            if(validationStatus.get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).contains(account)) {
                validationStatus.get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).remove(account);
            }
            if(!validationStatus.get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).contains(account)) {
                validationStatus.get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).add(account);
            }
        }

        if (validationStatus.get(ProfileValidationStatus.REJECTED.getProfileValidationStatus()).size() > 0) {
            profileUpdateRequest.setStatus(ProfileValidationStatus.REJECTED);
        } else if (validationStatus.get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).size() == 0) {
            profileUpdateRequest.setStatus(ProfileValidationStatus.ACCEPTED);
        }

        return profileUpdateRequest;
    }
}
