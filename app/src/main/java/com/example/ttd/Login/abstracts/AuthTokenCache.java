package com.example.ttd.Login.abstracts;

public interface AuthTokenCache {

    void cacheAuthToken(String authToken);

    String getAuthToken();
}
