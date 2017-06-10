package br.com.newestapps.movie;

import android.content.Context;

import com.github.fernandodev.androidproperties.lib.AssetsProperties;
import com.github.fernandodev.androidproperties.lib.Property;

public class Config extends AssetsProperties {

    @Property("THEMOVIEDB_API_KEY")
    public String apiKey;

    @Property("THEMOVIEDB_API_KEY_V4")
    public String apiKeyV4;

    @Property("THEMOVIEDB_ENDPOINT")
    public String baseUrl;

    @Property("THEMOVIEDB_POSTER_IMG_BASE")
    public String posterImgBaseUrl;

    @Property("RESULTS_LANGUAGE")
    public String resultLanguage;

    public Config(Context context) {
        super(context);
    }

}
