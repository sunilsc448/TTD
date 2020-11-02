package com.example.ttd.Async;

import java.util.List;

public interface GetCartItemsHttpEndpoint {

    enum FailReason {
        GENERAL_ERROR,
        NETWORK_ERROR
    }

    interface Callback {
        void onGetCartItemsSucceeded(List<CartItemSchemaAsync> cartItems);
        void onGetCartItemsFailed(FailReason failReason);
    }

    /**
     * @param limit max amount of cart items to fetch
     * @param callback object to be notified when the request completes
     */
    public void getCartItems(int limit, Callback callback);
}
