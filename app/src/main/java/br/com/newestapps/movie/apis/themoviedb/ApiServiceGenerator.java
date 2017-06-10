package br.com.newestapps.movie.apis.themoviedb;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.newestapps.movie.Config;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static Retrofit errorHandlerRetrofit = null;

    private static Retrofit.Builder builder;

    public static void init(Context context) {
        Config config = new Config(context);
        builder = new Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()));
    }

    public static Retrofit retrofit() {
        if (errorHandlerRetrofit == null) {
            OkHttpClient client = httpClient.build();

            errorHandlerRetrofit = builder
                    .client(client)
                    .build();
        }

        return errorHandlerRetrofit;
    }

    public static <S> S createService(Context context, Class<S> serviceClass) {
        final Config config = new Config(context);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                HttpUrl.Builder url = request.url().newBuilder()
                        .addQueryParameter("api_key", config.apiKey)
                        .addQueryParameter("language", config.resultLanguage);

                request = request.newBuilder().url(url.build()).build();

                return chain.proceed(request);
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = builder
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

}