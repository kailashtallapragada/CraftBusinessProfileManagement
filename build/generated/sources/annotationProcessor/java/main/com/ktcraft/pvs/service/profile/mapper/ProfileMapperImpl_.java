package com.ktcraft.pvs.service.profile.mapper;

import com.ktcraft.pvs.service.model.Address;
import com.ktcraft.pvs.service.model.Profile;
import com.ktcraft.pvs.service.model.ProfileUpdateRequest;
import com.ktcraft.pvs.service.profile.vo.AddressDto;
import com.ktcraft.pvs.service.profile.vo.ProfileDto;
import com.ktcraft.pvs.service.profile.vo.ProfileUpdateRequestDto;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-27T18:17:33+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.12 (Amazon.com Inc.)"
)
@Component
@Qualifier("delegate")
public class ProfileMapperImpl_ implements ProfileMapper {

    @Override
    public Profile profileDtoToProfile(ProfileDto profileDto) {
        if ( profileDto == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setOrganizationId( profileDto.getOrganizationId() );
        profile.setCompanyName( profileDto.getCompanyName() );
        profile.setLegalName( profileDto.getLegalName() );
        profile.setTaxIdentifier( profileDto.getTaxIdentifier() );
        profile.setEmail( profileDto.getEmail() );
        profile.setWebsite( profileDto.getWebsite() );
        profile.setBusinessAddress( addressDtoToAddress( profileDto.getBusinessAddress() ) );
        profile.setLegalAddress( addressDtoToAddress( profileDto.getLegalAddress() ) );
        if ( profileDto.getCreatedAt() != null ) {
            profile.setCreatedAt( Date.from( profileDto.getCreatedAt() ) );
        }
        if ( profileDto.getUpdatedAt() != null ) {
            profile.setUpdatedAt( Date.from( profileDto.getUpdatedAt() ) );
        }

        return profile;
    }

    @Override
    public ProfileDto profileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setOrganizationId( profile.getOrganizationId() );
        profileDto.setCompanyName( profile.getCompanyName() );
        profileDto.setLegalName( profile.getLegalName() );
        profileDto.setTaxIdentifier( profile.getTaxIdentifier() );
        profileDto.setEmail( profile.getEmail() );
        profileDto.setWebsite( profile.getWebsite() );
        profileDto.setBusinessAddress( addressToAddressDto( profile.getBusinessAddress() ) );
        profileDto.setLegalAddress( addressToAddressDto( profile.getLegalAddress() ) );
        if ( profile.getCreatedAt() != null ) {
            profileDto.setCreatedAt( profile.getCreatedAt().toInstant() );
        }
        if ( profile.getUpdatedAt() != null ) {
            profileDto.setUpdatedAt( profile.getUpdatedAt().toInstant() );
        }

        return profileDto;
    }

    @Override
    public ProfileUpdateRequest profileDtoToProfileUpdateRequest(ProfileDto profileDto) {
        if ( profileDto == null ) {
            return null;
        }

        Long organizationId = null;

        organizationId = profileDto.getOrganizationId();

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest( organizationId );

        profileUpdateRequest.companyName = profileDto.getCompanyName();
        profileUpdateRequest.legalName = profileDto.getLegalName();
        profileUpdateRequest.taxIdentifier = profileDto.getTaxIdentifier();
        profileUpdateRequest.email = profileDto.getEmail();
        profileUpdateRequest.website = profileDto.getWebsite();
        profileUpdateRequest.businessAddress = addressDtoToAddress( profileDto.getBusinessAddress() );
        profileUpdateRequest.legalAddress = addressDtoToAddress( profileDto.getLegalAddress() );
        if ( profileDto.getCreatedAt() != null ) {
            profileUpdateRequest.createdAt = Date.from( profileDto.getCreatedAt() );
        }
        if ( profileDto.getUpdatedAt() != null ) {
            profileUpdateRequest.updatedAt = Date.from( profileDto.getUpdatedAt() );
        }

        return profileUpdateRequest;
    }

