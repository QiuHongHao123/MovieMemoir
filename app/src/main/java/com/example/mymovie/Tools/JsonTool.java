package com.example.mymovie.Tools;

import com.example.mymovie.entity.Cinema;
import com.example.mymovie.entity.Memoir;
import com.example.mymovie.entity.UserTable;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonTool {
    public static UserTable tranidentifyJson2User(String userjson) {
        UserTable user = new UserTable();
        try {

            JSONObject jsonObject = new JSONObject(userjson);
            JSONObject jsonData = jsonObject.getJSONObject("userId");
            user.setUserId(jsonData.getInt("userId"));
            user.setUserName(jsonData.getString("userName"));
            user.setSurname(jsonData.getString("surname"));
            user.setDob(jsonData.getString("dob"));
            user.setState(jsonData.getString("state"));
            user.setPostcode(jsonData.getString("postcode"));
            user.setGender(jsonData.getString("gender"));
            user.setAddress(jsonData.getString("address"));


        } catch (Exception e) {

            // TODO: handle exception

        }
        return user;
    }

    public static Cinema tranCinemaJson2Cinema(String Cinemajson) {
        Cinema cinema = new Cinema();
        try {

            JSONObject jsonObject = new JSONObject(Cinemajson);
            cinema.setCinemaId(jsonObject.getInt("cinemaId"));
            cinema.setCinemaLocation(jsonObject.getString("cinemaLocation"));
            cinema.setCinemaName(jsonObject.getString("cinemaName"));


        } catch (Exception e) {

            // TODO: handle exception

        }
        return cinema;
    }

    public static Memoir tranMemoirJson2Memoir(String Memoirjson) {
        Memoir memoir = new Memoir();
        memoir.setUserId(tranidentifyJson2User(Memoirjson));
        String cinemajson=null;
        try {
            JSONObject jsonObject = new JSONObject(Memoirjson);
            cinemajson = jsonObject.getJSONObject("cinemaId").toString();
            memoir.setCinemaId(tranCinemaJson2Cinema(cinemajson));
            memoir.setMemoirId(jsonObject.getInt("memoirId"));
            memoir.setScore(jsonObject.getDouble("score"));
            memoir.setMovieName(jsonObject.getString("movieName"));
            memoir.setReleaseDate(jsonObject.getString("releaseDate"));
            memoir.setWatchTime(jsonObject.getString("watchTime"));
            memoir.setComment(jsonObject.getString("comment"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return memoir;
    }
}