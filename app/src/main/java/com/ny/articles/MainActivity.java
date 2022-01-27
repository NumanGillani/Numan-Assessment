package com.ny.articles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ny.articles.Adapters.ArticlesAdapter;
import com.ny.articles.Models.ArticlesModel;
import com.ny.articles.Models.ResultsModel;
import com.ny.articles.Retrofit.RetrofitClient;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public ProgressDialog pDialog;
    RecyclerView rv_articles;
    List<ArticlesModel> articlesModels = new ArrayList<>();
    private ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rv_articles = findViewById(R.id.popularArticles);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_articles.setLayoutManager(mLayoutManager);
        rv_articles.setItemAnimator(new DefaultItemAnimator());

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading, please wait ...");

        getPopularArticles();

    }

    private void getPopularArticles() {
        try {
            showpDialog();
            Call<ResultsModel> call = RetrofitClient.getInstance().getMyApi().getPopularArticles();
            call.enqueue(new Callback<ResultsModel>() {
                @Override
                public void onResponse(Call<ResultsModel> call, Response<ResultsModel> response) {
                    Log.e("response.body()", response.isSuccessful() + ".");
                    ResultsModel myArticlesList = response.body();
                    Log.e("Data Found", myArticlesList.getStatus());
                    if (myArticlesList.getStatus().equals("OK")) {
                        articlesModels = myArticlesList.getMostPopularArticles();
                        articlesAdapter = new ArticlesAdapter(articlesModels, MainActivity.this);
                        rv_articles.setAdapter(articlesAdapter);
                        articlesAdapter.notifyDataSetChanged();
                        hidepDialog();

                    }

                }

                @Override
                public void onFailure(Call<ResultsModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
                    hidepDialog();
                }

            });
        } catch (Exception ex) {
            Toast.makeText(this, "Sorry, Unusual App Behaviour found.", Toast.LENGTH_SHORT).show();
            hidepDialog();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            Utils.Companion.setTitle(articlesModels.get(position).getTitle());
            Utils.Companion.setArticleDescription(articlesModels.get(position).getArticleDescription());
            Utils.Companion.setArticleSection(articlesModels.get(position).getArticleSection());
            Utils.Companion.setArticleURL(articlesModels.get(position).getArticleURL());
            Utils.Companion.setByLine(articlesModels.get(position).getByLine());
            Utils.Companion.setPublishedDate(articlesModels.get(position).getPublishedDate());
            Utils.Companion.setThumbnailURL(articlesModels.get(position).getMediaThumbnails().get(0).getMediaMetadata().get(2).getThumbnailURL());

            Intent detailActivity = new Intent(this, DetailsScreen.class);
            startActivity(detailActivity);
        } catch (Exception ex) {
            Toast.makeText(this, "Sorry, Unusual App Behaviour found..", Toast.LENGTH_SHORT).show();
        }
    }


    public void showpDialog() {
        try {
            pDialog.show();
        } catch (Exception ex) {
            Log.e("Exception", "Progress Dialog Show");
        }
    }

    public void hidepDialog() {
        try {
            pDialog.dismiss();
        } catch (Exception ex) {
            Log.e("Exception", "Progress Dialog Hide");
        }
    }

    @Override
    public void onBackPressed() {

        FancyAlertDialog.Builder
                .with(this)
                .setTitle("Thanks for considering Syed Numan for the Assessment !!!")
                .setMessage("Do you really want to Exit ?")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnText("Exit")
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel, View.VISIBLE)
                .onPositiveClicked(dialog -> super.onBackPressed())
                .onNegativeClicked(dialog -> Log.e("Do", "Nothing"))
                .build()
                .show();

        ;
    }
}