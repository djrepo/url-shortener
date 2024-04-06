package com.shorturl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LongUrlWrapper {

    @JsonProperty("long_url")
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

}
