package com.ktcraft.pvs.service.profile.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ktcraft.pvs.service.model.Account;
import com.ktcraft.pvs.service.model.Address;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import lombok.Data;

@Data
public class ProfileUpdateRequestDto {

    @JsonProperty("organization_id")
    public Long organizationId;

    @JsonProperty("company_name")
    public String companyName;

    @JsonProperty("legal_name")
    public String legalName;

    @JsonProperty("tax_identifier")
    public String taxIdentifier;

    @JsonProperty("email")
    public String email;

    @JsonProperty("website")
    public String website;

    @JsonProperty("business_address")
    public Address businessAddress;

    @JsonProperty("legal_address")
    public Address legalAddress;

    @JsonProperty("status")
    public ProfileValidationStatus status;

    @JsonProperty("my_response")
    public ProfileValidationStatus myStatus;

    @JsonProperty("created_by")
    public Account createdBy;
}
