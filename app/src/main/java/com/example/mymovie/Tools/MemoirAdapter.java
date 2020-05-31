package com.example.mymovie.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mymovie.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MemoirAdapter extends ArrayAdapter<MemoirShow> {
    private int resourceId;
    public MemoirAdapter(@NonNull Context context, int resource, @NonNull List<MemoirShow> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MemoirShow memoirShow = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView memoirName=view.findViewById(R.id.t_memoirname);
        TextView memoirReleasedate=view.findViewById(R.id.t_memoirreleasedate);
        TextView memoirWatchdate=view.findViewById(R.id.t_memoirwatchdate);
        TextView memoirComment=view.findViewById(R.id.t_memoircomment);
        TextView memoirCinemapostcode=view.findViewById(R.id.t_memoir_cinemapostcode);
        TextView memoirScore=view.findViewById(R.id.t_memoir_userscore);
        TextView memoirPublicScore=view.findViewById(R.id.t_memoir_publicscore);
        ImageView memoirimg=view.findViewById(R.id.img_memoir);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        memoirName.setText(memoirShow.getMovie_name());
        memoirReleasedate.setText(formatter.format(memoirShow.getRelease_date()));
        if(memoirShow.getWatch_date()!=null){
            memoirWatchdate.setText(formatter.format(memoirShow.getWatch_date()));
        }else{
            memoirWatchdate.setText("2015-01-02");
        }

        memoirComment.setText(memoirShow.getComment());
        memoirCinemapostcode.setText(memoirShow.getCinema_postcode());
        memoirScore.setText(String.valueOf(memoirShow.getScore()));
        memoirPublicScore.setText(String.valueOf(memoirShow.getPublic_score()));
        Glide.with(getContext()).load(memoirShow.getImg_url()).centerInside().placeholder(R.mipmap.ic_launcher).into(memoirimg);

        return view;
    }
}
