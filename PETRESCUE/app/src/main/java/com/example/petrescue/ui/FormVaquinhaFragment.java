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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Vaquinha;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.VaquinhaService;
import com.example.petrescue.service.RetrofitConfig;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class FormVaquinhaFragment extends Fragment {

    private TextInputEditText titulo;
    private TextInputEditText descricao;
    private TextInputEditText meta;
    private Button salvar;
    private Button btnFoto;
    private TextView imgMessage;

    private Vaquinha vaquinha;
    private Retrofit retrofit;
    private VaquinhaService vaquinhaService;

    private final static int IMG_REQUEST_CODE = 21;
    private Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_vaquinha, container, false);

        this.vaquinha = (Vaquinha) getArguments().getSerializable("vaquinha");

        this.inicializaComponentes(root);

        this.btnFoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMG_REQUEST_CODE);
        });

        this.salvar.setOnClickListener(v -> {
            this.vaquinha.setTitulo(this.titulo.getText().toString());
            this.vaquinha.setDescricao(this.descricao.getText().toString());
            this.vaquinha.setMeta(this.meta.getText().toString().length() > 0 ? Double.parseDouble(this.meta.getText().toString()) : 0.0);
            if(bitmap != null) this.vaquinha.setFoto(imageToBase64());
            if (this.vaquinha.getId() != null) {
                this.editarVaquinha();
            } else {
                this.vaquinha.setIdUsuario(ControleActivity.USUARIO.getId());
                this.cadastrarVaquinha(root);
            }
        });

        return root;
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

    private void inicializaComponentes(View v) {
        this.titulo = v.findViewById(R.id.et_titulo_formvaquinha);
        this.descricao = v.findViewById(R.id.et_descricao_formvaquinha);
        this.meta = v.findViewById(R.id.et_meta_formvaquinha);
        this.salvar = v.findViewById(R.id.bt_salvar_formvaquinha);
        this.btnFoto = v.findViewById(R.id.btn_foto_vaquinha);
        this.imgMessage = v.findViewById(R.id.img_message_vaquinha);
        this.imgMessage.setVisibility(View.GONE);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.vaquinhaService = this.retrofit.create(VaquinhaService.class);

        if (this.vaquinha.getId() != null) {
            this.carregarInputs();
        }
    }

    private void carregarInputs() {
        this.titulo.setText(this.vaquinha.getTitulo());
        this.descricao.setText(this.vaquinha.getDescricao());
        this.meta.setText(this.vaquinha.getMeta() == null ? Double.toString(0.0) : Double.toString(this.vaquinha.getMeta()));
    }

    private void cadastrarVaquinha(View v) {
        this.vaquinhaService.cadastrarVaquinha(this.vaquinha).enqueue(new Callback<Vaquinha>() {
            @Override
            public void onResponse(Call<Vaquinha> call, Response<Vaquinha> response) {
                if (response.isSuccessful()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idvaquinha", response.body().getId());
                    Navigation.findNavController(v).navigate(R.id.action_nav_form_vaquinha_to_nav_vaquinha, bundle);
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Vaquinha> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }

    private void editarVaquinha() {
        this.vaquinhaService.editarVaquinha(this.vaquinha).enqueue(new Callback<Vaquinha>() {
            @Override
            public void onResponse(Call<Vaquinha> call, Response<Vaquinha> response) {
                if (response.isSuccessful()) {
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Vaquinha> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }
}