package com.example.petrescue.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.AnimalPIN;
import com.example.petrescue.domain.enums.TipoAnimal;
import com.example.petrescue.domain.enums.TipoPIN;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalPinService;
import com.example.petrescue.service.RetrofitConfig;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;


public class FormPinFragment extends Fragment {

    private TextView descricaoTela;
    private TextInputEditText descricao;
    private TextInputEditText raca;
    private TextInputLayout containerRaca;
    private Spinner tipoAnimal;
    private Button salvar;
    private Button btnFoto;
    private TextView imgMessage;

    private AnimalPIN pin;
    private AnimalPinService animalPinService;
    private Retrofit retrofit;

    private final static int IMG_REQUEST_CODE = 21;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form_pin, container, false);

        this.pin = (AnimalPIN) getArguments().getSerializable("pin");

        this.inicializaComponentes(view);

        this.btnFoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMG_REQUEST_CODE);
        });

        this.salvar.setOnClickListener(v -> {
            this.pin.setDescricao(this.descricao.getText().toString());
            this.pin.setTipoAnimal(TipoAnimal.valueOf(this.tipoAnimal.getSelectedItem().toString()));
            if(bitmap != null) this.pin.setFoto(imageToBase64());
            if(TipoPIN.AVISTADO.equals(this.pin.getTipoPIN())){
                this.pin.setRaca(null);
            }else{
                this.pin.setRaca(this.raca.getText().toString());
            }

            if (this.pin.getId() != null) {
                this.editarPin();
            } else {
                this.pin.setIdUsuario(ControleActivity.USUARIO.getId());
                this.cadastrarPin(v);
            }
        });

        return view;
    }

    public String imageToBase64() {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteOutput);
        byte[] imgBytes = byteOutput.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            this.imgMessage.setText("Imagem carregada!");
            this.imgMessage.setVisibility(View.VISIBLE);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Erro", "deu erro isso ae");
            }
        }

    }

    private void inicializaComponentes(View view) {
        this.descricao = view.findViewById(R.id.et_descricao_formpin);
        this.raca = view.findViewById(R.id.et_raca_formpin);
        this.tipoAnimal = view.findViewById(R.id.sp_tipoanimal_formpin);
        this.salvar = view.findViewById(R.id.bt_salvar_formpin);
        this.containerRaca = view.findViewById(R.id.container_raca_formpin);
        this.descricaoTela = view.findViewById(R.id.tv_descricao_tela_formpin);
        this.btnFoto = view.findViewById(R.id.btn_foto_pin);
        this.imgMessage = view.findViewById(R.id.img_message_form_pin);
        this.imgMessage.setVisibility(View.GONE);

        this.tipoAnimal.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, TipoAnimal.ANIMAIS));
        this.retrofit = RetrofitConfig.generateRetrofit();
        this.animalPinService = this.retrofit.create(AnimalPinService.class);

        if (TipoPIN.DESAPARECIDO.equals(this.pin.getTipoPIN())) {
            this.containerRaca.setVisibility(View.VISIBLE);
            this.descricaoTela.setText("Animal Desaparecido");
        }else{
            this.descricaoTela.setText("Animal Avistado");
        }

        if (this.pin.getId() != null) {
            this.carregarCampos(view);
        }
    }

    private void carregarCampos(View v) {
        for (int i = 0; i < this.tipoAnimal.getCount(); i++) {
            if (this.tipoAnimal.getItemAtPosition(i).equals(this.pin.getTipoAnimal())) {
                this.tipoAnimal.setSelection(i);
                break;
            }
        }
        this.descricao.setText(this.pin.getDescricao());
        this.raca.setText(this.pin.getRaca());
    }

    private void editarPin() {
        this.animalPinService.editarAnimalPIN(this.pin).enqueue(new Callback<AnimalPIN>() {
            @Override
            public void onResponse(Call<AnimalPIN> call, Response<AnimalPIN> response) {
                if (response.isSuccessful()) {
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<AnimalPIN> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, " + "tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }

    private void cadastrarPin(View v) {
        this.animalPinService.cadastrarAnimalPIN(this.pin).enqueue(new Callback<AnimalPIN>() {
            @Override
            public void onResponse(Call<AnimalPIN> call, Response<AnimalPIN> response) {
                if (response.isSuccessful()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idpin", response.body().getId());
                    Navigation.findNavController(v).navigate(R.id.nav_home, bundle);
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<AnimalPIN> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, " + "tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }
}
