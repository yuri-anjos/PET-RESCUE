package com.example.petrescue.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.AnimalPIN;
import com.example.petrescue.domain.enums.TipoPIN;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalPinService;
import com.example.petrescue.service.RetrofitConfig;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PinFragment extends Fragment {

    private Button editar;
    private Button btAcessarUsuario;
    private TextInputEditText descricao;
    private TextView tipoPin;
    private TextView tipoAnimal;
    private TextView raca;
    private ImageView foto;

    private Integer idPin;
    private AnimalPIN pin;

    private Retrofit retrofit;
    private AnimalPinService animalPinService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pin, container, false);
        this.pin = (AnimalPIN) getArguments().getSerializable("pin");
        this.idPin = pin.getId();
        this.inicializaComponentes(v);

        this.editar.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("pin", this.pin);
            bundle.putDoubleArray("location", new double[]{this.pin.getLocalizacao().getLatitude(),
                                                    this.pin.getLocalizacao().getLongitude()});
            Navigation.findNavController(v).navigate(R.id.action_nav_pin_to_nav_form_pin, bundle);
        });

        this.btAcessarUsuario.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("idusuario", this.pin.getIdUsuario());
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
        this.raca = v.findViewById(R.id.tv_raca_pin);
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

    private void atualizaCampos(){
//        this.foto.setImageBitmap();
        this.descricao.setText(this.pin.getDescricao());
        this.tipoPin.setText(this.pin.getRaca());
        this.tipoAnimal.setText(this.pin.getRaca());
        this.raca.setText(this.pin.getRaca());

        if(TipoPIN.AVISTADO.equals(this.pin.getTipoPIN())){
            this.raca.setVisibility(View.GONE);
        }else{
            this.raca.setVisibility(View.VISIBLE);
        }

        if(ControleActivity.USUARIO.getId().equals(this.pin.getIdUsuario())){
            this.editar.setVisibility(View.VISIBLE);
            this.btAcessarUsuario.setVisibility(View.GONE);
        }else{
            this.editar.setVisibility(View.GONE);
            this.btAcessarUsuario.setVisibility(View.VISIBLE);
        }

        if(this.pin.getAtivo().equals(false)){
            this.editar.setVisibility(View.GONE);
        }

    }
}
