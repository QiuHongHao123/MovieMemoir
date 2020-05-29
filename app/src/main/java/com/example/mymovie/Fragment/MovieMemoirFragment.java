package com.example.mymovie.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;;

import androidx.fragment.app.Fragment;

import com.example.mymovie.R;

public class MovieMemoirFragment extends Fragment {
    private TextView textView;

    public MovieMemoirFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.moviememoir_fragement, container, false);
        textView=view.findViewById(R.id.tvmemoir);
        textView.setText("This is movie memoir");
        return view;

    }
}
