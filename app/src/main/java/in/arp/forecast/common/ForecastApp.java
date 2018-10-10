package in.arp.forecast.common;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import in.arp.forecast.BuildConfig;
import in.arp.forecast.webservice.APIClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastApp extends Application {
    public static Retrofit retrofit;
    private static APIClass api;
    private OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        initDashboardRetrofit();
    }


    private void initDashboardRetrofit() {
        initHTTPClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(APIClass.class);
    }
    public static APIClass getParseApiService() {
        return api;
    }

    private void initHTTPClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);//add None for prod env
        client = new OkHttpClient.Builder().connectTimeout(45, TimeUnit.SECONDS).readTimeout(45, TimeUnit.SECONDS).addInterceptor(interceptor).build();
    }
}
