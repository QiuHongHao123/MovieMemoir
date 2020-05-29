package com.example.mymovie.Tools;

public class SearchResult {
    private String name;
    private String release_date;
    private int imageId;
    public SearchResult(String name,String release_date,int imageId){
        this.imageId=imageId;
        this.name=name;
        this.release_date=release_date;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
