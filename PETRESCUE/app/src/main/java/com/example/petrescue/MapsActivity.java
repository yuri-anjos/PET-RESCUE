package com.example.petrescue;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.petrescue.domain.constants.Constants;
import com.example.petrescue.service.FetchAddressService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    FusedLocationProviderClient client;
    AddressResultReceiver resultReceiver;
    GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        client = LocationServices.getFusedLocationProviderClient(this);
        resultReceiver = new AddressResultReceiver(null);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int statusCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (statusCode == ConnectionResult.SERVICE_MISSING) {
            Log.d("Service Missing", "Please install Google Play Services");
        }
        if (statusCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
            Log.d("Update required", "Please update Google Play Services");
        }
        if (statusCode == ConnectionResult.SERVICE_DISABLED) {
            Log.d("Service disabled", "Please enable Google Play Services");
        }
        if (statusCode == ConnectionResult.SUCCESS) {
            Log.d("Success", "Google Play Services' up-to-date");
        }
        if ((statusCode == 1) || (statusCode == 2) || (statusCode == 3)) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, statusCode,
                    0, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    }).show();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener
                (new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng userLocation = new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            createDogMarker(location);
                            mMap.moveCamera(CameraUpdateFactory.
                                    newLatLngZoom(userLocation, 16.0f));

                            location.setLongitude(-51.134043);
                            location.setLatitude(-29.8315136);
                            createCatMarker(location);
                            mMap.moveCamera(CameraUpdateFactory.
                                    newLatLngZoom(new LatLng(-29.8315136,
                                            -51.134043), 16.0f));

                        } else {
                            Log.e("Error", "Location not found");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Error", e.getMessage());
                    }
                });

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(3 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(builder.build())
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("Success", "" + locationSettingsResponse
                                .getLocationSettingsStates().isNetworkLocationPresent());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MapsActivity.this, 0);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    Log.e("Null location", "No location found");
                    finish();
                }
                for (Location location : locationResult.getLocations()) {

                    if (!Geocoder.isPresent()) {
                        return;
                    }
//                    startIntentService(location);
                }
            }


            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                Log.i("Location Availability", "" + locationAvailability.
                        isLocationAvailable());
            }
        };
        client.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    public Marker createMarkerOnUserLocation(Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            finish();
        }
        client.getLastLocation();
        return mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    private Marker createDogMarker(@NonNull Location location) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_dog_pin);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(bitmap);
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("User location")
                .icon(markerIcon));
    }

    private Marker resizeDogMarker(Marker marker) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_dog_pin);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth()
                        - Math.ceil(drawable.getIntrinsicWidth()*0.3)),
                (int) (drawable.getIntrinsicHeight()
                        - Math.ceil(drawable.getIntrinsicHeight()*0.3)));
        drawable.draw(canvas);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(bitmap);
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(marker.getPosition().latitude,
                        marker.getPosition().longitude))
                .title("User location")
                .icon(markerIcon));
    }


    private Marker createCatMarker(@NonNull Location location) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_cat_pin);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(bitmap);
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("User location")
                .icon(markerIcon));
    }

    private void startIntentService(Location location) {
        Intent intent = new Intent(this, FetchAddressService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }



    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if (resultData == null) {
                return;
            }

            final String addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

            if (addressOutput != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MapsActivity.this, addressOutput,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
