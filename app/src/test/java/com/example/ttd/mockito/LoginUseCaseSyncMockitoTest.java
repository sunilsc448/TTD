package com.example.ttd.mockito;

import com.example.ttd.Login.LoginUseCaseSync;
import com.example.ttd.Login.abstracts.AuthTokenCache;
import com.example.ttd.Login.abstracts.EventBusPoster;
import com.example.ttd.Login.abstracts.LoginHttpEndpointSync;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginUseCaseSyncMockitoTest {
    private LoginUseCaseSync SUT;

    @Mock private LoginHttpEndpointSync mLoginHttpEndpointSyncMock;
    @Mock private AuthTokenCache mAuthTokenCacheMock;
    @Mock private EventBusPoster mEventBusPosterMock;

    private static final String USERNAME = "userName";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "authToken";

    @Before
    public void setup() throws Exception{
//        mLoginHttpEndpointSyncMock = mock(LoginHttpEndpointSync.class);
//        mAuthTokenCacheMock = mock(AuthTokenCache.class);
//        mEventBusPosterMock = mock(EventBusPoster.class);
        SUT = new LoginUseCaseSync(mLoginHttpEndpointSyncMock,mAuthTokenCacheMock, mEventBusPosterMock);
        success();
    }

    //1. username and password will be passed to endpoint
    @Test
    public void loginSync_success_userNamePasswordPaasedToEndPoint() throws Exception{
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USERNAME, PASSWORD);
        verify(mLoginHttpEndpointSyncMock, times(1)).loginSync(argumentCaptor.capture(), argumentCaptor.capture());
        List<String> captures = argumentCaptor.getAllValues();
        assertThat(captures.get(0), is(USERNAME));
        assertThat(captures.get(1), is(PASSWORD));
    }

    //2. if login succeeds - auth token must to be cached
    @Test
    public void loginSync_success_authTokenCached() throws Exception{
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USERNAME, PASSWORD);
        verify(mAuthTokenCacheMock, times(1)).cacheAuthToken(argumentCaptor.capture());
        List<String> captures = argumentCaptor.getAllValues();
        assertThat(captures.get(0), is(AUTH_TOKEN));
    }

    //3. if login fails - auth token is unchanged
    @Test
    public void loginSync_failure_serverError() throws Exception{
        AuthError();
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mAuthTokenCacheMock);
    }


    private void success() throws Exception {
        when(mLoginHttpEndpointSyncMock.loginSync(Matchers.any(String.class), Matchers.any(String.class))).
                thenReturn(new LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SUCCESS, AUTH_TOKEN));
    }

    private void AuthError() throws Exception{
        when(mLoginHttpEndpointSyncMock.loginSync(Matchers.any(String.class), Matchers.any(String.class))).
                thenReturn(new LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, ""));
    }

}