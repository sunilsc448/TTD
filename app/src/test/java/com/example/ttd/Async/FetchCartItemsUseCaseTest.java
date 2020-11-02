package com.example.ttd.Async;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FetchCartItemsUseCaseTest {

    // region constants ----------------------------------------------------------------------------
    public static final int LIMIT = 10;
    public static final int PRICE = 5;
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    public static final String ID = "id";
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    @Mock GetCartItemsHttpEndpoint mGetCartItemsHttpEndpointMock;
    @Mock FetchCartItemsUseCase.Listener mListenerMock1;
    @Mock FetchCartItemsUseCase.Listener mListenerMock2;

    @Captor ArgumentCaptor<List<CartItemAsync>> mAcListCartItem;
    // endregion helper fields ---------------------------------------------------------------------

    FetchCartItemsUseCase SUT;

    @Before
    public void setup() throws Exception {
        SUT = new FetchCartItemsUseCase(mGetCartItemsHttpEndpointMock);
        success();
    }

    private List<CartItemSchemaAsync> getCartItemSchemes() {
        List<CartItemSchemaAsync> schemas = new ArrayList<>();
        schemas.add(new CartItemSchemaAsync(ID, TITLE, DESCRIPTION, PRICE));
        return schemas;
    }

    @Test
    public void fetchCartItems_correctLimitPassedToEndpoint() throws Exception {
        // Arrange
        ArgumentCaptor<Integer> acInt = ArgumentCaptor.forClass(Integer.class);
        // Act
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mGetCartItemsHttpEndpointMock).getCartItems(acInt.capture(), any(GetCartItemsHttpEndpoint.Callback.class));
        assertThat(acInt.getValue(), is(LIMIT));
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

    @Test
    public void fetchCartItems_generalError_observersNotifiedOfFailure() throws Exception {
        // Arrange
        generaError();
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
        networkError();
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onFetchCartItemsFailed();
        verify(mListenerMock2).onFetchCartItemsFailed();
    }

    // region helper methods -----------------------------------------------------------------------

    private List<CartItemAsync> getCartItems() {
        List<CartItemAsync> cartItems = new ArrayList<>();
        cartItems.add(new CartItemAsync(ID, TITLE, DESCRIPTION, PRICE));
        return cartItems;
    }

    private void success() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                GetCartItemsHttpEndpoint.Callback callback = (GetCartItemsHttpEndpoint.Callback) args[1];
                callback.onGetCartItemsSucceeded(getCartItemSchemes());
                return null;
            }
        }).when(mGetCartItemsHttpEndpointMock).getCartItems(anyInt(), any(GetCartItemsHttpEndpoint.Callback.class));
    }

    private void networkError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                GetCartItemsHttpEndpoint.Callback callback = (GetCartItemsHttpEndpoint.Callback) args[1];
                callback.onGetCartItemsFailed(GetCartItemsHttpEndpoint.FailReason.NETWORK_ERROR);
                return null;
            }
        }).when(mGetCartItemsHttpEndpointMock).getCartItems(anyInt(), any(GetCartItemsHttpEndpoint.Callback.class));
    }

    private void generaError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                GetCartItemsHttpEndpoint.Callback callback = (GetCartItemsHttpEndpoint.Callback) args[1];
                callback.onGetCartItemsFailed(GetCartItemsHttpEndpoint.FailReason.GENERAL_ERROR);
                return null;
            }
        }).when(mGetCartItemsHttpEndpointMock).getCartItems(anyInt(), any(GetCartItemsHttpEndpoint.Callback.class));
    }

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    // endregion helper classes --------------------------------------------------------------------

}