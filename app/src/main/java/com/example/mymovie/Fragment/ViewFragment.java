package com.example.mymovie.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymovie.R;
import com.example.mymovie.Tools.ImdbAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewFragment extends Fragment {

    private TextView t_moviename;
    private TextView t_releaseDate;
    private TextView t_directors;
    private TextView t_plot;
    private TextView t_actors;
    private TextView t_genre;
    private TextView t_countries;
    private ImageView img_score;
    private ImdbAPI imdbAPI=null;


    private  String moviename;
    private Double score;
    private String releaseDate ;
    private String directors ;
    private String plot ;
    private String genres ;
    private String actors;
    private String countries;

    public ViewFragment() {         // Required empty public constructor
         }
         @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
             View view = inflater.inflate(R.layout.view_fragment, container, false);
             Bundle bundle=getArguments();
             //String movieId=bundle.getString("movieId");movieId
             String movieId="tt1375666";
             imdbAPI=new ImdbAPI();
             GetimdbBymovieIdTask getimdbBymovieIdTask = new GetimdbBymovieIdTask();
             getimdbBymovieIdTask.execute(movieId);
             t_moviename = view.findViewById(R.id.t_moviename);
             t_releaseDate = view.findViewById(R.id.t_releasedate);
             t_directors = view.findViewById(R.id.t_director);
             t_plot = view.findViewById(R.id.t_plot);
             t_actors = view.findViewById(R.id.t_actors);
             t_genre =view.findViewById(R.id.t_genre);
             t_countries =view.findViewById(R.id.t_country);
             img_score=view.findViewById(R.id.img_score);

             return view;
    }
    private class GetimdbBymovieIdTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String movieId=params[0];
            return imdbAPI.getimdbBymovieId(movieId);
        }
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject=new JSONObject(result);
                releaseDate=jsonObject.getString("releaseDate");
                directors=jsonObject.getString("directors");
                plot=jsonObject.getString("plot");
                genres=jsonObject.getString("genres");
                actors=jsonObject.getString("stars");
                countries=jsonObject.getString("countries");
                score=jsonObject.getDouble("imDbRating");
                moviename=jsonObject.getString("fullTitle");
                score=score*10;
                if(score<=9){img_score.setImageResource(R.drawable.score0);}
                else if(score<=18){img_score.setImageResource(R.drawable.score0_5);}
                else if(score<=27){img_score.setImageResource(R.drawable.score1);}
                else if(score<=36){img_score.setImageResource(R.drawable.score1_5);}
                else if(score<=45){img_score.setImageResource(R.drawable.score2);}
                else if(score<=54){img_score.setImageResource(R.drawable.score2_5);}
                else if(score<=63){img_score.setImageResource(R.drawable.score3);}
                else if(score<=72){img_score.setImageResource(R.drawable.score3_5);}
                else if(score<=81){img_score.setImageResource(R.drawable.score4);}
                else if(score<=90){img_score.setImageResource(R.drawable.score4_5);}
                else{img_score.setImageResource(R.drawable.score5);}



                t_moviename.setText(moviename);
                t_releaseDate.setText(releaseDate);
                t_directors.setText(directors);
                t_plot.setText(plot);
                t_actors.setText(actors);
                t_genre.setText(genres);
                t_countries.setText(countries);




            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}