    @Override
    public ProfileDto profileUpdateRequestToProfileDto(ProfileUpdateRequest profileUpdateRequest) {
        if ( profileUpdateRequest == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setOrganizationId( profileUpdateRequest.organizationId );
        profileDto.setCompanyName( profileUpdateRequest.companyName );
        profileDto.setLegalName( profileUpdateRequest.legalName );
        profileDto.setTaxIdentifier( profileUpdateRequest.taxIdentifier );
        profileDto.setEmail( profileUpdateRequest.email );
        profileDto.setWebsite( profileUpdateRequest.website );
        profileDto.setBusinessAddress( addressToAddressDto( profileUpdateRequest.businessAddress ) );
        profileDto.setLegalAddress( addressToAddressDto( profileUpdateRequest.legalAddress ) );
        if ( profileUpdateRequest.createdAt != null ) {
            profileDto.setCreatedAt( profileUpdateRequest.createdAt.toInstant() );
        }
        if ( profileUpdateRequest.updatedAt != null ) {
            profileDto.setUpdatedAt( profileUpdateRequest.updatedAt.toInstant() );
        }

        return profileDto;
    }

    @Override
    public Profile profileUpdateRequestToProfile(ProfileUpdateRequest profileUpdateRequest) {
        if ( profileUpdateRequest == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setOrganizationId( profileUpdateRequest.organizationId );
        profile.setCompanyName( profileUpdateRequest.companyName );
        profile.setLegalName( profileUpdateRequest.legalName );
        profile.setTaxIdentifier( profileUpdateRequest.taxIdentifier );
        profile.setEmail( profileUpdateRequest.email );
        profile.setWebsite( profileUpdateRequest.website );
        profile.setBusinessAddress( profileUpdateRequest.businessAddress );
        profile.setLegalAddress( profileUpdateRequest.legalAddress );
        profile.setCreatedAt( profileUpdateRequest.createdAt );
        profile.setUpdatedAt( profileUpdateRequest.updatedAt );

        return profile;
    }

    @Override
    public ProfileUpdateRequestDto profileUpdateRequestToProfileUpdateRequestDto(ProfileUpdateRequest profileUpdateRequest) {
        if ( profileUpdateRequest == null ) {
            return null;
        }

        ProfileUpdateRequestDto profileUpdateRequestDto = new ProfileUpdateRequestDto();

        profileUpdateRequestDto.setOrganizationId( profileUpdateRequest.organizationId );
        profileUpdateRequestDto.setCompanyName( profileUpdateRequest.companyName );
        profileUpdateRequestDto.setLegalName( profileUpdateRequest.legalName );
        profileUpdateRequestDto.setTaxIdentifier( profileUpdateRequest.taxIdentifier );
        profileUpdateRequestDto.setEmail( profileUpdateRequest.email );
        profileUpdateRequestDto.setWebsite( profileUpdateRequest.website );
        profileUpdateRequestDto.setBusinessAddress( profileUpdateRequest.businessAddress );
        profileUpdateRequestDto.setLegalAddress( profileUpdateRequest.legalAddress );
        profileUpdateRequestDto.setStatus( profileUpdateRequest.status );
        profileUpdateRequestDto.setCreatedBy( profileUpdateRequest.createdBy );

        return profileUpdateRequestDto;
    }

    protected Address addressDtoToAddress(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        Address address = new Address();

        address.addressLine1 = addressDto.getAddressLine1();
        address.addressLine2 = addressDto.getAddressLine2();
        address.city = addressDto.getCity();
        address.zip = addressDto.getZip();
        address.country = addressDto.getCountry();

        return address;
    }

    protected AddressDto addressToAddressDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDto addressDto = new AddressDto();

        addressDto.setAddressLine1( address.addressLine1 );
        addressDto.setAddressLine2( address.addressLine2 );
        addressDto.setCity( address.city );
        addressDto.setZip( address.zip );
        addressDto.setCountry( address.country );

        return addressDto;
    }
}
