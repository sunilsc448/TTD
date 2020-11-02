package com.example.ttd.TTDemo;

import com.example.ttd.Login.NetworkErrorException;

public interface AddToCartHttpEndpointSync {

    EndpointResult addToCartSync(CartItemScheme cartItemScheme) throws NetworkErrorException;

    enum EndpointResult {
        SUCCESS,
        AUTH_ERROR,
        SERVER_ERROR
    }
}
