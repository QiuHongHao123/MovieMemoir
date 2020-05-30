package com.example.mymovie.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymovie.R;
import com.example.mymovie.Tools.SearchGoogleAPI;
import com.example.mymovie.Tools.SearchResult;
import com.example.mymovie.Tools.SearchmovieAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieSearchFragment extends Fragment {
    FragmentManager fragmentManager=null;
    private ListView sm_list=null;
    List<HashMap<String,String>> answers=null;
    public MovieSearchFragment () {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        Button b_search = view.findViewById(R.id.b_moviesearch);
        final TextView t_search = view.findViewById(R.id.t_moviesearch);
        sm_list=view.findViewById(R.id.sm_listview);
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
                         List<SearchResult> sm_result=new ArrayList<>();
                         answers=SearchGoogleAPI.getSnippet(result);
                         String snippet="";
                         String title="";
                         String date="2019";
                         String img_url="";
                         String formattedUrl="";
                         for(HashMap<String,String> one_ans:answers){
                             title=one_ans.get("title");
                             img_url=one_ans.get("imgurl");
                             snippet= one_ans.get("snippet");
                             formattedUrl=one_ans.get("formattedUrl");
                             SearchResult searchResult=new SearchResult(title,date,img_url);
                             sm_result.add(searchResult);
                         }
                         SearchmovieAdapter searchmovieAdapter=new SearchmovieAdapter(getContext(),R.layout.sm_list_view,sm_result);
                         sm_list.setAdapter(searchmovieAdapter);
                     }
                 }.execute();
            }});
        sm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 Bundle bundle =new Bundle();
                                 FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                 Fragment viewFragment=new ViewFragment();
                                 bundle.putString("movieId",answers.get(position).get("movieId"));
                                 viewFragment.setArguments(bundle);
                                 fragmentTransaction.replace(R.id.content_frame,viewFragment).addToBackStack(null).commit();


                             }
                         });
        return view;

    }
}

