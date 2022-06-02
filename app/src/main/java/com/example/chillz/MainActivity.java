package com.example.chillz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    public static final String MOVIE_ID ="";
    RecyclerView homeRecyclerView;
    Integer clickedItem;
    EditText searchMovie;
    ImageView handBurger,profile;
    List<Results> results;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        SetUpUI();
        getDatafromApi();

        searchMovie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });
        handBurger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Oops, I'm Not ready yet", Toast.LENGTH_SHORT).show();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Oops, Try some movies", Toast.LENGTH_SHORT).show();
            }

        });


    }

    //Linking the Xml ids to the java variables.
    private void SetUpUI() {
        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        searchMovie = (EditText) findViewById(R.id.etMovieSearch);
        handBurger = (ImageView) findViewById(R.id.handBurger);
        profile =(ImageView) findViewById(R.id.profile);
    }

    //Getting the JSON data from API using Retrofit.
    private void getDatafromApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<MoviesList> call = jsonPlaceHolderApi.getMoviesList();
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error fetching data, Check your Network", Toast.LENGTH_SHORT).show();
                }
                MoviesList moviesList = response.body();
                results = new ArrayList<>(Arrays.asList(moviesList.getResults()));
                PutDataIntoRecyclerView(results);
            }
            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error -> "+t , Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Data fetched from the api is processed and set to their respective place.
    private void PutDataIntoRecyclerView(List<Results> results){
        adapter = new Adapter(this,results);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        homeRecyclerView.setLayoutManager(gridLayoutManager);
        homeRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
    }

    @Override
    public void onItemClick(int position, String movieTitle) {
        Intent detailIntent = new Intent(this,MovieDetails.class);
        for(Results results1:results){
            if(results1.getMovieTitle().toLowerCase(Locale.ROOT).contains(movieTitle.toLowerCase())){
                clickedItem = results1.getMovieId();
            }

        }
        detailIntent.putExtra(MOVIE_ID, clickedItem.toString());
        startActivity(detailIntent);
    }
    //Text from Search feild is processed and information is displayed
    private void filter(String text) {
        ArrayList<Results> filteredList = new ArrayList<>();

        for(Results results1:results){
            if(results1.getMovieTitle().toLowerCase(Locale.ROOT).contains(text.toLowerCase())){
                filteredList.add(results1);
            }
        }
        adapter.filterList(filteredList);
    }
}