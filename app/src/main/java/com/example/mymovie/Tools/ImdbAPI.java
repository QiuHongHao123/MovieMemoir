package com.example.mymovie.Tools;

import com.example.mymovie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImdbAPI {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String getimdbBymovieId(String movieId) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        try {
            url = new URL("https://imdb-api.com/en/API/Title/k_b2VUv7oj/"+movieId);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    public static HashMap<String,String> getimdbBymovieName(String movieName) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        HashMap<String,String> hashMap=new HashMap<>();

        try {
            url = new URL("https://imdb-api.com/en/API/Search/k_b2VUv7oj/"+movieName);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
            JSONObject jsonObject=new JSONObject(textResult);
            JSONArray results=jsonObject.getJSONArray("results");
            String img_url=results.getJSONObject(0).getString("image");
            String movieId=results.getJSONObject(0).getString("id");
            String result=getimdbBymovieId(results.getJSONObject(0).getString("id"));
            JSONObject jsonObject2=new JSONObject(result);
            String score=jsonObject2.getString("imDbRating");

            hashMap.put("score",score);
            hashMap.put("img_url",img_url);
            hashMap.put("movieId",movieId);

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }

        return hashMap;
    }
}


