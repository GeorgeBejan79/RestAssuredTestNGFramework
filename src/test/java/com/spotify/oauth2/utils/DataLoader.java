package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {
    public  final Properties properties;
    public static DataLoader dataLoader;

    private DataLoader(){
        properties = PropertyUtils.propertiesLoader("src/test/resources/data.properties");
    }
    public static DataLoader getInstance(){
        if(dataLoader==null){
            dataLoader= new DataLoader();
        }
        return  dataLoader;
    }
    public String getGetPlaylistId(){
        String prop = properties.getProperty("get_playlist_id");
        if(prop != null) return prop;
        else throw new RuntimeException("property get_playlist_id is not specified in config.properties file ");
    }
    public String getUpdatedPlaylistId(){
        String prop = properties.getProperty("update_playlist_id");
        if(prop != null) return prop;
        else throw new RuntimeException("property client_secret is not specified in config.properties file ");
    }
}
