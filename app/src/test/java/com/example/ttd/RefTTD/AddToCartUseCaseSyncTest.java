package com.example.ttd.RefTTD;

import com.example.ttd.Login.LoginUseCaseSync;
import com.example.ttd.Login.NetworkErrorException;
import com.example.ttd.TTDemo.AddToCartHttpEndpointSync;
import com.example.ttd.TTDemo.CartItemScheme;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentCaptor.*;

@RunWith(MockitoJUnitRunner.class)
public class AddToCartUseCaseSyncTest {
    public static final String OFFER_ID = "offerId";
    public static final int AMOUNT = 4;
    @Mock
    AddToCartHttpEndpointSync mAddToCartHttpEndpointSyncMock;

    @Before
    public void setup() throws Exception {
//        SUT = new AddToCartUseCaseSync(mAddToCartHttpEndpointSyncMock);
//        success();
    }

    @Test
    public void addToCartSync_correctParametersPassedToEndpoint() throws Exception {
        // Arrange
        ArgumentCaptor<CartItemScheme> ac = ArgumentCaptor.forClass(CartItemScheme.class);
        // Act
//        SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        verify(mAddToCartHttpEndpointSyncMock).addToCartSync(ac.capture());
        assertThat(ac.getValue().getOfferId(), is(OFFER_ID));
        assertThat(ac.getValue().getAmount(), is(AMOUNT));
    }

    @Test
    public void addToCartSync_success_successReturned() throws Exception {
        // Arrange
        // Act
//        LoginUseCaseSync.UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
//        assertThat(result, is(LoginUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void addToCartSync_authError_failureReturned() throws Exception {
        // Arrange
//        authError();
        // Act
//        LoginUseCaseSync.UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
//        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void addToCartSync_generalError_failureReturned() throws Exception {
        // Arrange
//        generalError();
        // Act
//        LoginUseCaseSync.UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
//        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void addToCartSync_networkError_networkErrorReturned() throws Exception {
        // Arrange
//        networkError();
        // Act
//        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
//        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    // region helper methods -----------------------------------------------------------------------

//    private void success() throws NetworkErrorException {
//        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
//                .thenReturn(SUCCESS);
//    }
//
//    private void authError() throws NetworkErrorException {
//        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
//                .thenReturn(AUTH_ERROR);
//    }
//
//    private void generalError() throws NetworkErrorException {
//        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
//                .thenReturn(GENERAL_ERROR);
//    }
//
//    private void networkError() throws NetworkErrorException {
//        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
//                .thenThrow(new NetworkErrorException());
//    }
}