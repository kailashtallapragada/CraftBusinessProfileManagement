package com.ktcraft.pvs.service.account.mapper;

import com.ktcraft.pvs.service.account.vo.AccountDto;
import com.ktcraft.pvs.service.model.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-27T01:10:46+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.12 (Amazon.com Inc.)"
)
@Component
@Qualifier("delegate")
public class AccountMapperImpl_ implements AccountMapper {

    @Override
    public Account getAccountFromAccountDto(AccountDto accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        Account account = new Account();

        account.organizationId = accountDto.organizationId;
        account.accountId = accountDto.accountId;
        account.productId = accountDto.productId;

        return account;
    }

    @Override
    public List<Account> getAccountFromAccountDto(List<AccountDto> accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        List<Account> list = new ArrayList<Account>( accountDto.size() );
        for ( AccountDto accountDto1 : accountDto ) {
            list.add( getAccountFromAccountDto( accountDto1 ) );
        }

        return list;
    }

    @Override
    public Account getAccountFromMap(Map accountMap) {
        if ( accountMap == null ) {
            return null;
        }

        Account account = new Account();

        return account;
    }

    @Override
    public AccountDto getAccountDtoFromAccount(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.organizationId = account.organizationId;
        accountDto.accountId = account.accountId;
        accountDto.productId = account.productId;

        return accountDto;
    }
}
