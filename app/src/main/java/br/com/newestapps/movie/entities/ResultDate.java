package br.com.newestapps.movie.entities;

import java.sql.Date;

public class ResultDate {

    private String minimum;
    private String maximum;

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return "ResultDate{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }
}
