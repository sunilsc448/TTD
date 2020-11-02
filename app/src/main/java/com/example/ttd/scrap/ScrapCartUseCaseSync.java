package com.example.ttd.scrap;

import com.example.ttd.Login.NetworkErrorException;
import com.example.ttd.TTDemo.AddToCartHttpEndpointSync;
import com.example.ttd.TTDemo.CartItemScheme;

public class ScrapCartUseCaseSync {

    private AddToCartHttpEndpointSync mAddToCartHttpEndpointSync;

    public ScrapCartUseCaseSync(AddToCartHttpEndpointSync addToCartHttpEndpointSync) {
        mAddToCartHttpEndpointSync = addToCartHttpEndpointSync;
    }

    public UseCaseResult addToCartSync(String offerid, int amount) {
        AddToCartHttpEndpointSync.EndpointResult result;

        try {
            result = mAddToCartHttpEndpointSync.addToCartSync(new CartItemScheme(offerid, amount));
        } catch (NetworkErrorException e) {
           return UseCaseResult.NETWORKERROR;
        }

        if(result == AddToCartHttpEndpointSync.EndpointResult.SUCCESS){
            return UseCaseResult.SUCCESS;
        }else if(result == AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR ||
                    result == AddToCartHttpEndpointSync.EndpointResult.SERVER_ERROR){
            return UseCaseResult.FAILURE;
        }else{
            throw new RuntimeException();
        }
    }
    
    enum UseCaseResult{
        SUCCESS, FAILURE, NETWORKERROR;
    }
}
