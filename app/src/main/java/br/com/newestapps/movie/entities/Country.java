package br.com.newestapps.movie.entities;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("iso_3166_1")
    private String iso;

    private String name;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "iso='" + iso + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
