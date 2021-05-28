package com.example.petrescue.domain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Mensagem;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterMensagem extends RecyclerView.Adapter<AdapterMensagem.ViewHolderMensagem> {

    private List<Mensagem> data;

    public AdapterMensagem(List<Mensagem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolderMensagem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_mensagem, parent, false);
        return new ViewHolderMensagem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMensagem holder, int position) {
        Mensagem mensagem = this.data.get(position);
        holder.nome.setText(mensagem.getNomeAutor() + ":");
        holder.mensagem.setText(mensagem.getTexto());
        holder.horario.setText(new SimpleDateFormat("hh:mm").format(mensagem.getHorario()));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolderMensagem extends RecyclerView.ViewHolder {

        TextView nome, mensagem, horario;

        public ViewHolderMensagem(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.tv_nome_itemlistamensagem);
            mensagem = itemView.findViewById(R.id.tv_mensagem_itemlistamensagem);
            horario = itemView.findViewById(R.id.tv_horario_itemlistamensagem);

        }
    }
}