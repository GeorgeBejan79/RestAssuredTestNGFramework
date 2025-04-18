package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

public  class TokenManager {
    private static String access_token;
    private static Instant expiry_time;
//we used synchronized to make sure that not multiple threads are calling the token and might end up in race condition
    public synchronized static String getToken() {
        try {
            if (access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("Renewing token");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiry_durationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiry_durationInSeconds - 300);
            } else {
                System.out.println("Token is good to use...");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Abort failed to get token ");
        }
        return access_token;
    }

    private static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());

        Response response = RestResource.postAccount(formParams);
        if (response.statusCode() != 200) {
            throw new RuntimeException("ABORT!!! renew Token");
        }
        return response;
    }
}
