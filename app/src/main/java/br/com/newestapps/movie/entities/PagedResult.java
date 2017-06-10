package br.com.newestapps.movie.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedResult<T> {

    private int page;
    private List<T> results;
    private ResultDate dates;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public ResultDate getDates() {
        return dates;
    }

    public void setDates(ResultDate dates) {
        this.dates = dates;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "PagedResult{" +
                "page=" + page +
                ", results=" + results +
                ", dates=" + dates +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }
}
