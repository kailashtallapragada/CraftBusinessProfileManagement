package com.ktcraft.pvs.service.account.dao;

import com.ktcraft.pvs.service.account.vo.AccountDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private static Integer numberOfProducts = 4;
    private static Random random = new Random();

    private static Long getMSD(Long n) {
        while (n >= 10) {
            n /= 10;
        }
        return n;
    }

    @Override
    public List<AccountDto> getAccountsForOrganizationId(Long organizationId, Long accountId) {
        final Integer msd = Integer.parseInt(getMSD(accountId).toString());
        List<AccountDto> accounts = new ArrayList<>();
        for (Integer i = 1; i <= numberOfProducts; i++) {
            if (!i.equals(msd) && random.nextBoolean()) {
                accounts.add(new AccountDto(organizationId, accountId, i));
            }
        }
        accounts.add(new AccountDto(organizationId, accountId.longValue(), msd));
        return accounts;
    }
}
