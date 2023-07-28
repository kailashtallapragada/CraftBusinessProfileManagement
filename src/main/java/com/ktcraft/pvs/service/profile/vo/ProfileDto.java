package com.ktcraft.pvs.service.profile.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class ProfileDto {

   @JsonProperty(value = "organization_id", access = JsonProperty.Access.READ_ONLY)
   public Long organizationId;

   @JsonProperty(value = "company_name")
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
   public AddressDto businessAddress;

   @JsonProperty("legal_address")
   public AddressDto legalAddress;

   @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
   public Instant createdAt;

   @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
   public Instant updatedAt;
}
