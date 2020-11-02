package com.example.ttd.Login;

import com.example.ttd.Login.abstracts.AuthTokenCache;
import com.example.ttd.Login.abstracts.EventBusPoster;
import com.example.ttd.Login.abstracts.LoginHttpEndpointSync;
import com.example.ttd.TTDemo.AddToCartHttpEndpointSync;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginUseCaseSyncTest {

    private LoginUseCaseSync SUT;

    private LoginHttpEndpointSync mLoginHttpEndpointSyncMock;
    private AuthTokenCache mAuthTokenCacheTDMock;
    private EventBusPoster mEventBusPosterTDMock;

    private static final String USERNAME = "userName";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "auth_token";

    @Before
    public void setUp() throws Exception {
        mLoginHttpEndpointSyncMock = mock(LoginHttpEndpointSync.class);
        mAuthTokenCacheTDMock = mock(AuthTokenCache.class);
        mEventBusPosterTDMock = mock(EventBusPoster.class);
        SUT = new LoginUseCaseSync(mLoginHttpEndpointSyncMock, mAuthTokenCacheTDMock, mEventBusPosterTDMock);
        success();
    }

    private void success() throws NetworkErrorException {
        when(mLoginHttpEndpointSyncMock.loginSync(Matchers.any(String.class),
                Matchers.any(String.class))).
                thenReturn(new LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SUCCESS, AUTH_TOKEN));
    }

    //username and password are passed to end point
    @Test
    public void loginSync_paramsPassed_correct() throws NetworkErrorException {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USERNAME, PASSWORD);
        verify(mLoginHttpEndpointSyncMock).loginSync(captor.capture(), captor.capture());
        List<String> values = captor.getAllValues();
        assertThat(values.get(0), is(USERNAME));
        assertThat(values.get(1), is(PASSWORD));
    }
//    //login success - auth token is saved
//    @Test
//    public void loginSync_success_authTokenSaved(){
//        SUT.loginSync(USERNAME, PASSWORD);
//        assertThat(mAuthTokenCacheTDMock.mAuthToken, is(AUTH_TOKEN));
//    }
//    //login failed - auth token is unchanged
//    @Test
//    public void loginSync_fail_authTokenUnchanged(){
//        mLoginHttpEndpointSyncMock.mIsAuthError = true;
//        SUT.loginSync(USERNAME, PASSWORD);
//        assertThat(mAuthTokenCacheTDMock.mAuthToken, is(""));
//    }
}