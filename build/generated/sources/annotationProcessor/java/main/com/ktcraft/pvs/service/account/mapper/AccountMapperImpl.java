package com.ktcraft.pvs.service.account.mapper;

import com.ktcraft.pvs.service.account.vo.AccountDto;
import com.ktcraft.pvs.service.model.Account;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-27T01:10:47+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.12 (Amazon.com Inc.)"
)
@Component
@Primary
public class AccountMapperImpl extends CustomAccountMapperImpl {

    @Autowired
    @Qualifier("delegate")
    private AccountMapper delegate;

    @Override
    public Account getAccountFromAccountDto(AccountDto accountDto)  {
        return delegate.getAccountFromAccountDto( accountDto );
    }

    @Override
    public List<Account> getAccountFromAccountDto(List<AccountDto> accountDto)  {
        return delegate.getAccountFromAccountDto( accountDto );
    }

    @Override
    public AccountDto getAccountDtoFromAccount(Account account)  {
        return delegate.getAccountDtoFromAccount( account );
    }
}
