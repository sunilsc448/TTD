package com.example.ttd.Async;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FetchCartItemsManualTestDoublesUseCaseTest {

    public static final int LIMIT = 10;
    public static final int PRICE = 5;
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    public static final String ID = "id";

    GetCartItemsHttpEndpointTd mGetCartItemsHttpEndpointTd;
    @Mock FetchCartItemsUseCase.Listener mListenerMock1;
    @Mock FetchCartItemsUseCase.Listener mListenerMock2;

    @Captor ArgumentCaptor<List<CartItemAsync>> mAcListCartItem;

    FetchCartItemsUseCase SUT;

    @Before
    public void setup() throws Exception {
        mGetCartItemsHttpEndpointTd = new GetCartItemsHttpEndpointTd();
        SUT = new FetchCartItemsUseCase(mGetCartItemsHttpEndpointTd);
    }

    @Test
    public void fetchCartItems_correctLimitPassedToEndpoint() throws Exception {
        // Arrange
        // Act
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        assertThat(mGetCartItemsHttpEndpointTd.mInvocationCount, is(1));
        assertThat(mGetCartItemsHttpEndpointTd.mLastLimit, is(LIMIT));
    }

    @Test
    public void fetchCartItems_success_observersNotifiedWithCorrectData() throws Exception {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onCartItemsFetched(mAcListCartItem.capture());
        verify(mListenerMock2).onCartItemsFetched(mAcListCartItem.capture());
        List<List<CartItemAsync>> captures = mAcListCartItem.getAllValues();
        List<CartItemAsync> capture1 = captures.get(0);
        List<CartItemAsync> capture2 = captures.get(1);
        assertThat(capture1, is(getCartItems()));
        assertThat(capture2, is(getCartItems()));
    }

    @Test
    public void fetchCartItems_generalError_observersNotifiedOfFailure() throws Exception {
        // Arrange
        mGetCartItemsHttpEndpointTd.mGeneralError = true;
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onFetchCartItemsFailed();
        verify(mListenerMock2).onFetchCartItemsFailed();
    }

    @Test
    public void fetchCartItems_networkError_observersNotifiedOfFailure() throws Exception {
        // Arrange
        mGetCartItemsHttpEndpointTd.mNetworkError = true;
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onFetchCartItemsFailed();
        verify(mListenerMock2).onFetchCartItemsFailed();
    }

    @Test
    public void fetchCartItems_success_unsubscribedObserversNotNotified() throws Exception {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.unregisterListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onCartItemsFetched(any(List.class));
        verifyNoMoreInteractions(mListenerMock2);
    }

    private List<CartItemAsync> getCartItems() {
        List<CartItemAsync> cartItems = new ArrayList<>();
        cartItems.add(new CartItemAsync(ID, TITLE, DESCRIPTION, PRICE));
        return cartItems;
    }

    //Mock and Stub
    private class GetCartItemsHttpEndpointTd implements GetCartItemsHttpEndpoint {

        private int mInvocationCount;
        private int mLastLimit;

        private boolean mNetworkError;
        private boolean mGeneralError;

        @Override
        public void getCartItems(int limit, Callback callback) {
            mInvocationCount++;
            mLastLimit = limit;
            if (mNetworkError) {
                callback.onGetCartItemsFailed(FailReason.NETWORK_ERROR);
            } else if (mGeneralError) {
                callback.onGetCartItemsFailed(FailReason.GENERAL_ERROR);
            } else {
                callback.onGetCartItemsSucceeded(getCartItemSchemes());
            }
        }
    }

    private List<CartItemSchemaAsync> getCartItemSchemes() {
        List<CartItemSchemaAsync> schemas = new ArrayList<>();
        schemas.add(new CartItemSchemaAsync(ID, TITLE, DESCRIPTION, PRICE));
        return schemas;
    }
}