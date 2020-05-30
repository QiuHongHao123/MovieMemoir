package com.example.mymovie.Tools;

public class SearchResult {
    private String name;
    private String release_date;
    private String imageUrl;
    public SearchResult(String name,String release_date,String url){
        this.imageUrl=url;
        this.name=name;
        this.release_date=release_date;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
