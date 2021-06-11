package com.example.petrescue.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PinFragment extends Fragment implements OnMapReadyCallback {

    private Button editar;
    private Button btAcessarUsuario;
    private TextInputEditText descricao;
    private TextView tipoPin;
    private TextView tipoAnimal;
    private TextInputEditText raca;
    private ImageView foto;

    private Integer idPin;
    private AnimalPIN pin;

    private Retrofit retrofit;
    private AnimalPinService animalPinService;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pin, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_pin);
        mapFragment.getMapAsync(this);

        this.idPin = getArguments().getInt("idpin");
        this.inicializaComponentes(v);

        this.editar.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("pin", this.pin);
            Navigation.findNavController(v).navigate(R.id.action_nav_pin_to_nav_form_pin, bundle);
        });

        this.btAcessarUsuario.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idusuario", this.pin.getIdUsuario());
            Navigation.findNavController(v).navigate(R.id.action_nav_pin_to_nav_usuario, bundle);
        });

        return v;
    }

    private void inicializaComponentes(View v) {
        this.editar = v.findViewById(R.id.bt_editar_pin);
        this.btAcessarUsuario = v.findViewById(R.id.bt_acessar_usuario_pin);
        this.descricao = v.findViewById(R.id.et_descricao_pin);
        this.tipoPin = v.findViewById(R.id.tv_tipopin_pin);
        this.tipoAnimal = v.findViewById(R.id.tv_tipoanimal_pin);
        this.raca = v.findViewById(R.id.et_raca_pin);
        this.foto = v.findViewById(R.id.iv_foto_pin);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.animalPinService = this.retrofit.create(AnimalPinService.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.animalPinService.buscarAnimalPinId(this.idPin).enqueue(new Callback<AnimalPIN>() {
            @Override
            public void onResponse(Call<AnimalPIN> call, Response<AnimalPIN> response) {
                if (response.isSuccessful()) {
                    pin = response.body();
                    atualizaCampos();
                    if (mMap != null) {
                        mMap.clear();
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pin.getLocalizacao().getLatitude(), pin.getLocalizacao().getLongitude()), 16.0f));
                    switch (pin.getTipoAnimal()) {
                        case CACHORRO:
                            createMarker(pin.getLocalizacao(), TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_dog : R.drawable.missing_dog);
                            break;
                        case GATO:
                            createMarker(pin.getLocalizacao(), TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_cat : R.drawable.missing_cat);
                            break;
                        case ROEDOR:
                            createMarker(pin.getLocalizacao(), TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_rodent : R.drawable.missing_rodent);
                            break;
                        case AVE:
                            createMarker(pin.getLocalizacao(), TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_bird : R.drawable.missing_bird);
                            break;
                        case OUTROS:
                            createMarker(pin.getLocalizacao(), TipoPIN.AVISTADO.equals(pin.getTipoPIN()) ? R.drawable.spotted_other : R.drawable.missing_other);
                            break;
                    }
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<AnimalPIN> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
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
    }

    private void createMarker(@NonNull Localizacao location, int img) {
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

    private void atualizaCampos() {
        this.descricao.setText(this.pin.getDescricao());
        this.tipoPin.setText(this.pin.getTipoPIN().toString());
        this.tipoAnimal.setText(this.pin.getTipoAnimal().toString());
        this.raca.setText(this.pin.getRaca());
        this.btAcessarUsuario.setText("Acesse o perfil de " + this.pin.getNomeUsuario());

        if (ControleActivity.USUARIO.getId().equals(this.pin.getIdUsuario())) {
            this.editar.setVisibility(View.VISIBLE);
            this.btAcessarUsuario.setVisibility(View.GONE);
        } else {
            this.editar.setVisibility(View.GONE);
            this.btAcessarUsuario.setVisibility(View.VISIBLE);
        }

        if (this.pin.getAtivo().equals(false)) {
            this.editar.setVisibility(View.GONE);
        }

        if (this.pin.getFoto() != null) {
            byte[] imgBytes = Base64.decode(this.pin.getFoto(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            foto.setImageBitmap(bitmap);
            foto.setRotation(90);
        }
    }
}
