package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;


public class PlaylistApi {
    //static String access_token = "BQCmRCTvhuCkB5poOqFymb6eyC687xkll-WXW9xjqnUGEQU23Up5zw_dgPtH9gIcVhODxZvYrz-G6r_7cTB5pIfbAgO7F--NVNT7X7nYUfDDY5r2gYSVdhu75R98obFI16cTnGlCB75gRXBJGLHsV6fLcfYPXGn-7jvutBbip7L7Fuw4bXPMM-q3a2ieBDjJ7bJp95o8_lTLalJaclXJ2TJiBcQMXw3Wjfn4pKPokDiAOEn162lij_x_NdxjEmQD09agh_43aCNqJovZ0d4bw0o83wfvFRqCjaE_xHgNxf9_OyOi";
    @Step
    public static Response post(Playlist requestPlayList) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS,
                getToken(), requestPlayList);
    }

    public static Response post(String token, Playlist requestPlayList) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() +  PLAYLISTS,
                token, requestPlayList);
    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        return RestResource.update(PLAYLISTS + "/" + playlistId, getToken(), requestPlaylist);
    }
}
