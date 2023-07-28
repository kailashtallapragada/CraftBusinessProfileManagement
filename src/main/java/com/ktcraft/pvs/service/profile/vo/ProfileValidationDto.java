package com.ktcraft.pvs.service.profile.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ktcraft.pvs.service.profile.vo.constants.ProfileValidationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileValidationDto {

    @JsonProperty(value = "validation_status")
    public ProfileValidationStatus profileValidationStatus;
}
