package com.ktcraft.pvs.service.profile.util;

import com.ktcraft.pvs.service.model.Account;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileUtilTest {

    @Autowired
    ProfileUtil profileUtil;

    @Test
    void handleProfileUpdateRequest_withoutStatusInProgress() {

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUpdateRequest.setStatus(ProfileValidationStatus.REJECTED);

        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.ACCEPTED);

        assertEquals(ProfileValidationStatus.REJECTED, profileUpdateRequest.getStatus());

        profileUpdateRequest.setStatus(ProfileValidationStatus.ACCEPTED);
        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.REJECTED);

        assertEquals(ProfileValidationStatus.ACCEPTED, profileUpdateRequest.getStatus());
    }

    @Test
    void handleProfileUpdateRequest_toStatusRejected() {
        final Account account = new Account(10l, 20l, 1);

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.REJECTED);
        assertEquals(ProfileValidationStatus.REJECTED, profileUpdateRequest.getStatus());
        assertEquals(1, profileUpdateRequest.getValidationStatus()
                        .get(ProfileValidationStatus.REJECTED.getProfileValidationStatus()).size());

        profileUpdateRequest.setStatus(ProfileValidationStatus.IN_PROGRESS);
        profileUpdateRequest.getValidationStatus().put(
                ProfileValidationStatus.REJECTED.getProfileValidationStatus(), new ArrayList<>());
        profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus())
                .add(account);
        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.REJECTED);
        assertEquals(ProfileValidationStatus.REJECTED, profileUpdateRequest.getStatus());
        assertEquals(1, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.REJECTED.getProfileValidationStatus()).size());
        assertEquals(0, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).size());

        profileUpdateRequest.setStatus(ProfileValidationStatus.IN_PROGRESS);
        profileUpdateRequest.getValidationStatus().put(
                ProfileValidationStatus.REJECTED.getProfileValidationStatus(), new ArrayList<>());
        profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus())
                .add(account);
        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.REJECTED);
        assertEquals(ProfileValidationStatus.REJECTED, profileUpdateRequest.getStatus());
        assertEquals(1, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.REJECTED.getProfileValidationStatus()).size());
        assertEquals(0, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).size());
    }

    @Test
    void handleProfileUpdateRequest_toStatusAccepted() {
        final Account account = new Account(10l, 20l, 1);
        final Account account2 = new Account(10l, 20l, 2);

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.ACCEPTED);
        assertEquals(ProfileValidationStatus.ACCEPTED, profileUpdateRequest.getStatus());
        assertEquals(1, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).size());

        profileUpdateRequest.setStatus(ProfileValidationStatus.IN_PROGRESS);
        profileUpdateRequest.getValidationStatus().put(
                ProfileValidationStatus.ACCEPTED.getProfileValidationStatus(), new ArrayList<>());
        profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus())
                .add(account);
        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.ACCEPTED);
        assertEquals(ProfileValidationStatus.ACCEPTED, profileUpdateRequest.getStatus());
        assertEquals(1, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).size());
        assertEquals(0, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.IN_PROGRESS.getProfileValidationStatus()).size());

        profileUpdateRequest.setStatus(ProfileValidationStatus.IN_PROGRESS);
        profileUpdateRequest.getValidationStatus().put(
                ProfileValidationStatus.ACCEPTED.getProfileValidationStatus(), new ArrayList<>());
        profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus())
                .add(account);
        profileUtil.handleProfileUpdateRequest(
                profileUpdateRequest, 10l, 20l, 1, ProfileValidationStatus.ACCEPTED);
        assertEquals(ProfileValidationStatus.ACCEPTED, profileUpdateRequest.getStatus());
        assertEquals(1, profileUpdateRequest.getValidationStatus()
                .get(ProfileValidationStatus.ACCEPTED.getProfileValidationStatus()).size());
    }
}