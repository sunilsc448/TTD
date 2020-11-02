package com.example.ttd.RefLog;

import com.example.ttd.Login.LoggedInEvent;
import com.example.ttd.Login.LoginUseCaseSync;
import com.example.ttd.Login.NetworkErrorException;
import com.example.ttd.Login.abstracts.AuthTokenCache;
import com.example.ttd.Login.abstracts.EventBusPoster;
import com.example.ttd.Login.abstracts.LoginHttpEndpointSync;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LoginUseCaseSyncTest {
    private LoginUseCaseSync SUT;
    private LoginHttpEndpointSyncTD mLoginHttpEndpointSyncTD;
    private AuthTokenCacheTD mAuthTokenCacheTD;
    private EventBusPosterTD mEventBusPosterTD;
    private static final String USERNAME = "userName";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "authToken";

    @Before
    public void setup() {
        mLoginHttpEndpointSyncTD = new LoginHttpEndpointSyncTD();
        mAuthTokenCacheTD = new AuthTokenCacheTD();
        mEventBusPosterTD = new EventBusPosterTD();
        SUT = new LoginUseCaseSync(mLoginHttpEndpointSyncTD,mAuthTokenCacheTD, mEventBusPosterTD);
    }

    //1. username and password will be passed to endpoint
    @Test
    public void loginSync_success_userNamePasswordPaasedToEndPoint() throws Exception{
        SUT.loginSync(USERNAME, PASSWORD);
        Assert.assertThat(mLoginHttpEndpointSyncTD.mUserName, is(USERNAME));
        Assert.assertThat(mLoginHttpEndpointSyncTD.mPassword, is(PASSWORD));

    }
    //2. if login succeeds - auth token must to be cached
    @Test
    public void loginSync_success_authTokenCached() throws Exception{
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTD.getAuthToken(), is(AUTH_TOKEN));
    }
    //3. if login fails - auth token is unchanged
    @Test
    public void loginSync_failure_serverError() throws Exception{
        mLoginHttpEndpointSyncTD.mIsServerError = true;
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTD.getAuthToken(), is(""));
    }

    @Test
    public void loginSync_failure_generalError() throws Exception{
        mLoginHttpEndpointSyncTD.mIsAuthError = true;
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTD.getAuthToken(), is(""));
    }


    //4. if login succeeds - login event posted
    @Test
    public void loginSync_success_logEventPostedToBus(){
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTD.mEvent, is(instanceOf(LoggedInEvent.class)));
    }
    //5. if login fails - no login event posted
    @Test
    public void loginSync_failure_noEventPosted(){
        mLoginHttpEndpointSyncTD.mIsServerError = true;
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTD.mEventCount, is(0));
    }
    //6. if login succeeds - success returned
    @Test
    public void loginSync_success_successReturned() throws Exception{
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.SUCCESS));
    }
    //7. if login fails - fail returned
    @Test
    public void loginSync_failure_failReturned() throws Exception{
        mLoginHttpEndpointSyncTD.mIsServerError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }
    //8. network error - network error returned
    @Test
    public void loginSync_noNetwork_networkError() throws Exception{
        mLoginHttpEndpointSyncTD.mIsNetworkError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    private static class LoginHttpEndpointSyncTD implements LoginHttpEndpointSync{
        public String mUserName;
        public String mPassword;

        public boolean mIsServerError;
        public boolean mIsAuthError;
        public boolean mIsNetworkError;

        @Override
        public EndpointResult loginSync(String username, String password) throws NetworkErrorException {
            mUserName = username;
            mPassword = password;
            if(mIsServerError){
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "");
            }else if(mIsAuthError){
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "");
            }else if(mIsNetworkError){
                throw new NetworkErrorException();
            }
            else {
                return new EndpointResult(EndpointResultStatus.SUCCESS, AUTH_TOKEN);
            }
        }
    }

    //it is fake because it is working implementation but instead caching the token into persistent memory it stores in memeory
    private static class AuthTokenCacheTD implements AuthTokenCache{
        String mAuthToken = "";
        @Override
        public void cacheAuthToken(String authToken) {
            mAuthToken = authToken;
        }

        @Override
        public String getAuthToken() {
            return mAuthToken;
        }
    }

    private static class EventBusPosterTD implements EventBusPoster{

        public Object mEvent;
        public int mEventCount;

        @Override
        public void postEvent(Object event) {
            mEventCount++;
            mEvent = event;
        }
    }
}