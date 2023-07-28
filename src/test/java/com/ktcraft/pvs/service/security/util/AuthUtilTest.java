package com.ktcraft.pvs.service.security.util;

import com.ktcraft.pvs.service.model.Account;
import com.ktcraft.pvs.service.security.AccountAuthentication;
import com.ktcraft.pvs.service.security.util.AuthUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthUtilTest {

    @Autowired
    AuthUtil authUtil;

    @Test
    void getAccountAuthentication_withoutAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertNull(authUtil.getAccountAuthentication());
    }

    @Test
    void getAccountAuthentication_withoutAccountAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(Mockito.mock(Authentication.class));
        assertNull(authUtil.getAccountAuthentication());
    }

    @Test
    void getAccountAuthentication_withAccountAuthentication() {
        final AccountAuthentication mock = Mockito.mock(AccountAuthentication.class);
        SecurityContextHolder.getContext().setAuthentication(mock);
        assertEquals(mock, authUtil.getAccountAuthentication());
    }

    @Test
    void getCurrentAccount_withAccountNull() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertNull(authUtil.getCurrentAccount());
    }

    @Test
    void getCurrentAccount_withoutOrgId() {
        final AccountAuthentication mock = Mockito.mock(AccountAuthentication.class);
        Mockito.when(mock.getOrganizationId()).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(mock);
        assertNull(authUtil.getCurrentAccount());
    }

    @Test
    void getCurrentAccount_withoutAccountId() {
        final AccountAuthentication mock = Mockito.mock(AccountAuthentication.class);
        Mockito.when(mock.getOrganizationId()).thenReturn(10l);
        Mockito.when(mock.getAccountId()).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(mock);
        assertNull(authUtil.getCurrentAccount());
    }

    @Test
    void getCurrentAccount_withoutProductId() {
        final AccountAuthentication mock = Mockito.mock(AccountAuthentication.class);
        Mockito.when(mock.getOrganizationId()).thenReturn(10l);
        Mockito.when(mock.getAccountId()).thenReturn(10l);
        Mockito.when(mock.getProductId()).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(mock);
        assertNull(authUtil.getCurrentAccount());
    }

    @Test
    void getCurrentAccount_withValidAccountAuthentication() {
        final AccountAuthentication mock = Mockito.mock(AccountAuthentication.class);
        Mockito.when(mock.getOrganizationId()).thenReturn(10l);
        Mockito.when(mock.getAccountId()).thenReturn(20l);
        Mockito.when(mock.getProductId()).thenReturn(30);
        SecurityContextHolder.getContext().setAuthentication(mock);
        final Account currentAccount = authUtil.getCurrentAccount();
        assertEquals(10l, currentAccount.getOrganizationId());
        assertEquals(20l, currentAccount.getAccountId());
        assertEquals(30, currentAccount.getProductId());
    }
}