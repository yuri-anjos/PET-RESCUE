package com.example.petrescue.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Animal;
import com.example.petrescue.domain.enums.SituacaoAdocao;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalService;
import com.example.petrescue.service.RetrofitConfig;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnimalAdocaoFragment extends Fragment {

    private Integer idanimal;
    private Animal animal;
    private Retrofit retrofit;
    private AnimalService animalService;

    private Button btEditar;
    private Button btAdotar;
    private Button btAcessarUsuario;
    private ImageView foto;
    private TextInputEditText descricao;
    private TextInputEditText vacinas;
    private TextView nome;
    private LinearLayout dono;
    private TextView raca;
    private TextView idade;
    private TextView sexo;
    private TextView castrado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_animal_adocao, container, false);
        this.idanimal = getArguments().getInt("idanimal");
        this.inicializaComponentes(v);

        this.btEditar.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("animal", this.animal);
            Navigation.findNavController(v).navigate(R.id.action_nav_animal_adocao_to_nav_form_animal_adocao, bundle);
        });

        this.btAdotar.setOnClickListener(v1 -> {
            this.animalService.adotarAnimal(this.idanimal).enqueue(new Callback<Animal>() {
                @Override
                public void onResponse(Call<Animal> call, Response<Animal> response) {
                    if (response.isSuccessful()) {
                        animal = response.body();
                        atualizaCampos();
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<Animal> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.toString());
                }
            });
        });

        this.btAcessarUsuario.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idusuario", this.animal.getIdUsuario());
            Navigation.findNavController(v).navigate(R.id.action_nav_animal_adocao_to_nav_usuario, bundle);
        });

        return v;
    }

    private void inicializaComponentes(View v) {
        this.btAcessarUsuario = v.findViewById(R.id.bt_acessar_usuario_animaladocao);
        this.btEditar = v.findViewById(R.id.bt_editar_animaladocao);
        this.btAdotar = v.findViewById(R.id.bt_adotar_animaladocao);
        this.descricao = v.findViewById(R.id.tv_descricao_animaladocao);
        this.vacinas = v.findViewById(R.id.tv_vacinas_animaladocao);
        this.foto = v.findViewById(R.id.iv_foto_animaladocao);
        this.nome = v.findViewById(R.id.tv_nome_animaladocao);
        this.dono = v.findViewById(R.id.ll_dono_animaladocao);
        this.raca = v.findViewById(R.id.tv_raca_animaladocao);
        this.idade = v.findViewById(R.id.tv_idade_animaladocao);
        this.sexo = v.findViewById(R.id.tv_sexo_animaladocao);
        this.castrado = v.findViewById(R.id.tv_castrado_animaladocao);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.animalService = this.retrofit.create(AnimalService.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.animalService.buscarAnimalAdocaoId(this.idanimal).enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    animal = response.body();
                    atualizaCampos();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.toString());
            }
        });
    }

    private void atualizaCampos() {
        this.descricao.setText(this.animal.getDescricao());
        this.vacinas.setText(this.animal.getVacinas());
        this.nome.setText(this.animal.getNome());
        this.descricao.setText(this.animal.getDescricao());
        this.vacinas.setText(this.animal.getVacinas());
        this.raca.setText(this.animal.getRaca());
        this.idade.setText(Integer.toString(Calendar.getInstance().get(Calendar.YEAR) - this.animal.getDataNascimento()) + " ano(s)");
        this.sexo.setText(this.animal.getSexo().toString());
        this.castrado.setText(this.animal.getCastrado() == true ? "Castrado" : "N??o castrado");
        this.btAcessarUsuario.setText("Acesse o perfil de " + this.animal.getNomeUsuario());

        if (ControleActivity.USUARIO.getId().equals(this.animal.getIdUsuario())) {
            this.btAcessarUsuario.setVisibility(View.GONE);
            if (SituacaoAdocao.ADOTADO.equals(this.animal.getSituacaoAdocao())) {
                this.dono.setVisibility(View.GONE);
            } else {
                this.dono.setVisibility(View.VISIBLE);
            }
        } else {
            this.dono.setVisibility(View.GONE);
            this.btAcessarUsuario.setVisibility(View.VISIBLE);
        }

        if(this.animal.getFoto() != null) {
            byte[] byteArray = Base64.decode(this.animal.getFoto(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            foto.setImageBitmap(bitmap);
            foto.setRotation(90);
        }
    }
}