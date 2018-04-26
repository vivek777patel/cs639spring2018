package base.pace.cs639springhw6;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private PlaceAutocompleteFragment mAutocompleteFragment;

    private TextView mLocationNameTextView, mWeatherDetailsTextView;
    private ImageView mLocationImageView, mWeatherDetailsImageView;

    private String mSelectedLocationString, mSelectedLocationTemperature;
    private GeoDataClient mGeoDataClient;
    private double mSelectedPlaceLat, mSelectedPlaceLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        addOnPlaceSelectedListener();
        //getSelectedPlaceWeatherInfo(40.7127753, -74.0059728);
    }

    private void setViews() {
        mLocationNameTextView = findViewById(R.id.selected_place_text_view);
        mLocationImageView = findViewById(R.id.selected_place_image_view);

        mWeatherDetailsTextView = findViewById(R.id.selected_place_weather_text_view);
        mWeatherDetailsImageView = findViewById(R.id.selected_place_weather_image_view);

        mAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mGeoDataClient = Places.getGeoDataClient(this);
    }

    private void addOnPlaceSelectedListener() {
        mAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //Log.i(TAG, "Selected Place ID : " + place.getLatLng().latitude + " : Lon : " + place.getLatLng().longitude);

                // Can be used to query weather based on name
                mSelectedLocationString = place.getName().toString();

                // Can be used to query weather based on latitude and longitude
                mSelectedPlaceLat = place.getLatLng().latitude;
                mSelectedPlaceLon = place.getLatLng().longitude;

                mAutocompleteFragment.setText(place.getName());
                mLocationNameTextView.setText(place.getName());

                getPhotos(place.getId());
                getSelectedPlaceWeatherInfo(mSelectedPlaceLat, mSelectedPlaceLon);

            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
                mLocationNameTextView.setText(R.string.something_went_wrong);
                generateToastMessage("id", R.string.something_went_wrong);
            }
        });
    }

    // Request photos and metadata for the specified place.
    private void getPhotos(String placeId) {
        //final String placeId = "ChIJa147K9HX3IAR-lwiGIQv9i4";
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {

                // Get the list of photos. As per doc, it will give max 10 photos of a place
                PlacePhotoMetadataResponse photos = task.getResult();

                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                // A check to see if image is available or not
                if (photoMetadataBuffer.getCount() != 0) {
                    // Get the first photo in the list.
                    PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                    // Get a full-size bitmap for the photo.
                    Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            Bitmap bitmap = photo.getBitmap();
                            mLocationImageView.setImageBitmap(bitmap);
                            mLocationImageView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void getSelectedPlaceWeatherInfo(double selectedPlaceLat, double selectedPlaceLon) {
        // According to API, need to concat Latitude and Longitude
        String latLon = String.valueOf(selectedPlaceLat) + "," + String.valueOf(selectedPlaceLon);

        WeatherAPIHttpClient.getInstance().getWeatherDetailOfPlaceValue(latLon, new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        JSONObject responseJsonObject = new JSONObject(response.body().string());
                        JSONObject dataJsonObject = responseJsonObject.getJSONObject("data");
                        JSONArray currentWeatherJsonArray = dataJsonObject.getJSONArray("current_condition");
                        JSONObject firstJsonObject = currentWeatherJsonArray.getJSONObject(0);

                        mSelectedLocationTemperature = firstJsonObject.optString("temp_F");
                        String weatherIconUrl = firstJsonObject.getJSONArray("weatherIconUrl").getJSONObject(0).optString("value");
                        String weatherDesc = firstJsonObject.getJSONArray("weatherDesc").getJSONObject(0).optString("value");

                        mWeatherDetailsTextView.setVisibility(View.VISIBLE);

                        //Set weather icon image view only if the url is not empty
                        if (!weatherIconUrl.isEmpty()) {
                            Log.i(TAG, "Image view is not empty");
                            mWeatherDetailsImageView.setVisibility(View.VISIBLE);
                            Picasso.get().load(weatherIconUrl).into(mWeatherDetailsImageView);
                        }
                        // Degree sign (char) 0x00B0 or "\u00B0"
                        // mWeatherDetailsTextView.setText(mSelectedLocationTemperature+ (char) 0x00B0 + " , " + weatherDesc);
                        mWeatherDetailsTextView.setText(mSelectedLocationTemperature + "\u00B0" + " , " + weatherDesc);
                        //generateToastMessage("string", String.format(getString(R.string.success_fetch_msg), mSelectedLocationString, mSelectedLocationTemperature));
                        generateToastMessage("string", getString(R.string.success_fetch_msg, mSelectedLocationString, mSelectedLocationTemperature));
                    } else
                        generateToastMessage("id", R.string.something_went_wrong);

                } catch (IOException e) {
                    generateToastMessage("id", R.string.something_went_wrong);
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    generateToastMessage("id", R.string.problem_with_response_json);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                generateToastMessage("id", R.string.response_json_not_available);

            }
        });
    }

    // To generate Toast message
    private void generateToastMessage(String format, Object value) {
        if (format.equalsIgnoreCase("string"))
            Toast.makeText(this, value.toString(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, (Integer) value, Toast.LENGTH_SHORT).show();
    }
}
