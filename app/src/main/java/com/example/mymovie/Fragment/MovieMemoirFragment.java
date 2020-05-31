package com.example.mymovie.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymovie.R;
import com.example.mymovie.Tools.ImdbAPI;
import com.example.mymovie.Tools.JsonTool;
import com.example.mymovie.Tools.MemoirAdapter;
import com.example.mymovie.Tools.MemoirShow;
import com.example.mymovie.entity.Memoir;
import com.example.mymovie.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MovieMemoirFragment extends Fragment {
    private List<MemoirShow> memoirShowList;
    private TextView textView;
    private ListView memoirview;
    private Spinner sortspinner;
    private Button b_submit;
    private int selected=0;
    NetworkConnection networkConnection=null;
    FragmentManager fragmentManager=null;
    FragmentTransaction fragmentTransaction=null;
    MemoirAdapter memoirAdapter;


    public MovieMemoirFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.moviememoir_fragement, container, false);
        networkConnection=new NetworkConnection();
        memoirShowList=new ArrayList<MemoirShow>();
        memoirview=view.findViewById(R.id.list_memoirshow);
        textView=view.findViewById(R.id.tvmemoir);
        sortspinner=view.findViewById(R.id.sort_spinner);
        b_submit=view.findViewById(R.id.b_summit2movieview);
        textView.setText("This is movie memoir");
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();


        GetAllMemoirTask getAllMemoirTask = new GetAllMemoirTask ();
        getAllMemoirTask.execute();
        sortspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Collections.sort(memoirShowList, new Comparator<MemoirShow>() {
                        @Override
                        public int compare(MemoirShow m1, MemoirShow m2) {
                            if( m1.getScore()>= m2.getScore()) {
                                return 1;
                            }
                            else {
                                return -1;
                            }
                        }
                    });
                    if(memoirAdapter!=null)
                    {memoirAdapter.notifyDataSetChanged();}
                }
                if(position==1){
                    Collections.sort(memoirShowList, new Comparator<MemoirShow>() {
                        @Override
                        public int compare(MemoirShow m1, MemoirShow m2) {
                            if( m1.getPublic_score()>= m2.getPublic_score()) {
                                return 1;
                            }
                            else {
                                return -1;
                            }
                        }
                    });
                    if(memoirAdapter!=null)
                    {memoirAdapter.notifyDataSetChanged();}
                }
                if(position==2){
                    Collections.sort(memoirShowList, new Comparator<MemoirShow>() {
                        @Override
                        public int compare(MemoirShow m1, MemoirShow m2) {
                            return m1.getRelease_date().compareTo(m2.getRelease_date());
                        }
                    });
                    if(memoirAdapter!=null)
                    {memoirAdapter.notifyDataSetChanged();}
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment viewFragment=new ViewFragment();
                Bundle bundle=new Bundle();
                bundle.putString("movieId",memoirShowList.get(selected).getMovieId());
                viewFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_frame,viewFragment).addToBackStack(null).commit();

            }
        });

        memoirview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memoirview.setSelector(R.color.itemBlue);
                selected=position;
            }
        });

        return view;

    }
    private class GetAllMemoirTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String Result=networkConnection.getAllMemoir();
            try {
                JSONArray jsonArray=new JSONArray(Result);
                for(int i=0;i<jsonArray.length();i++){
                    Memoir memoir=JsonTool.tranMemoirJson2Memoir(jsonArray.get(i).toString());
                    MemoirShow memoirShow=new MemoirShow();
                    memoirShow.setMovie_name(memoir.getMovieName());
                    HashMap<String,String> hashMap= ImdbAPI.getimdbBymovieName(memoir.getMovieName()) ;
                    memoirShow.setCinema_postcode(memoir.getCinemaId().getCinemaLocation());
                    memoirShow.setComment(memoir.getComment());
                    memoirShow.setImg_url(hashMap.get("img_url"));

                    Double s=new Double(hashMap.get("score"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    ParsePosition pos = new ParsePosition(0);
                    Date releasedate = formatter.parse(memoir.getReleaseDate(), pos);
                    memoirShow.setRelease_date(releasedate);
                    memoirShow.setPublic_score(s);
                    memoirShow.setScore(memoir.getScore());
                    Date watchtime = formatter.parse(memoir.getWatchTime(), pos);
                    memoirShow.setWatch_date(watchtime);
                    memoirShow.setMovieId(hashMap.get("movieId"));
                    memoirShowList.add(memoirShow);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return "";
        }
        @Override
        protected void onPostExecute(String Result) {
            memoirAdapter=new MemoirAdapter(getContext(),R.layout.memoir_show_listview,memoirShowList);
            memoirview.setAdapter(memoirAdapter);

        }
    }
}
