package com.example.chillz;

import static android.widget.ImageView.*;
import static com.example.chillz.MainActivity.MOVIE_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {

    private String movieId;
    public Integer movieIdInt;
    private ImageView backPoster;
    private TextView movieTitle,movieOverView,rating,duration,language,releaseDate,genreTv,productionCompanyTv,budget,revenue,website;
    List<Genre> genre;
    List<ProductionCompany> productionCompanies;
    List<SpokenLanguage> spokenLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent = getIntent();
        movieId = intent.getStringExtra(MOVIE_ID);
        setUpUiViews();
        getDataFromApi();

    }

    //Getting the JSON data from API using Retrofit.
    private void getDataFromApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        //movieId is sent to instance of the object, using movieID the URL is manipulated.
        movieIdInt = Integer.parseInt(movieId);
        Call<DataOnMovie> call = jsonPlaceHolderApi.getDataOnMovie(movieIdInt);

        call.enqueue(new Callback<DataOnMovie>() {
            @Override
            public void onResponse(Call<DataOnMovie> call, Response<DataOnMovie> response) {
                //Even if the response is empty or file not found, it is a Response. To find Response that has errors 'if' condition is used.
                if(!response.isSuccessful()){
                    Toast.makeText(MovieDetails.this, "Error check internet ->" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                DataOnMovie dataOnMovies = response.body();
                setDataOnUi(dataOnMovies);
            }
            @Override
            public void onFailure(Call<DataOnMovie> call, Throwable t) {
                Toast.makeText(MovieDetails.this, "Error ->"+t, Toast.LENGTH_LONG).show();
            }
        });

    }

    //Data fetched from the api is processed and set to their respective place.
    private void setDataOnUi(DataOnMovie dataOnMovies) {

       //Setting Data on Textfields

        movieTitle.setText(dataOnMovies.getOriginal_title());
        movieOverView.setText(dataOnMovies.getOverview());
        rating.setText("IDMb Rating : "+ dataOnMovies.getVote_average());
        duration.setText("Run Time: "+dataOnMovies.getRuntime()+" mins");
        releaseDate.setText(dataOnMovies.getRelease_date());
        budget.setText(shortenNumber(dataOnMovies.getBudget())+" Cr");
        revenue.setText(shortenNumber(dataOnMovies.getRevenue())+ " Cr");
        website.setMovementMethod(LinkMovementMethod.getInstance());
        website.setText(Html.fromHtml("<a href="+dataOnMovies.getHomepage()+">"+dataOnMovies.getHomepage()+"</a>"));

        genre = new ArrayList<>(Arrays.asList(dataOnMovies.getGernes()));
        String content ="";
        for(Genre genre1:genre){
            content += genre1.getName()+", \n";
            genreTv.setText(content);
        }

        productionCompanies = new ArrayList<>(Arrays.asList(dataOnMovies.getProductionCompanies()));
        String ProductionContent="";
        for(ProductionCompany productionCompany1:productionCompanies){
            ProductionContent += productionCompany1.getName()+",\n";
            productionCompanyTv.setText(ProductionContent);

        }
        spokenLanguage = new ArrayList<>(Arrays.asList(dataOnMovies.getSpokenLanguage()));
        String SpokenLanguageContent="";
        for(SpokenLanguage spokenLanguage1:spokenLanguage){
            SpokenLanguageContent += spokenLanguage1.getName()+", \n";
            language.setText(SpokenLanguageContent);
        }

        //Setting the relative image to the ImageView
        String URL = "https://image.tmdb.org/t/p/w780"+dataOnMovies.getBackdrop_path();
        Picasso.with(this).load(URL).into(backPoster);
    }

    //This method converts long digits into readable amounts ie,: argument passed to function is 10000000, this method returns as 1 crore.
    private String shortenNumber(Integer budget) {
        float amountInCrores;
        amountInCrores = budget/10000000;
        String amountInCrorestr =String.valueOf(amountInCrores);
        return amountInCrorestr;
    }

    //Linking the Xml ids to the java variables.
    private void setUpUiViews() {
        backPoster      = (ImageView) findViewById(R.id.detailsBackground);
        movieTitle      = (TextView) findViewById(R.id.tvMovieTitle);
        movieOverView   = (TextView) findViewById(R.id.tvMovieOverView);
        rating          = (TextView) findViewById(R.id.tvRating);
        language        = (TextView) findViewById(R.id.tvLanguage);
        duration        = (TextView) findViewById(R.id.tvRunTime);
        releaseDate     = (TextView) findViewById(R.id.tvReleaseDate);
        genreTv           = (TextView) findViewById(R.id.tvGenre);
      productionCompanyTv = (TextView) findViewById(R.id.tvProductionCompanies);
        budget          = (TextView) findViewById(R.id.tvBudget);
        revenue         = (TextView) findViewById(R.id.tvRevenue);
        website         = (TextView) findViewById(R.id.tvHomepage);
    }
}