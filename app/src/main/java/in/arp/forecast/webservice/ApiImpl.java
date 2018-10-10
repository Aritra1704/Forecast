package in.arp.forecast.webservice;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import in.arp.forecast.BuildConfig;
import in.arp.forecast.common.ForecastApp;
import in.arp.forecast.model.BaseResponse;
import in.arp.forecast.model.WeatherList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiImpl {

    private static ApiImpl INSTANCE = new ApiImpl();
    private static APIClass parseAPIClass;
    private ApiResp listener;
    private static int apiId;

    public static ApiImpl getInst(int apitype) {
        apiId = apitype;
        parseAPIClass = ForecastApp.getParseApiService();
        return INSTANCE;
    }

    public interface ApiResp {
        void onResponse(Object response, Object error, int apiId);
    }

    public void getfiveDayData(String q, String mode, @NonNull ApiResp listener) {
        this.listener = listener;
        parseAPIClass.fetchData(q, mode, BuildConfig.API_KEY).enqueue(fivedaysCallback);
    }

    Callback<BaseResponse> fivedaysCallback = new Callback<BaseResponse>() {
        @Override
        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
            try {
                if(response.isSuccessful()) {
                    if (response.body().getCod().equalsIgnoreCase("200")) {
                        listener.onResponse(response.body().getList(), null, apiId);
                    }
                }else{
                    listener.onResponse(null, response.errorBody().string(), apiId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<BaseResponse> call, Throwable t) {
            listener.onResponse(null, t.getLocalizedMessage(), apiId);
        }
    };
}
