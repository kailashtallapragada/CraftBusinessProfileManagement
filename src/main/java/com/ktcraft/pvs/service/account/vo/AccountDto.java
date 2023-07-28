package com.ktcraft.pvs.service.account.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @JsonProperty("organization_id")
    public Long organizationId;

    @JsonProperty("account_id")
    public Long accountId;

    @JsonProperty("product_id")
    public Integer productId;
}
