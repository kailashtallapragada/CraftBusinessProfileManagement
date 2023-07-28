package com.ktcraft.pvs.service.profile.dao;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.ktcraft.pvs.constants.AppConstants;
import com.ktcraft.pvs.constants.ErrorConstants;
import com.ktcraft.pvs.service.account.mapper.AccountMapper;
import com.ktcraft.pvs.service.exceptions.InvalidRequestException;
import com.ktcraft.pvs.service.exceptions.ResourceNotFoundException;
import com.ktcraft.pvs.service.exceptions.ServiceUnavailableException;
import com.ktcraft.pvs.service.model.Profile;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.persistance.util.DynamoDBUtils;
import com.ktcraft.pvs.service.profile.util.ProfileUtil;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private static final String PROFILE_PREFIX = "profile_";

    private final CacheManager cacheManager;
    private final DynamoDBUtils dynamoDBUtils;
    private final DynamoDBMapper dynamoDBMapper;
    private final ProfileUtil profileUtil;
    private final AccountMapper accountMapper;

    @Autowired
    public ProfileServiceImpl(final CacheManager cacheManager, final DynamoDBUtils dynamoDBUtils,
                              final DynamoDBMapper dynamoDBMapper, final ProfileUtil profileUtil,
                              AccountMapper accountMapper) {
        this.cacheManager = cacheManager;
        this.dynamoDBUtils = dynamoDBUtils;
        this.dynamoDBMapper = dynamoDBMapper;
        this.profileUtil = profileUtil;
        this.accountMapper = accountMapper;
    }

    @Override
    @CachePut(value = AppConstants.PROFILE_CACHE_KEY, key="#profile.organizationId")
    public Profile createProfile(Profile profile) {
        try {
            dynamoDBMapper.save(profile, dynamoDBUtils.saveWithoutPrimaryKey(Profile.class));
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.DuplicateProfile.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
        return profile;
    }

    @Override
    @CachePut(value = AppConstants.PROFILE_CACHE_KEY, key="#profile.organizationId")
    public Profile updateProfile(Profile profile) {
        cacheManager.getCache(AppConstants.PROFILE_UPDATE_CACHE_KEY).evict(profile.getOrganizationId());
        try {
            dynamoDBMapper.save(profile);
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.DuplicateProfile.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
        return profile;
    }

    @Override
    @Cacheable(value = AppConstants.PROFILE_CACHE_KEY, key="#id")
    public Profile getProfileForOrganizationId(Long id) {
        if (id == null) {
            throw new InvalidRequestException(ErrorConstants.InvalidInput.getMessage());
        }
        final Profile profile;
        try {
            profile = dynamoDBMapper.load(Profile.class, id);
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.DuplicateProfileUpdateRequest.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
        if (null == profile) {
            throw new ResourceNotFoundException(ErrorConstants.ProfileNotFound.getMessage());
        }
        return profile;
    }

    @Override
    public Profile getProfileForOrganizationId(Profile profile) {
        if (profile == null || profile.getOrganizationId() == null) {
            throw new InvalidRequestException(ErrorConstants.InvalidInput.getMessage());
        }
        return this.getProfileForOrganizationId(profile.getOrganizationId());
    }

    @Override
    public void deleteProfileForOrganizationId(Long id) {
        if (id == null) {
            throw new InvalidRequestException(ErrorConstants.InvalidInput.getMessage());
        }
        deleteProfileforOrganizationId(new Profile(id));
    }

    @Override
    public void deleteProfileforOrganizationId(Profile profile) {
        try {
            dynamoDBMapper.delete(getProfileForOrganizationId(profile.getOrganizationId()));
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.DuplicateProfileUpdateRequest.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
        cacheManager.getCache(AppConstants.PROFILE_CACHE_KEY).evict(profile.getOrganizationId());
    }

    @Override
    @CachePut(value = AppConstants.PROFILE_UPDATE_CACHE_KEY, key="#profileUpdateRequest")
    public ProfileUpdateRequest createProfileUpdateRequest(ProfileUpdateRequest profileUpdateRequest) {
        try {
            dynamoDBMapper.save(profileUpdateRequest, dynamoDBUtils.saveWithoutPrimaryKey(Profile.class));
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.DuplicateProfileUpdateRequest.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
        return profileUpdateRequest;
    }

    @Override
    @Cacheable(value = AppConstants.PROFILE_UPDATE_CACHE_KEY, key="#id")
    public ProfileUpdateRequest getProfileUpdateRequestForOrganizationId(final Long id) {
        if (id == null) {
            throw new InvalidRequestException(ErrorConstants.InvalidInput.getMessage());
        }
        final ProfileUpdateRequest profileUpdateRequest;
        try {
            profileUpdateRequest = dynamoDBMapper.load(ProfileUpdateRequest.class, id);
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.ProfileVerificationNotAccepted.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
        if (null == profileUpdateRequest) {
            throw new ResourceNotFoundException(ErrorConstants.ProfileUpdateRequestNotFound.getMessage());
        } else {
            if (profileUpdateRequest.getValidationStatus() != null) {
                for (Map.Entry e: profileUpdateRequest.getValidationStatus().entrySet()) {
                    if (e.getValue() != null && e.getValue() instanceof List) {
                        final List<Object> collect = ((List<?>) e.getValue()).stream().map(v -> {
                            if (v instanceof Map) {
                                return accountMapper.getAccountFromMap((Map) v);
                            }
                            return v;
                        }).collect(Collectors.toList());
                        e.setValue(collect);
                    }
                }
            }
        }
        return profileUpdateRequest;
    }

    @Override
    public ProfileUpdateRequest verifyProfileUpdateRequest(final Long organizationId, final Long accountId,
                                                           final Integer productId,
                                                           final ProfileValidationStatus status) {
        final ProfileUpdateRequest profileUpdateRequest = getProfileUpdateRequestForOrganizationId(organizationId);
        profileUtil.handleProfileUpdateRequest(profileUpdateRequest, organizationId, accountId, productId, status);
        try {
            dynamoDBMapper.save(profileUpdateRequest);
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.ProfileVerificationNotAccepted.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
        return profileUpdateRequest;
    }

    @Override
    public void deleteProfileUpdateRequest(final Long organizationId) {
        try {
            dynamoDBMapper.delete(getProfileUpdateRequestForOrganizationId(organizationId));
        } catch (DynamoDBMappingException e) {
            throw new InvalidRequestException(e.getMessage());
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidRequestException(ErrorConstants.ProfileVerificationNotAccepted.getMessage());
        } catch (SdkClientException e) {
            throw new ServiceUnavailableException();
        }
    }


}
