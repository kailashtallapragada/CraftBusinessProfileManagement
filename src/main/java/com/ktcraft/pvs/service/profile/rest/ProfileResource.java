package com.ktcraft.pvs.service.profile.rest;

import com.ktcraft.pvs.service.profile.bo.ProfileBO;
import com.ktcraft.pvs.service.profile.vo.ProfileUpdateRequestDto;
import com.ktcraft.pvs.service.profile.vo.ProfileValidationDto;
import com.ktcraft.pvs.service.security.AccountAuthentication;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ktcraft.pvs.service.profile.vo.ProfileDto;

@RestController
@RequestMapping("/profile")
public class ProfileResource {

    private final Logger logger;
    private final ProfileBO profileBO;

    @Autowired
    public ProfileResource(final Logger logger, final ProfileBO profileBO) {
        this.logger = logger;
        this.profileBO = profileBO;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileDto createProfile(@CurrentSecurityContext(expression = "authentication") AccountAuthentication auth,
                                    @RequestBody final ProfileDto profileDto) {
        profileDto.setOrganizationId(auth.getOrganizationId());
        return profileBO.createProfileFromDto(profileDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileDto getProfile(@CurrentSecurityContext(expression = "authentication") AccountAuthentication auth) {
         return profileBO.getProfileDto(auth.getOrganizationId());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProfileDto updateProfile(@CurrentSecurityContext(expression = "authentication") AccountAuthentication auth,
                                    @RequestBody final ProfileDto profileDto) {
        profileDto.setOrganizationId(auth.getOrganizationId());
        return profileBO.updateProfileFromDto(profileDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@CurrentSecurityContext(expression = "authentication") AccountAuthentication auth) {
        profileBO.deleteProfileDto(auth.getOrganizationId());
    }

    @GetMapping("/updaterequest")
    @ResponseStatus(HttpStatus.OK)
    public ProfileUpdateRequestDto getProfileUpdateRequest(
            @CurrentSecurityContext(expression = "authentication") AccountAuthentication auth) {
        return profileBO.getProfileUpdateRequestDto();
    }

    @PatchMapping("/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void verifyProfile(@CurrentSecurityContext(expression = "authentication") AccountAuthentication auth,
                              @RequestBody ProfileValidationDto profileValidationDto) {
        profileBO.verifyProfileUpdateRequest(profileValidationDto);
    }
}