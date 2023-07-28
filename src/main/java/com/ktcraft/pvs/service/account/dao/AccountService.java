package com.ktcraft.pvs.service.account.dao;

import com.ktcraft.pvs.service.account.vo.AccountDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAccountsForOrganizationId(Long organizationId, Long accountId);
}
