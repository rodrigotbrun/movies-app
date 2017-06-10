package br.com.newestapps.movie.entities;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    private int id;

    @SerializedName("results")
    private TrailerVideo videos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TrailerVideo getVideos() {
        return videos;
    }

    public void setVideos(TrailerVideo videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "id=" + id +
                ", videos=" + videos +
                '}';
    }
}
