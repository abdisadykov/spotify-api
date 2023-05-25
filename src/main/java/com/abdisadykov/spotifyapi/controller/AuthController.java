package com.abdisadykov.spotifyapi.controller;

import com.abdisadykov.spotifyapi.model.enums.AuthorizationScope;
import com.abdisadykov.spotifyapi.model.enums.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final URI redirectUri = SpotifyHttpManager.makeUri("localhost:8080/api/get-user-code/");
    private String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(Keys.CLIENT_ID.getKey())
            .setClientSecret(Keys.CLIENT_SECRET.getKey())
            .setRedirectUri(redirectUri)
            .build();

    @GetMapping("/login")
    public String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope(
                        AuthorizationScope.USER_READ_PRIVATE.getScope() +
                        AuthorizationScope.USER_READ_EMAIL.getScope() +
                        AuthorizationScope.USER_TOP_READ.getScope()
                        )
                .show_dialog(true)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    @GetMapping("/get-user-code")
    public String getSpotifyUserCode(
            @RequestParam String userCode,
            HttpServletResponse response) throws IOException {
        code = userCode;

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        final AuthorizationCodeCredentials credentials;

        try {
            credentials = authorizationCodeRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getAccessToken());
        System.out.println("Expires in: " + credentials.getExpiresIn());

        response.sendRedirect("localhost:8080/top-artists");

        return spotifyApi.getAccessToken();
    }
}
