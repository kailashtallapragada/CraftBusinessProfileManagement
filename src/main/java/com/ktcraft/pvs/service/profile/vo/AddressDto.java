package com.ktcraft.pvs.service.profile.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressDto {

    @JsonProperty("address_line_1")
    public String addressLine1;

    @JsonProperty("address_line_2")
    public String addressLine2;

    @JsonProperty("city")
    public String city;

    @JsonProperty("zip")
    public String zip;

    @JsonProperty("country")
    public String country;
}
