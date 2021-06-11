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
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Animal;
import com.example.petrescue.domain.enums.Sexo;
import com.example.petrescue.domain.enums.TipoAnimal;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalService;
import com.example.petrescue.service.RetrofitConfig;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class FormAnimalAdocaoFragment extends Fragment {

    private Spinner tipoAnimal;
    private TextInputEditText raca;
    private RadioGroup rgSexo;
    private TextInputEditText nome;
    private TextInputEditText nascimento;
    private TextInputEditText descricao;
    private TextInputEditText vacinas;
    private CheckBox castrado;
    private Button salvar;
    private Button btnFoto;
    private TextView imgMessage;

    private Animal animal;
    private Retrofit retrofit;
    private AnimalService animalService;

    private final static int IMG_REQUEST_CODE = 21;
    private Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_adocao, container, false);
        this.animal = (Animal) getArguments().getSerializable("animal");

        this.inicializaComponentes(root);

        this.btnFoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMG_REQUEST_CODE);
        });

        this.salvar.setOnClickListener(v -> {
            this.animal.setTipoAnimal(TipoAnimal.valueOf(this.tipoAnimal.getSelectedItem().toString()));
            this.animal.setRaca(this.raca.getText().toString());
            if(bitmap != null) this.animal.setFoto(imageToBase64());
            switch (this.rgSexo.getCheckedRadioButtonId()) {
                case R.id.rb_macho_formadocao:
                    this.animal.setSexo(Sexo.MACHO);
                    break;
                case R.id.rb_femea_formadocao:
                    this.animal.setSexo(Sexo.FEMEA);
                    break;
            }
            this.animal.setNome(this.nome.getText().toString());
            this.animal.setDataNascimento(Integer.parseInt(this.nascimento.getText().toString()));
            this.animal.setDescricao(this.descricao.getText().toString());
            this.animal.setVacinas(this.vacinas.getText().toString());
            this.animal.setCastrado(this.castrado.isChecked());

            if (this.animal.getId() != null) {
                this.editarAnimal();
            } else {
                this.animal.setIdUsuario(ControleActivity.USUARIO.getId());
                this.cadastrarAnimal(root);
            }
        });

        this.rgSexo.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_macho_formadocao:
                    this.animal.setSexo(Sexo.MACHO);
                    break;
                case R.id.rb_femea_formadocao:
                    this.animal.setSexo(Sexo.FEMEA);
                    break;
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
        this.tipoAnimal = v.findViewById(R.id.sp_tipoanimal_formadocao);
        this.raca = v.findViewById(R.id.et_raca_formadocao);
        this.rgSexo = v.findViewById(R.id.rg_sexo_formadocao);
        this.nome = v.findViewById(R.id.et_nome_formadocao);
        this.nascimento = v.findViewById(R.id.et_nascimento_formadocao);
        this.descricao = v.findViewById(R.id.et_descricao_formadocao);
        this.vacinas = v.findViewById(R.id.et_vacinas_formadocao);
        this.castrado = v.findViewById(R.id.cb_castrado_formadocao);
        this.salvar = v.findViewById(R.id.bt_salvar_formadocao);
        this.btnFoto = v.findViewById(R.id.btn_foto_form_adocao);
        this.imgMessage = v.findViewById(R.id.img_message_form_adocao);
        this.imgMessage.setVisibility(View.GONE);

        this.tipoAnimal.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, TipoAnimal.ANIMAIS));
        this.retrofit = RetrofitConfig.generateRetrofit();
        this.animalService = this.retrofit.create(AnimalService.class);

        if (this.animal.getId() != null) {
            this.carregarInputs(v);
        }
    }

    private void carregarInputs(View v) {
        for (int i = 0; i < this.tipoAnimal.getCount(); i++) {
            if (this.tipoAnimal.getItemAtPosition(i).equals(this.animal.getTipoAnimal())) {
                this.tipoAnimal.setSelection(i);
                break;
            }
        }
        this.raca.setText(this.animal.getRaca());
        this.nome.setText(this.animal.getNome());
        this.nascimento.setText(this.animal.getDataNascimento() == null ? null : Integer.toString(this.animal.getDataNascimento()));
        this.descricao.setText(this.animal.getDescricao());
        this.vacinas.setText(this.animal.getVacinas());
        this.castrado.setChecked(this.animal.getCastrado());
        RadioButton rb;
        if (Sexo.MACHO.equals(this.animal.getSexo())) {
            rb = v.findViewById(R.id.rb_macho_formadocao);
        } else {
            rb = v.findViewById(R.id.rb_femea_formadocao);
        }
        rb.setChecked(true);
    }

    private void cadastrarAnimal(View v) {
        this.animalService.cadastrarAnimal(this.animal).enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idanimal", response.body().getId());
                    Navigation.findNavController(v).navigate(R.id.action_nav_form_animal_adocao_to_nav_animal_adocao, bundle);
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }

    private void editarAnimal() {
        this.animalService.editarAnimal(this.animal).enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }
}
