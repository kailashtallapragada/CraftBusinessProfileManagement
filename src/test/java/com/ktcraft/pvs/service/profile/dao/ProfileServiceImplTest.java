package com.ktcraft.pvs.service.profile.dao;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.ktcraft.pvs.constants.AppConstants;
import com.ktcraft.pvs.service.account.mapper.AccountMapper;
import com.ktcraft.pvs.service.exceptions.InvalidRequestException;
import com.ktcraft.pvs.service.exceptions.ResourceNotFoundException;
import com.ktcraft.pvs.service.exceptions.ServiceUnavailableException;
import com.ktcraft.pvs.service.model.Profile;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.persistance.util.DynamoDBUtils;
import com.ktcraft.pvs.service.profile.util.ProfileUtil;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Import(ProfileServiceImpl.class)
@ExtendWith(SpringExtension.class)
class ProfileServiceImplTest {

    @MockBean
    CacheManager cacheManager;

    @MockBean
    DynamoDBUtils dynamoDBUtils;

    @MockBean
    DynamoDBMapper dynamoDBMapper;

    @MockBean
    ProfileUtil profileUtil;

    @MockBean
    AccountMapper accountMapper;

    @Autowired
    ProfileServiceImpl profileService;

    @Test
    void createProfile() {
        final Profile profile = Mockito.mock(Profile.class);

        final DynamoDBSaveExpression dynamoDBSaveExpression = Mockito.mock(DynamoDBSaveExpression.class);
        when(dynamoDBUtils.saveWithoutPrimaryKey(Profile.class)).thenReturn(dynamoDBSaveExpression);

        profileService.createProfile(profile);

        Mockito.verify(dynamoDBMapper, Mockito.times(1)).save(profile, dynamoDBSaveExpression);

        Mockito.doThrow(new DynamoDBMappingException())
                .when(dynamoDBMapper)
                .save(profile, dynamoDBSaveExpression);

        assertThrows(InvalidRequestException.class, () -> profileService.createProfile(profile));

        Mockito.doThrow(Mockito.mock(ConditionalCheckFailedException.class))
                .when(dynamoDBMapper)
                .save(profile, dynamoDBSaveExpression);

        assertThrows(InvalidRequestException.class, () -> profileService.createProfile(profile));

        Mockito.doThrow(Mockito.mock(SdkClientException.class))
                .when(dynamoDBMapper)
                .save(profile, dynamoDBSaveExpression);

        assertThrows(ServiceUnavailableException.class, () -> profileService.createProfile(profile));
    }

    @Test
    void updateProfile() {
        final Profile profile = Mockito.mock(Profile.class);

        when(cacheManager.getCache(AppConstants.PROFILE_UPDATE_CACHE_KEY)).thenReturn(Mockito.mock(Cache.class));

        assertEquals(profile, profileService.updateProfile(profile));
        Mockito.verify(dynamoDBMapper, Mockito.times(1)).save(profile);

        Mockito.doThrow(new DynamoDBMappingException())
                .when(dynamoDBMapper)
                .save(profile);

        assertThrows(InvalidRequestException.class, () -> profileService.updateProfile(profile));

        Mockito.doThrow(Mockito.mock(ConditionalCheckFailedException.class))
                .when(dynamoDBMapper)
                .save(profile);

        assertThrows(InvalidRequestException.class, () -> profileService.updateProfile(profile));

        Mockito.doThrow(Mockito.mock(SdkClientException.class))
                .when(dynamoDBMapper)
                .save(profile);

        assertThrows(ServiceUnavailableException.class, () -> profileService.updateProfile(profile));
    }

