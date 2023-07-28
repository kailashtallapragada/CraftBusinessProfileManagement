package com.ktcraft.pvs.service.profile.mapper;

import com.ktcraft.pvs.service.model.Profile;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.vo.ProfileDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-27T18:17:33+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.12 (Amazon.com Inc.)"
)
@Component
@Primary
public class ProfileMapperImpl extends CustomProfileMapperImpl {

    @Autowired
    @Qualifier("delegate")
    private ProfileMapper delegate;

    @Override
    public Profile profileDtoToProfile(ProfileDto profileDto)  {
        return delegate.profileDtoToProfile( profileDto );
    }

    @Override
    public ProfileDto profileToProfileDto(Profile profile)  {
        return delegate.profileToProfileDto( profile );
    }

    @Override
    public ProfileUpdateRequest profileDtoToProfileUpdateRequest(ProfileDto profileDto)  {
        return delegate.profileDtoToProfileUpdateRequest( profileDto );
    }

    @Override
    public ProfileDto profileUpdateRequestToProfileDto(ProfileUpdateRequest profileUpdateRequest)  {
        return delegate.profileUpdateRequestToProfileDto( profileUpdateRequest );
    }

    @Override
    public Profile profileUpdateRequestToProfile(ProfileUpdateRequest profileUpdateRequest)  {
        return delegate.profileUpdateRequestToProfile( profileUpdateRequest );
    }
}
