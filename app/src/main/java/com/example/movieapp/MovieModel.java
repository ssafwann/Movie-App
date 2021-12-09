/*
    The class is the used to represent a movie entity, it contains all the fields that are stored in the firebase
 */
package com.example.movieapp;

import java.io.Serializable;

public class MovieModel implements Serializable {

    String genre, name, image, runtime;
    String rating,release, shortDesc;
    String writer,director,cast;
    Long price;

    public MovieModel() {
    }

    public MovieModel(
            String genre, String image, String name, Long price,
            String rating, String release, String shortDesc, String runtime,
            String writer, String director, String cast
    ) {
        this.genre = genre;
        this.image = image;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.release = release;
        this.shortDesc = shortDesc;
        this.runtime = runtime;
        this.writer = writer;
        this.director = director;
        this.cast = cast;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }
}
