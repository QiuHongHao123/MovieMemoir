package com.example.mymovie.Tools;

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
    private static final String BASE_URL =  "https://imdb-api.com/en/API/Title/k_84160Nnb/";
    public static String getimdbBymovieId(String movieId) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        try {
            url = new URL("https://imdb-api.com/en/API/Title/k_84160Nnb/"+movieId);
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

}


