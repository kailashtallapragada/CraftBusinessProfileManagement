package com.ktcraft.pvs.service.account.mapper;

import com.ktcraft.pvs.service.model.Account;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public abstract class CustomAccountMapperImpl implements AccountMapper {

    @Override
    public Account getAccountFromMap(Map accountMap) {
        final Account account = new Account();
        if (accountMap != null) {
            if (accountMap.get("organizationId") instanceof Number) {
                account.setOrganizationId(((Number) accountMap.get("organizationId")).longValue());
            }
            if (accountMap.get("accountId") instanceof Number) {
                account.setAccountId(((Number) accountMap.get("accountId")).longValue());
            }
            if (accountMap.get("productId") instanceof Number) {
                account.setProductId(((Number) accountMap.get("productId")).intValue());
            }
        }
        return account;
    }
}
