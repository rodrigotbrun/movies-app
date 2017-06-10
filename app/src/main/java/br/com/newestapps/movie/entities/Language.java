package br.com.newestapps.movie.entities;

import com.google.gson.annotations.SerializedName;

public class Language {

    @SerializedName("iso_639_1")
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
        return "Language{" +
                "iso='" + iso + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
