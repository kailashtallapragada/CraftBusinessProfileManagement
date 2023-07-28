package com.ktcraft.pvs.service.security.util;

import com.ktcraft.pvs.service.model.Account;
import com.ktcraft.pvs.service.security.AccountAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUtil {

    public AccountAuthentication getAccountAuthentication() {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication() instanceof AccountAuthentication) {
            return (AccountAuthentication) SecurityContextHolder.getContext().getAuthentication();
        }
        return null;
    }

    public Account getCurrentAccount() {
        final AccountAuthentication accountAuthentication = getAccountAuthentication();
        if (accountAuthentication != null
                && accountAuthentication.getOrganizationId() != null
                && accountAuthentication.getAccountId() != null
                && accountAuthentication.getProductId() != null) {
            return new Account(accountAuthentication.getOrganizationId(), accountAuthentication.getAccountId(),
                    accountAuthentication.getProductId());
        }
        return null;
    }
}
