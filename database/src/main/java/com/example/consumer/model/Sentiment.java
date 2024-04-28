package com.example.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sentiment {
    @JsonProperty("id")
    private String id;
    @JsonProperty("sentiment")
    private String sentiment;
    @JsonProperty("movie_id")
    private String movieId;

    public String getMovieId() {
        return movieId;
    }

    public String getSentiment() {
        return sentiment;
    }

    public String getId() {
        return id;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
}
