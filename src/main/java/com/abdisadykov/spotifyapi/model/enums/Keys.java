package com.abdisadykov.spotifyapi.model.enums;

public enum Keys {

    CLIENT_ID("18b6aed40f7b4c328e876dd419a20310"),
    CLIENT_SECRET("32382d68eb744ea09224b9523977c80d");

    private final String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
