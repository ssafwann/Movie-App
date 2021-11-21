package com.example.movieapp;

public class Model {

    String genre, image, name, price;
    String rating,release, shortDesc;

    public Model() {
    }

    public Model(String genre, String image, String name, String price, String rating, String release, String shortDesc) {
        this.genre = genre;
        this.image = image;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.release = release;
        this.shortDesc = shortDesc;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }
}
