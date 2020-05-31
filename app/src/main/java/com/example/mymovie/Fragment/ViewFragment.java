package com.example.mymovie.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymovie.R;
import com.example.mymovie.Tools.ImdbAPI;
import com.example.mymovie.database.WatchListMovieDatabase;
import com.example.mymovie.entity.WatchListMovie;
import com.example.mymovie.viewmodel.WatchListMovieViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewFragment extends Fragment {

    private TextView t_moviename;
    private TextView t_releaseDate;
    private TextView t_directors;
    private TextView t_plot;
    private TextView t_actors;
    private TextView t_genre;
    private TextView t_countries;
    private ImageView img_score;
    private Button b_add2watchlist;
    private Button b_add2memoir;
    private ImdbAPI imdbAPI=null;

    private  List<WatchListMovie> noewdata;
    private String img_url;

    //WatchListMovieDatabase db = null;
    WatchListMovieViewModel watchlistmovieViewModel;
    FragmentManager fragmentManager=null;

    private String movieId;
    private  String moviename;
    private Double score;
    private String releaseDate ;
    private String directors ;
    private String plot ;
    private String genres ;
    private String actors;
    private String countries;
    private Boolean ifexisted=false;
    private List<WatchListMovie> nowdata;
    public ViewFragment() {         // Required empty public constructor
         }
         @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             // Inflate the View for this fragment
             View view = inflater.inflate(R.layout.view_fragment, container, false);
             final Bundle bundle=getArguments();
             movieId=bundle.getString("movieId");
             fragmentManager = getActivity().getSupportFragmentManager();
             //movieId="tt1375666";
             imdbAPI=new ImdbAPI();
             t_moviename = view.findViewById(R.id.t_moviename);
             t_releaseDate = view.findViewById(R.id.t_releasedate);
             t_directors = view.findViewById(R.id.t_director);
             t_plot = view.findViewById(R.id.t_plot);
             t_actors = view.findViewById(R.id.t_actors);
             t_genre =view.findViewById(R.id.t_genre);
             t_countries =view.findViewById(R.id.t_country);
             img_score=view.findViewById(R.id.img_score);
             b_add2watchlist=view.findViewById(R.id.b_add2Watchlist);
             b_add2memoir=view.findViewById(R.id.b_add2Memoir);
             b_add2watchlist.setClickable(false);
             b_add2memoir.setClickable(false);
             watchlistmovieViewModel = new ViewModelProvider(this).get(WatchListMovieViewModel.class);
             watchlistmovieViewModel.initalizeVars(getActivity().getApplication());
             GetimdbBymovieIdTask getimdbBymovieIdTask = new GetimdbBymovieIdTask();
             getimdbBymovieIdTask.execute(movieId);
             watchlistmovieViewModel.getAllWatchListMovies().observe(this, new Observer<List<WatchListMovie>>()
             {
                 @Override
                 public void onChanged(@Nullable final List<WatchListMovie> watchlistmovies) {
                     nowdata=watchlistmovies;
                 }
             });
             b_add2watchlist.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(ifexisted==false) {
                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                         String cur_date = df.format(new Date());

                         WatchListMovie wm = new WatchListMovie(moviename, releaseDate, cur_date, movieId);
                         watchlistmovieViewModel.insert(wm);
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         Fragment watchlistFragment = new WatchlistFragment();
                         fragmentTransaction.replace(R.id.content_frame, watchlistFragment).addToBackStack(null).commit();
                     }
                     else{
                         Toast toast=Toast.makeText(getContext(), "The movie is existed in watch list", Toast.LENGTH_SHORT);
                         toast.show();
                     }
                 }
             });

             b_add2memoir.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Bundle bundle1=new Bundle();
                     bundle1.putString("img_url",img_url);
                     bundle1.putString("movieName",moviename);
                     bundle1.putString("movieId",movieId);
                     bundle1.putString("releaseDate",releaseDate);
                     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                     Fragment addFragment = new AddFragment();
                     addFragment.setArguments(bundle1);
                     fragmentTransaction.replace(R.id.content_frame, addFragment).addToBackStack(null).commit();

                 }
             });

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
                img_url=jsonObject.getString("image");
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
                b_add2memoir.setClickable(true);
                watchlistmovieViewModel.getAllWatchListMovies().getValue();
                if(nowdata!=null){
                    for(WatchListMovie w : nowdata){
                    if(w.getMovieId().equals(movieId)){
                        ifexisted=true;
                        break;
                    }
                }}
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}