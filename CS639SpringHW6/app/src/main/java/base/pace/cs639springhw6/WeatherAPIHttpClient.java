package base.pace.cs639springhw6;

import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class WeatherAPIHttpClient {

    private final String TAG = "WeatherAPIHttpClient";
    //private final String BASE_URL = "http://api.worldweatheronline.com/free/v1/";
    private final String BASE_URL = "http://api.worldweatheronline.com/premium/v1/";
    private final String WEATHER_API_KEY = "6b8826a5e0b84274bc9214603182104";
    private final String OUTPUT_FORMAT = "json";
    private final int NO_OF_DAYS = 1;
    private Retrofit mRetrofit;

    private static final WeatherAPIHttpClient ourInstance = new WeatherAPIHttpClient();
    private WeatherService mWeatherService;

    public static WeatherAPIHttpClient getInstance() {
        return ourInstance;
    }

    //http://api.worldweatheronline.com/premium/v1/weather.ashx?key=6b8826a5e0b84274bc9214603182104&q=London&format=json&num_of_days=1
    private WeatherAPIHttpClient() {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        mWeatherService = mRetrofit.create(WeatherService.class);
    }

    private interface WeatherService {

        @GET("weather.ashx")
        Call<ResponseBody> getWeatherDetailOfPlaceValue(@Query("key") String key,@Query(value = "q",encoded = true) String latLon, @Query("format")String format, @Query("num_of_days") int noOfDays);

        // A way to pass a url directly instead passing just key
        @GET
        Call<ResponseBody> getWeatherDetailOfPlaceValue(@Url String url);
    }

    public void getWeatherDetailOfPlaceValue(String latLon, Callback<ResponseBody> cb) {
//        String url = BASE_URL + "weather.ashx?key="+WEATHER_API_KEY+"&q=" + latLon + "&format=" + OUTPUT_FORMAT + "&num_of_days=" + NO_OF_DAYS;
//        Log.i(TAG, "SSSSSS : " + url);
        mWeatherService.getWeatherDetailOfPlaceValue(WEATHER_API_KEY,latLon,OUTPUT_FORMAT,NO_OF_DAYS).enqueue(cb);
        //mWeatherService.getWeatherDetailOfPlaceValue(url).enqueue(cb);
    }
}
