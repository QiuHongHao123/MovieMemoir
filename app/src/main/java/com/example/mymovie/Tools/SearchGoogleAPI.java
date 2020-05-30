package com.example.mymovie.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SearchGoogleAPI {

    private static final String API_KEY  = "AIzaSyCRoiMqEmHNpxjrI11F9IuZ-UrykEQ9tXU";
    private static final String SEARCH_ID_cx = "016466645747118374776:u4llg2dqqgr";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+ API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
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
    public static List<HashMap<String,String>> getSnippet(String result){
        String snippet = null;
        String imgurl=null;
        String title=null;
        String pageid=null;
        String url="";
        List<HashMap<String,String>> Ans=new ArrayList<HashMap<String,String>>();
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            if(jsonArray != null && jsonArray.length() > 0) {
                for(int i=0 ;i<jsonArray.length();i++){
                    HashMap<String,String> one_ans = new HashMap<String,String>();
                    snippet =jsonArray.getJSONObject(i).getString("snippet");
                    title=jsonArray.getJSONObject(i).getString("title");
                    url=jsonArray.getJSONObject(i).getString("formattedUrl");
                    pageid=jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("pageid");
                    imgurl =jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("cse_image").getJSONObject(0).getString("src");
                    if(url.contains("/title/"))
                    {

                        one_ans.put("snippet",snippet);
                        one_ans.put("movieId",pageid);
                        one_ans.put("imgurl",imgurl);
                        one_ans.put("title",title);
                        Ans.add(one_ans);
                    }

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return Ans;
    }
}