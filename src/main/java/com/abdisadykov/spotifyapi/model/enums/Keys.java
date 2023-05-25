package com.abdisadykov.spotifyapi.model.enums;

public enum Keys {

    CLIENT_ID(System.getenv("clientId")),
    CLIENT_SECRET(System.getenv("clientSecret"));

    private final String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
