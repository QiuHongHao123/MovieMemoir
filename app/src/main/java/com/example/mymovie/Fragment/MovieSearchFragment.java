package com.example.mymovie.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymovie.R;
import com.example.mymovie.Tools.SearchGoogleAPI;

public class MovieSearchFragment extends Fragment {
    public MovieSearchFragment () {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);
        Button b_search = view.findViewById(R.id.b_moviesearch);
        final TextView t_search = view.findViewById(R.id.t_moviesearch);
        b_search.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                final String search_key = t_search.getText().toString();             //create an anonymous AsyncTask
                 new AsyncTask<String, Void, String>() {
                     @Override
                     protected String doInBackground(String... params) {
                         return SearchGoogleAPI.search(search_key, new String[]{"num"}, new String[]{"3"});
                     }
                     @SuppressLint("StaticFieldLeak")
                     @Override
                     protected void onPostExecute(String result) {
                         t_search.setText(SearchGoogleAPI.getSnippet(result));
                     }
                 }.execute();
            }});
        return view;

    }
}

