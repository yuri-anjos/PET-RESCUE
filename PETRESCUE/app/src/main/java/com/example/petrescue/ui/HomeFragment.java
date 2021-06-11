package com.example.petrescue.ui;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.AnimalPIN;
import com.example.petrescue.domain.enums.TipoPIN;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.domain.subClasses.Localizacao;
import com.example.petrescue.service.AnimalPinService;
import com.example.petrescue.service.RetrofitConfig;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FusedLocationProviderClient client;
    private GoogleMap mMap;
    private View view;
    private FloatingActionButton newSpottedAnimalButton;
    private FloatingActionButton newMissingAnimalButton;
    private Retrofit retrofit = RetrofitConfig.generateRetrofit();
    private AnimalPinService animalPinService = this.retrofit.create(AnimalPinService.class);
    private List<AnimalPIN> animalPINS = new ArrayList<>();
    private FloatingActionButton btnListPins;

    private Localizacao localizacao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        newSpottedAnimalButton = view.findViewById(R.id.newSpottedAnimalButtom);
        newMissingAnimalButton = view.findViewById(R.id.newMissingAnimalButtom);
        btnListPins = view.findViewById(R.id.listAnimalPinsButton);
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        // clickListeners
        newSpottedAnimalButton.setOnClickListener(v -> {
            AnimalPIN animalPIN = new AnimalPIN();
            animalPIN.setTipoPIN(TipoPIN.AVISTADO);
            this.buscarLocalizacaoETrocarTela(animalPIN, v);
        });

        newMissingAnimalButton.setOnClickListener(v -> {
            AnimalPIN animalPIN = new AnimalPIN();
            animalPIN.setTipoPIN(TipoPIN.DESAPARECIDO);
            this.buscarLocalizacaoETrocarTela(animalPIN, v);
        });

        btnListPins.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("location", (Serializable) localizacao);
            Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_lista_pin, bundle);
        });

        return this.view;
    }

    private void buscarLocalizacaoETrocarTela(AnimalPIN animalPIN, View v) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) {
                Log.e("ERROR", "Location not found");
            }
            animalPIN.setLocalizacao(new Localizacao(location.getLatitude(), location.getLongitude()));
            Bundle bundle = new Bundle();
            bundle.putSerializable("pin", animalPIN);
            Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_form_pin, bundle);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMarkerClickListener(marker -> {
            double latitude = marker.getPosition().latitude;
            double longitude = marker.getPosition().longitude;

            for (AnimalPIN pin : animalPINS) {
                Localizacao location = pin.getLocalizacao();
                if (location.getLatitude() == latitude && location.getLongitude() == longitude) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("idpin", pin.getId());
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_pin, bundle);
                }
            }
            return false;
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        int statusCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

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
            GoogleApiAvailability.getInstance().getErrorDialog(this, statusCode, 0, dialog -> {
                return;
            }).show();
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (this.mMap != null) {
            this.mMap.clear();
        }

        client.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16.0f));
            } else {
                Log.e("Error", "Location not found");
            }
            this.localizacao = new Localizacao();
            this.localizacao.setLatitude(location.getLatitude());
            this.localizacao.setLongitude(location.getLongitude());

            this.animalPinService.buscarAnimaisPin(localizacao).enqueue(new Callback<List<AnimalPIN>>() {
                @Override
                public void onResponse(Call<List<AnimalPIN>> call, Response<List<AnimalPIN>> response) {
                    if (response.isSuccessful()) {
                        animalPINS.clear();
                        animalPINS.addAll(response.body());
                        for (AnimalPIN pin : animalPINS) {
                            Location location = new Location("none");
                            location.setLatitude(pin.getLocalizacao().getLatitude());
                            location.setLongitude(pin.getLocalizacao().getLongitude());

                            switch (pin.getTipoAnimal()) {
                                case CACHORRO:
                                    createMarker(location, TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_dog : R.drawable.missing_dog);
                                    break;
                                case GATO:
                                    createMarker(location, TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_cat : R.drawable.missing_cat);
                                    break;
                                case ROEDOR:
                                    createMarker(location, TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_rodent : R.drawable.missing_rodent);
                                    break;
                                case AVE:
                                    createMarker(location, TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_bird : R.drawable.missing_bird);
                                    break;
                                case OUTROS:
                                    createMarker(location, TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_other : R.drawable.missing_other);
                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<List<AnimalPIN>> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
                }
            });
        }).addOnFailureListener(e -> Log.e("Error", e.getMessage()));

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(3 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        settingsClient.checkLocationSettings(builder.build()).addOnSuccessListener(locationSettingsResponse ->
                Log.i("Success", "" + locationSettingsResponse.getLocationSettingsStates().isNetworkLocationPresent())).addOnFailureListener(e -> {
            if (e instanceof ResolvableApiException) {
                ResolvableApiException resolvable = (ResolvableApiException) e;
                try {
                    resolvable.startResolutionForResult(getActivity(), 0);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        });

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    Log.i("DEBUG", "Localizacao vazia.");
                    return;
                } else {
                    for (Location location : locationResult.getLocations()) {
                        Log.i("DEBUG", "Localizacao atual: " + location.getLatitude() + " " + location.getLongitude());
                    }
                }
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                Log.i("DEBUG", "Localizacao disponivel.");
            }
        };
        client.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void createMarker(@NonNull Location location, int img) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getContext(), img);
        int width = (int) (vectorDrawable.getIntrinsicWidth() * 0.5);
        int height = (int) (vectorDrawable.getIntrinsicHeight() * 0.5);
        vectorDrawable.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .anchor(0.5f, 1));
    }
}
