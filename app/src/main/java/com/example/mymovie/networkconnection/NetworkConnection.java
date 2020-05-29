package com.example.mymovie.networkconnection;

import com.example.mymovie.entity.Credentials;
import com.example.mymovie.entity.UserTable;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {

    private OkHttpClient client=null;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String results="";
    public NetworkConnection(){
        client=new OkHttpClient();
    }
    private static final String BASE_URL = "http://192.168.1.104:8080/Movie/webresources/";
    public String identify(String u_email,String password_hash) {
        final String methodPath = "movie_entity.credentials/identify/" + u_email+"/"+password_hash;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getrecentHighestMemoir(String userId) {
        final String methodPath = "movie_entity.memoir/RecentHignestMovie4_f/" + userId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getMaxUserid() {
        final String methodPath = "movie_entity.usertable/getMaxId/";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public String if_existed(String u_email) {
        final String methodPath = "movie_entity.credentials/" + u_email;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public String addUserTable(Integer userid,String username, String surname, String gender, String address, String state, String postcode, String dob){
        UserTable nu=new UserTable(userid);
        Date dob1=null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dob1=dateFormat.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String dob_str= format.format(dob1);

        nu.setUserName(username);
        nu.setSurname(surname);
        nu.setGender(gender);
        nu.setAddress(address);
        nu.setState(state);
        nu.setPostcode(postcode);
        nu.setDob(dob_str);


        Gson gson = new Gson();
        String nuJson = gson.toJson(nu);
        String strResponse="";
        final String methodPath = "movie_entity.usertable/";
        RequestBody body = RequestBody.create(nuJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }
    public String addCredenticals(UserTable userid,String hash_pwd,String email){
        Date signup = new Date(System.currentTimeMillis());
        Credentials credential = new Credentials(email);
        credential.setPasswordHash(hash_pwd);
        credential.setSignDate(signup);
        credential.setUserId(userid);
        Gson gson = new Gson();
        String ncJson = gson.toJson(credential);
        String strResponse="";
        final String methodPath = "movie_entity.credentials/";
        RequestBody body = RequestBody.create(ncJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public String addUser(String username, String surname, String gender, String address, String state, String postcode, String dob,String hash_pwd,String email ){
        String maxuserid_str=getMaxUserid();
        maxuserid_str=maxuserid_str.replaceAll( "\\]","");
        maxuserid_str=maxuserid_str.replaceAll("\\[","");

        JSONObject jsonObj = null;
        Integer userid=-1;
        try {
            jsonObj = new JSONObject(maxuserid_str);
            userid=jsonObj.getInt("MAXID")+1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!if_existed(email).isEmpty())
        {
            return "Existed";
        }

        UserTable nu=new UserTable(userid);
        nu.setUserName(username);
        nu.setSurname(surname);
        nu.setGender(gender);
        nu.setAddress(address);
        nu.setState(state);
        nu.setPostcode(postcode);
        //nu.setDob(dob);
        String strresponse="";

        strresponse=addUserTable(userid,username,surname,gender,address,state,postcode,dob)+addCredenticals(nu,hash_pwd,email);

        return strresponse;
    }



}
