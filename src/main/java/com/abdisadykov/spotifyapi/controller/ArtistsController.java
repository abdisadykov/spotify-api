package com.abdisadykov.spotifyapi.controller;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

import java.io.IOException;
import java.util.List;

@RestController
public class ArtistsController {

    private SpotifyApi spotifyApi;

    @GetMapping("/top-artists")
    public List<Artist> getTopArtists() throws IOException, ParseException, SpotifyWebApiException {
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                .time_range("medium-term")
                .limit(10)
                .offset(5)
                .build();

        final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
        return List.of(artistPaging.getItems());
    }

}
