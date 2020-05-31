package com.example.mymovie.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mymovie.R;

import java.util.List;

public class SearchmovieAdapter extends ArrayAdapter<SearchResult> {
    private int resouceId;


    public SearchmovieAdapter( Context context, int resource,  List<SearchResult> objects) {
        super(context, resource, objects);
        this.resouceId=resource;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        SearchResult searchResult=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resouceId,parent,false);
        ImageView img=(ImageView)view.findViewById(R.id.sm_img);
        TextView name=(TextView)view.findViewById(R.id.sm_name);
        TextView releasedate=(TextView)view.findViewById(R.id.sm_releasedate);
        String imgurl=searchResult.getImageUrl();
        Glide.with(getContext()).load(imgurl).centerInside().placeholder(R.mipmap.ic_launcher).into(img);
        //img.setImageResource(searchResult.getImageId());
        name.setText(searchResult.getName());
        releasedate.setText("");
        return view;
    }


}
