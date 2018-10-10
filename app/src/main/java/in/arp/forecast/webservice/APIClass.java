package in.arp.forecast.webservice;

import in.arp.forecast.model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIClass {
    @GET("forecast")
    Call<BaseResponse> fetchData(@Query("q") String q, @Query("mode") String mode, @Query("APPID") String appid);
}
