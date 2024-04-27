package com.example.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty("id")
    private String id;
    @JsonProperty("review")
    private String review;
    @JsonProperty("movie_id")
    private String movieId;

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}