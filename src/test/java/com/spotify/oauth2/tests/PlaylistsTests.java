package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static com.spotify.oauth2.utils.FakersUtils.generateDescription;
import static com.spotify.oauth2.utils.FakersUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@Epic("Spotify Oauth2.0")
@Feature("Playlist Api")
public class PlaylistsTests extends BaseTest {
    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
        return Playlist.builder().name(name).description(description)._public(_public).build();
    }
    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }
    @Step
    public void assertError(Error responseErr, StatusCode statusCode ){
        assertThat(responseErr.getError().getStatus(),equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(), equalTo(statusCode.msg));
    }
    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode,equalTo(statusCode.code));
    }
    @Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name="allure",type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this is the description ...")
    @Test(description = "Should be able to create a playlist")
    public void ShouldBeAbleToCreateAPlayList() {
        Playlist requestPlaylist =  playlistBuilder(generateName(), generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
    }

    @Test
    public void ShouldBeAbleToGetPlayList() {
        Playlist requestPlaylist =  playlistBuilder("New Playlist", "New playlist description",true);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
    }

    @Test
    public void ShouldBeAbleToUpdatePlayList() {
        Playlist requestPlaylist =  playlistBuilder(generateName(), generateDescription(),false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatedPlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
    }
    @Story("Create a playlist story")
    @Test
    public void ShouldNotBeAbleToCreateAPlayListWithName() {
        Playlist requestPlaylist =  playlistBuilder("", generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);
        assertError(response.as(Error.class),StatusCode.CODE_400);
    }

    @Story("Create a playlist story")
    @Test
    public void ShouldNotBeAbleToCreateAPlayListWithExpiredToken() {
        String invalidToken = "12345";
        Playlist requestPlaylist =  playlistBuilder(generateName(), generateDescription(),false);
        Response response = PlaylistApi.post(invalidToken,requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_401);
        assertError(response.as(Error.class),StatusCode.CODE_401);
    }
}
