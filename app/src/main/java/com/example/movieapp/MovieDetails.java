package com.example.movieapp;


import android.content.Intent;
import android.graphics.Movie;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.net.URL;

public class MovieDetails extends AppCompatActivity {

    TextView movieName, movieDesc, movieGenre, movieRating, moviePrice, movieRelease;
    TextView movieDirector, movieWriter, movieRuntime, movieCast;
    ImageView movieImage;
    MovieModel movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_movieD);
        getMovie();
        intializeTextViews();
        showMovieData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
            });
    }

    public void getMovie() {
        Intent intent = getIntent();
        movie = (MovieModel) getIntent().getSerializableExtra("movie");
    }

    public void intializeTextViews() {
        movieName = findViewById(R.id.movie_name); movieDesc = findViewById(R.id.movie_desc);
        movieGenre = findViewById(R.id.movie_genre); movieRating = findViewById(R.id.movie_rating);
        moviePrice = findViewById(R.id.movie_price); movieRelease = findViewById(R.id.movie_release);
        movieImage = findViewById(R.id.movie_image); movieDirector = findViewById(R.id.movie_director);
        movieWriter= findViewById(R.id.movie_writer); movieRuntime = findViewById(R.id.movie_runtime);
        movieCast = findViewById(R.id.movie_cast);
    }


    private void showMovieData() {
        String movie_name = movie.getName(); String movie_desc = movie.getShortDesc();
        String movie_genre = movie.getGenre(); String movie_rating = movie.getRating();
        String movie_release = movie.getRelease(); String movie_image = movie.getImage();
        String movie_director = movie.getDirector(); String movie_writer = movie.getWriter();
        String movie_cast = movie.getCast(); String movie_runtime = movie.getRuntime();

        // convert long to string
        Long movie_long_price = movie.getPrice();
        String movie_price =String.valueOf(movie_long_price);

        movieName.setText(movie_name); movieDesc.setText(movie_desc);
        movieGenre.setText(movie_genre); movieRating.setText(movie_rating);
        moviePrice.setText(movie_price); movieRelease.setText(movie_release);
        Glide.with(MovieDetails.this).load(movie_image).into(movieImage);
        movieDirector.setText(movie_director); movieCast.setText(movie_cast);
        movieWriter.setText(movie_writer); movieRuntime.setText(movie_runtime);
    }

}