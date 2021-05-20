package com.example.petrescue.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Animal;
import com.example.petrescue.domain.CarteiraDTO;
import com.example.petrescue.domain.Usuario;
import com.example.petrescue.domain.Vaquinha;
import com.example.petrescue.domain.adapter.AdapterAnimal;
import com.example.petrescue.domain.adapter.AdapterVaquinha;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalService;
import com.example.petrescue.service.ConversaService;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;
import com.example.petrescue.service.VaquinhaService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsuarioFragment extends Fragment implements AdapterAnimal.OnAnimalListener, AdapterVaquinha.OnVaquinhaListener {

    private LinearLayout linearLayoutSaldo;
    private ImageView foto;
    private TextView nome;
    private TextView email;
    private TextView saldo;
    private TextInputEditText saldoAdicional;
    private Button adicionarSaldo;
    private Button editar;
    private Button conversar;

    private View view;
    private Retrofit retrofit;

    private UsuarioService usuarioService;
    private Usuario usuario;
    private Integer idusuario;

    private RecyclerView recyclerViewVaquinha;
    private AdapterVaquinha vaquinhaAdapter;
    private List<Vaquinha> listaVaquinha;
    private VaquinhaService vaquinhaService;

    private RecyclerView recyclerViewAnimal;
    private AdapterAnimal animalAdapter;
    private List<Animal> listaAnimal;
    private AnimalService animalService;

    private ConversaService conversaService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_usuario, container, false);

        if (getArguments() != null) {
            this.idusuario = getArguments().getInt("idusuario");
        }

        this.inicializaComponentes(this.view);

        this.adicionarSaldo.setOnClickListener(v -> {
            this.usuarioService.depositarSaldo(new CarteiraDTO(this.usuario.getId(), Double.parseDouble(this.saldoAdicional.getText().toString()))).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        ControleActivity.USUARIO = response.body();
                        Toast.makeText(getActivity(), "Depósito Concluído!", Toast.LENGTH_LONG).show();
                        saldoAdicional.setText(Double.toString(0.0));
                        onResume();
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.toString());
                }
            });
        });

        this.editar.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_nav_usuario_to_nav_editar_usuario);
        });

        this.conversar.setOnClickListener(v -> {
            this.conversaService = this.retrofit.create(ConversaService.class);
            // buscar id da conversa e trocar de tela
        });

        return this.view;
    }

    private void inicializaComponentes(View v) {
        this.recyclerViewAnimal = v.findViewById(R.id.rv_animais_usuario);
        this.recyclerViewVaquinha = v.findViewById(R.id.rv_vaquinhas_usuario);

        this.linearLayoutSaldo = v.findViewById(R.id.ll_saldo_usuario);
        this.foto = v.findViewById(R.id.iv_foto_usuario);
        this.nome = v.findViewById(R.id.tv_nome_usuario);
        this.email = v.findViewById(R.id.tv_email_usuario);
        this.saldo = v.findViewById(R.id.tv_saldo_usuario);
        this.saldoAdicional = v.findViewById(R.id.et_saldo_adicional_usuario);
        this.adicionarSaldo = v.findViewById(R.id.bt_adicionar_saldo_usuario);
        this.editar = v.findViewById(R.id.bt_editar_usuario);
        this.conversar = v.findViewById(R.id.bt_conversar_usuario);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.usuarioService = this.retrofit.create(UsuarioService.class);
        this.vaquinhaService = this.retrofit.create(VaquinhaService.class);
        this.animalService = this.retrofit.create(AnimalService.class);

        this.listaVaquinha = new ArrayList<>();
        this.vaquinhaAdapter = new AdapterVaquinha(this.listaVaquinha, this);
        this.recyclerViewVaquinha.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerViewVaquinha.setAdapter(this.vaquinhaAdapter);

        this.listaAnimal = new ArrayList<>();
        this.animalAdapter = new AdapterAnimal(this.listaAnimal, this);
        this.recyclerViewAnimal.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        this.recyclerViewAnimal.setAdapter(this.animalAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (this.idusuario != null && this.idusuario != ControleActivity.USUARIO.getId()) {
            this.usuarioService.buscarUsuarioId(this.idusuario).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        usuario = response.body();
                        atualizaCampos();
                        buscarListas();
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.toString());
                }
            });
        } else {
            this.usuario = ControleActivity.USUARIO;
            this.atualizaCampos();
            buscarListas();
        }
    }

    private void atualizaCampos() {
        this.nome.setText(this.usuario.getNome());
        this.email.setText(this.usuario.getEmail());
//        this.foto.setImageBitmap();
        if (ControleActivity.USUARIO.getId().equals(this.usuario.getId())) {
            this.linearLayoutSaldo.setVisibility(View.VISIBLE);
            this.editar.setVisibility(View.VISIBLE);
            this.conversar.setVisibility(View.GONE);
            this.saldo.setText(Double.toString(this.usuario.getSaldo()));
        } else {
            this.linearLayoutSaldo.setVisibility(View.GONE);
            this.editar.setVisibility(View.GONE);
            this.conversar.setVisibility(View.VISIBLE);
        }
    }

    private void buscarListas() {
        this.vaquinhaService.buscarVaquinhasUsuarioId(this.usuario.getId()).enqueue(new Callback<List<Vaquinha>>() {
            @Override
            public void onResponse(Call<List<Vaquinha>> call, Response<List<Vaquinha>> response) {
                if (response.isSuccessful()) {
                    listaVaquinha.clear();
                    listaVaquinha.addAll(response.body());
                    if (listaVaquinha.isEmpty()) {
                        recyclerViewVaquinha.setVisibility(View.GONE);
                    } else {
                        recyclerViewVaquinha.setVisibility(View.VISIBLE);
                    }
                    vaquinhaAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Vaquinha>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.toString());
            }
        });

        this.animalService.buscarAnimaisAdocaoUsuarioId(this.usuario.getId()).enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    listaAnimal.clear();
                    listaAnimal.addAll(response.body());
                    if (listaAnimal.isEmpty()) {
                        recyclerViewAnimal.setVisibility(View.GONE);
                    } else {
                        recyclerViewAnimal.setVisibility(View.VISIBLE);
                    }
                    animalAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.toString());
            }
        });
    }

    @Override
    public void onAnimalClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idanimal", this.listaAnimal.get(position).getId());
        Navigation.findNavController(this.view).navigate(R.id.action_nav_usuario_to_nav_animal_adocao, bundle);
    }

    @Override
    public void onVaquinhaClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idvaquinha", this.listaVaquinha.get(position).getId());
        Navigation.findNavController(this.view).navigate(R.id.action_nav_usuario_to_nav_vaquinha, bundle);
    }
}