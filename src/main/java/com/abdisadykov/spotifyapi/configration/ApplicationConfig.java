package com.abdisadykov.spotifyapi.configration;

import com.abdisadykov.spotifyapi.model.enums.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@Configuration
public class ApplicationConfig {

    private static final URI redirectUri = SpotifyHttpManager.makeUri("localhost:8080/api/get-user-code/");

    @Bean
    public SpotifyApi spotifyApi() {
        return new SpotifyApi.Builder()
                .setClientId(Keys.CLIENT_ID.getKey())
                .setClientSecret(Keys.CLIENT_SECRET.getKey())
                .setRedirectUri(redirectUri)
                .build();
    }

}
