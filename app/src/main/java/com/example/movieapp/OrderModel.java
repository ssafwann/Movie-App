package com.example.movieapp;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderModel implements Serializable {

    List<MovieModel> movies = new ArrayList<>();
    String purchaseDate;
    Long price;
    Long quantity;
    String orderID;
    int credits;

    public OrderModel() {

    }

    public OrderModel(List<MovieModel> movies, String purchaseDate, Long price, Long quantity, String orderID, int credits) {
        this.movies = movies;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.quantity = quantity;
        this.orderID = orderID;
        this.credits = credits;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}

