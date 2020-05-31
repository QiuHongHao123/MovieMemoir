package com.example.mymovie.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymovie.R;
import com.example.mymovie.entity.WatchListMovie;
import com.example.mymovie.viewmodel.WatchListMovieViewModel;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WatchlistFragment extends Fragment {
    private TextView textView;
    private ListView unitList;
    private Button b_view;
    private Button b_delete;
    private FragmentManager fragmentManager=null;
    WatchListMovieViewModel watchlistmovieViewModel;
    private List<WatchListMovie> nowdata;
    private List<HashMap<String, String>> unitListArray;
    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int list_select=-1;
    private SimpleAdapter mListAdapter;
    List<WatchListMovie> todelete;
    public WatchlistFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        final View view = inflater.inflate(R.layout.watchlist_fragment, container, false);
        watchlistmovieViewModel = new ViewModelProvider(this).get(WatchListMovieViewModel.class);
        watchlistmovieViewModel.initalizeVars(getActivity().getApplication());
        textView=view.findViewById(R.id.tvwatch);
        textView.setText("Watch List");
        unitList = view.findViewById(R.id.list_watchlist);
        b_delete=view.findViewById(R.id.b_delete_watchlist);
        b_view=view.findViewById(R.id.b_view_watchlist);
        // I just use the pre listview

        final String[] colHEAD = new String[] {"MovieName","ReleaseDate","AddTime"};
        final int[] dataCell = new int[] {R.id.MovieName,R.id.MovieDate,R.id.MovieScore};
        fragmentManager=getActivity().getSupportFragmentManager();
        unitListArray = new ArrayList<HashMap<String, String>>();
        watchlistmovieViewModel.getAllWatchListMovies().observe(this, new Observer<List<WatchListMovie>>()
        {
            @Override
            public void onChanged(@Nullable final List<WatchListMovie> watchlistmovies) {
                unitListArray.clear();
                nowdata=watchlistmovies;
                if(watchlistmovies !=null){
                    for (WatchListMovie temp : watchlistmovies) {
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("MovieName", temp.getMovieName());
                        map.put("ReleaseDate", temp.getReleaseDate());
                        map.put("AddTime",temp.getAddTime());
                        unitListArray.add(map);
                    }
                }
                mListAdapter = new SimpleAdapter(getActivity(), unitListArray, R.layout.m_list_view, colHEAD, dataCell);
                unitList.setAdapter(mListAdapter);
            }
        });
        delete_longitem();
        unitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                unitList.setSelector(R.color.itemBlue);
                list_select=position;
            }
        });
        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                WatchListMovie selected=nowdata.get(list_select);
                Bundle bundle = new Bundle();
                bundle.putString("movieId",selected.getMovieId());
                Fragment viewFragment=new ViewFragment();
                viewFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_frame,viewFragment).addToBackStack(null).commit();
            }
        });
        b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setTitle("Sure?");
                builder.setMessage("Remove the Movie from movielist？");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });

                builder.setPositiveButton("Determine", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        watchlistmovieViewModel.delete(nowdata.get(list_select));
                    }
                });
                builder.show();
            }
        });

        return view;

    }
    public void delete_longitem(){
        todelete=new ArrayList<WatchListMovie>();
        for(int i=0;i<unitListArray.size();i++){
            Date one_date =new Date();
            ParsePosition pos = new ParsePosition(0);
            one_date = formatter.parse(unitListArray.get(i).get("AddTime"), pos);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.DATE, -7);
            Date todaypre7 = calendar1.getTime();
            if( one_date.before(todaypre7)){

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setTitle(unitListArray.get(i).get("MovieName")+"Long time after add");
                builder.setMessage("Remove the Movie from movielist？");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });

                final int finalI = i;

                builder.setPositiveButton("Determine", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        todelete.add(nowdata.get(finalI));

                    }
                });

                builder.show();

            }


        }
        if(!todelete.isEmpty()){
            for(WatchListMovie watchListMovie:todelete){
                watchlistmovieViewModel.delete(watchListMovie);
            }}
    }
}
