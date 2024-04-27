package com.example.consumer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "review")
public class MessageDocument {

    @Id
    private String id;
    private String movieId;
    private String review;

    private String rating;
    


    // getters and setters
    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getReview() {
        return review;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
    
