package com.example.mymovie.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.mymovie.R;

public class AddFragment extends Fragment {

    private EditText etMessage;
    private TextView t_moviename;
    private Button button;
    private ImageView m_img;
    String movieId=null;
    FragmentManager fragmentManager=null;
    FragmentTransaction fragmentTransaction=null;

    public AddFragment() {
        // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the View for this fragment
            View view = inflater.inflate(R.layout.add_fragment, container, false);
            etMessage = view.findViewById(R.id.et_message);
            button = view.findViewById(R.id.btn);
            m_img=view.findViewById(R.id.img_moviefromview);
            t_moviename=view.findViewById(R.id.t_moviename);
            final Bundle bundle=getArguments();
            String moviename=bundle.getString("movieName");
            String releaseDate=bundle.getString("releaseDate");
            String memoirimg=bundle.getString("img_url");
            movieId=bundle.getString("img_url");
            t_moviename.setText(moviename+releaseDate);
            m_img.setImageResource(R.drawable.greenbook);



            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    Fragment viewFragment=new ViewFragment();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    bundle.putString("movieId",movieId);
                    viewFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content_frame,viewFragment).addToBackStack(null).commit();

                }
            });
            return view;
    }
}
