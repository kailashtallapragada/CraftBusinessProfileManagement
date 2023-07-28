package com.ktcraft.pvs.service.account.mapper;

import com.ktcraft.pvs.service.account.vo.AccountDto;
import com.ktcraft.pvs.service.model.Account;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(CustomAccountMapperImpl.class)
public interface AccountMapper {

    Account getAccountFromAccountDto(AccountDto accountDto);

    List<Account> getAccountFromAccountDto(List<AccountDto> accountDto);

    Account getAccountFromMap(Map accountMap);

    AccountDto getAccountDtoFromAccount(Account account);
}
