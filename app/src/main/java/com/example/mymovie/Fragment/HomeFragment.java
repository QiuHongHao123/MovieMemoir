package com.example.mymovie.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymovie.R;
import com.example.mymovie.Tools.JspnTool;
import com.example.mymovie.entity.UserTable;
import com.example.mymovie.networkconnection.NetworkConnection;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView wellcome;
    private TextView hometitle;
    NetworkConnection networkConnection=null;
    View view=null;
    public HomeFragment() {
        // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
            view = inflater.inflate(R.layout.home_fragment, container, false);
            SharedPreferences sharedPref= getActivity(). getSharedPreferences("User", Context.MODE_PRIVATE);

            String identify= sharedPref.getString("identify",null);
            identify=identify.replaceAll("\\]","");
            identify=identify.replaceAll("\\[","");
            GetRecenthighestTask getRecenthighestTask = new GetRecenthighestTask();
            UserTable user = new UserTable();
            user= JspnTool.tranidentifyJson2User(identify);
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            String curr_time=dateFormat.format(calendar.getTime());
            networkConnection=new NetworkConnection();
            String userid_str=user.getUserId().toString();
            getRecenthighestTask.execute(userid_str);
//            unitList = view.findViewById(R.id.recent_highest_listview);
//            unitListArray = new ArrayList<HashMap<String, String>>();
//
//            map.put("Name", "test");
//            map.put("Date", "qunima");
//            map.put("Score","2");
//            unitListArray.add(map);

            wellcome=view.findViewById(R.id.t_welcome);
            wellcome.setText("Wellcome   "+user.getSurname());
            hometitle=view.findViewById(R.id.home_title);
            hometitle.setText("Home Screen    "+curr_time);

//            mListAdapter = new SimpleAdapter(getActivity(), unitListArray, R.layout.m_list_view, colHEAD, dataCell);
//            unitList.setAdapter(mListAdapter);
            return view;

    }


    private class GetRecenthighestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user_id = params[0];


            //String result=networkConnection.getrecentHighestMemoir(user_id);

            return networkConnection.getrecentHighestMemoir(user_id);
        }
        @Override
        protected void onPostExecute(String movie_List) {
            List<HashMap<String, String>> unitListArray;
            SimpleAdapter mListAdapter;
            ListView unitList;

            String[] colHEAD = new String[] {"Name","Date","Score"};
            int[] dataCell = new int[] {R.id.MovieName,R.id.MovieDate,R.id.MovieScore};
            unitList = view.findViewById(R.id.recent_highest_listview);
            unitListArray = new ArrayList<HashMap<String, String>>();
            try {
                JSONArray jsonArray = new JSONArray(movie_List);
                for(int i=0;i<jsonArray.length();i++){
                    HashMap<String,String> map = new HashMap<String,String>();
                    JSONObject one=jsonArray.getJSONObject(i);
                    map.put("Name", one.getString("moviename"));
                    map.put("Date", one.getString("score"));
                    map.put("Score",one.getString("releaseDate"));
                    unitListArray.add(map);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }




            mListAdapter = new SimpleAdapter(getActivity(), unitListArray, R.layout.m_list_view, colHEAD, dataCell);
            unitList.setAdapter(mListAdapter);
        }

    }
}