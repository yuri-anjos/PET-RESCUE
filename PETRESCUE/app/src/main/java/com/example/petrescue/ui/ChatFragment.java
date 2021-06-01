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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Conversa;
import com.example.petrescue.domain.Mensagem;
import com.example.petrescue.domain.adapter.AdapterConversa;
import com.example.petrescue.domain.adapter.AdapterMensagem;
import com.example.petrescue.domain.enums.TipoUsuario;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.CircleImageTransform;
import com.example.petrescue.service.ConversaService;
import com.example.petrescue.service.MensagemService;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.RoundedCornersTransform;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterMensagem adapterMensagem;
    private Conversa conversa;
    private List<Mensagem> listaMensagens;
    private Integer idconversa;

    private Retrofit retrofit;
    private ConversaService conversaService;
    private MensagemService mensagemService;

    private Mensagem mensagem;
    private TextInputEditText textoMensagem;
    private Button enviarMsg;

    private ImageView foto;
    private TextView nome;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        this.idconversa = getArguments().getInt("idconversa");

        this.inicializarComponentes(v);

        this.enviarMsg.setOnClickListener(v1 -> {
            this.mensagem = new Mensagem();
            this.mensagem.setTexto(this.textoMensagem.getText().toString());
            this.mensagem.setIdConversa(this.idconversa);
            this.mensagem.setIdAutor(ControleActivity.USUARIO.getId());
            this.mensagemService.cadastrarMensagem(this.mensagem).enqueue(new Callback<Mensagem>() {
                @Override
                public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                    if (response.isSuccessful()) {
                        listaMensagens.add(response.body());
                        adapterMensagem.notifyDataSetChanged();
                        textoMensagem.setText("");
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<Mensagem> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.toString());
                }
            });
        });
        return v;
    }

    private void inicializarComponentes(View v) {
        this.nome = v.findViewById(R.id.tv_nome_chat);
        this.foto = v.findViewById(R.id.iv_foto_chat);
        this.recyclerView = v.findViewById(R.id.rv_listamensagens);
        this.textoMensagem = v.findViewById(R.id.et_text_mensagem_chat);
        this.enviarMsg = v.findViewById(R.id.bt_enviar_chat);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.conversaService = this.retrofit.create(ConversaService.class);
        this.mensagemService = this.retrofit.create(MensagemService.class);

        this.listaMensagens = new ArrayList<>();
        this.adapterMensagem = new AdapterMensagem(this.listaMensagens);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.adapterMensagem);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.conversaService.buscarConversaId(this.idconversa, ControleActivity.USUARIO.getId()).enqueue(new Callback<Conversa>() {
            @Override
            public void onResponse(Call<Conversa> call, Response<Conversa> response) {
                if (response.isSuccessful()) {
                    conversa = response.body();
                    carregarCampos();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Conversa> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.toString());
            }
        });

        this.mensagemService.buscarMensagensConversaId(this.idconversa).enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    listaMensagens.clear();
                    listaMensagens.addAll(response.body());
                    adapterMensagem.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.toString());
            }
        });
    }

    private void carregarCampos() {
        this.nome.setText(ControleActivity.USUARIO.getId().equals(this.conversa.getIdUsuarioUm()) ? this.conversa.getNomeUsuarioDois() : this.conversa.getNomeUsuarioUm());
        if (this.conversa.getFoto() != null && this.conversa.getFoto().length() > 0) {
            Picasso.get()
                    .load(this.conversa.getFoto()).transform(new CircleImageTransform())
                    .placeholder(R.drawable.perfil_icon)
                    .error(R.drawable.perfil_icon)
                    .resize(64, 64)
                    .centerCrop()
                    .into(this.foto);
        }
    }
}
