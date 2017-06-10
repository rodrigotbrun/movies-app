package br.com.newestapps.movie.entities;

import com.google.gson.annotations.SerializedName;

class TrailerVideo {

    private String id;

    @SerializedName("iso_639_1")
    private String isoLanguage;

    @SerializedName("iso_3166_1")
    private String isoCountry;

    private String key;
    private String name;
    private String site;
    private int size;
    private String trailer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public void setIsoLanguage(String isoLanguage) {
        this.isoLanguage = isoLanguage;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return "TrailerVideo{" +
                "id='" + id + '\'' +
                ", isoLanguage='" + isoLanguage + '\'' +
                ", isoCountry='" + isoCountry + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", trailer='" + trailer + '\'' +
                '}';
    }
}