    @Test
    void getProfileForOrganizationId() {

        assertThrows(InvalidRequestException.class, () -> profileService.getProfileForOrganizationId((Long) null));

        final Profile profile = Mockito.mock(Profile.class);

        Mockito.doReturn(profile)
                .when(dynamoDBMapper)
                .load(Profile.class, 10l);

        assertEquals(profile, profileService.getProfileForOrganizationId(10l));

        Mockito.doThrow(new DynamoDBMappingException())
                .when(dynamoDBMapper)
                .load(Profile.class, 10l);

        assertThrows(InvalidRequestException.class, () -> profileService.getProfileForOrganizationId(10l));

        Mockito.doThrow(Mockito.mock(ConditionalCheckFailedException.class))
                .when(dynamoDBMapper)
                .load(Profile.class, 10l);

        assertThrows(InvalidRequestException.class, () -> profileService.getProfileForOrganizationId(10l));

        Mockito.doThrow(Mockito.mock(SdkClientException.class))
                .when(dynamoDBMapper)
                .load(Profile.class, 10l);

        assertThrows(ServiceUnavailableException.class, () -> profileService.getProfileForOrganizationId(10l));

        Mockito.doReturn(null)
                .when(dynamoDBMapper)
                .load(Profile.class, 10l);

        assertThrows(ResourceNotFoundException.class ,() ->profileService.getProfileForOrganizationId(10l));
    }

    @Test
    void testGetProfileForOrganizationId() {
        assertThrows(InvalidRequestException.class,
                () -> profileService.getProfileForOrganizationId(new Profile()));
    }

    @Test
    void deleteProfileForOrganizationId() {
        assertThrows(InvalidRequestException.class, () -> profileService.deleteProfileForOrganizationId(null));
    }

    @Test
    void verifyProfileUpdateRequest() {
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        Mockito.doReturn(profileUpdateRequest).when(dynamoDBMapper).load(ProfileUpdateRequest.class, 123l);
        Mockito.doReturn(null).when(profileUtil).handleProfileUpdateRequest(profileUpdateRequest, 123l, 123l, 1,ProfileValidationStatus.ACCEPTED);

        assertEquals(profileUpdateRequest, profileService.verifyProfileUpdateRequest(123l, 123l, 1, ProfileValidationStatus.ACCEPTED));

        Mockito.doThrow(new DynamoDBMappingException()).when(dynamoDBMapper).save(profileUpdateRequest);
        assertThrows(InvalidRequestException.class, () -> profileService.verifyProfileUpdateRequest(123l, 123l, 1, ProfileValidationStatus.ACCEPTED));

        Mockito.doThrow(new ConditionalCheckFailedException("some error msg")).when(dynamoDBMapper).save(profileUpdateRequest);
        assertThrows(InvalidRequestException.class, () -> profileService.verifyProfileUpdateRequest(123l, 123l, 1, ProfileValidationStatus.ACCEPTED));

        Mockito.doThrow(new SdkClientException("some error msg")).when(dynamoDBMapper).save(profileUpdateRequest);
        assertThrows(ServiceUnavailableException.class, () -> profileService.verifyProfileUpdateRequest(123l, 123l, 1, ProfileValidationStatus.ACCEPTED));
    }

    @Test
    void deleteProfileUpdateRequest() {
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        Mockito.doReturn(profileUpdateRequest).when(dynamoDBMapper).load(ProfileUpdateRequest.class, 123l);
        Mockito.doThrow(new DynamoDBMappingException()).when(dynamoDBMapper).delete(profileUpdateRequest);
        assertThrows(InvalidRequestException.class, () -> profileService.deleteProfileUpdateRequest(123l));

        Mockito.doThrow(new ConditionalCheckFailedException("some exception msg")).when(dynamoDBMapper).delete( profileUpdateRequest);
        assertThrows(InvalidRequestException.class, () -> profileService.deleteProfileUpdateRequest(123l));

        Mockito.doThrow(new SdkClientException("some exception msg")).when(dynamoDBMapper).delete(profileUpdateRequest);
        assertThrows(ServiceUnavailableException.class, () -> profileService.deleteProfileUpdateRequest(123l));
    }
}