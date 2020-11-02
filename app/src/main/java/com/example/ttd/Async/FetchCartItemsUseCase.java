package com.example.ttd.Async;

import java.util.ArrayList;
import java.util.List;

public class FetchCartItemsUseCase {

    public interface Listener {
        void onCartItemsFetched(List<CartItemAsync> capture);
        void onFetchCartItemsFailed();
    }

    private final List<Listener> mListeners = new ArrayList<>();
    private final GetCartItemsHttpEndpoint mGetCartItemsHttpEndpoint;

    public FetchCartItemsUseCase(GetCartItemsHttpEndpoint getCartItemsHttpEndpoint) {
        mGetCartItemsHttpEndpoint = getCartItemsHttpEndpoint;
    }

    public void fetchCartItemsAndNotify(int limit) {
        mGetCartItemsHttpEndpoint.getCartItems(limit, new GetCartItemsHttpEndpoint.Callback() {

            @Override
            public void onGetCartItemsSucceeded(List<CartItemSchemaAsync> cartItems) {
                notifySucceeded(cartItems);
            }

            @Override
            public void onGetCartItemsFailed(GetCartItemsHttpEndpoint.FailReason failReason) {
                switch (failReason) {
                    case GENERAL_ERROR:
                    case NETWORK_ERROR:
                        notifyFailed();
                        break;
                }
            }
        });
    }

    private void notifySucceeded(List<CartItemSchemaAsync> cartItems) {
        for (Listener listener : mListeners) {
            listener.onCartItemsFetched(cartItemsFromSchemas(cartItems));
        }
    }

    private void notifyFailed() {
        for (Listener listener : mListeners) {
            listener.onFetchCartItemsFailed();
        }
    }

    private List<CartItemAsync> cartItemsFromSchemas(List<CartItemSchemaAsync> cartItemSchemaAsyncs) {
        List<CartItemAsync> cartItems = new ArrayList<>();
        for (CartItemSchemaAsync schema : cartItemSchemaAsyncs) {
            cartItems.add(new CartItemAsync(
                    schema.getId(),
                    schema.getTitle(),
                    schema.getDescription(),
                    schema.getPrice()
            ));
        }
        return cartItems;
    }

    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

}
