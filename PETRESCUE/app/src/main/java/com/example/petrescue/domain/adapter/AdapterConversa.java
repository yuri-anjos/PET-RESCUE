package com.example.petrescue.domain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Conversa;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdapterConversa extends RecyclerView.Adapter<AdapterConversa.ViewHolderConversa> {

    private List<Conversa> data;
    private OnConversaListener conversaListener;

    public AdapterConversa(List<Conversa> data, OnConversaListener onConversaListener) {
        this.data = data;
        this.conversaListener = onConversaListener;
    }

    @NonNull
    @Override
    public ViewHolderConversa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_conversa, parent, false);
        return new ViewHolderConversa(view, conversaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderConversa holder, int position) {
        Conversa conversa = this.data.get(position);
        holder.nome.setText(ControleActivity.USUARIO.getId() != conversa.getIdUsuarioUm() ? conversa.getNomeUsuarioUm() : conversa.getNomeUsuarioDois());
        // holder.foto.setImageBitmap();
        if (conversa.getUltimaMensagem() != null) {
            holder.mensagem.setText(conversa.getUltimaMensagem().getTexto());
            holder.horario.setText(new SimpleDateFormat("hh:mm dd/MM").format(conversa.getUltimaMensagem().getHorario()));
        }else{
            holder.mensagem.setText("");
            holder.horario.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolderConversa extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView foto;
        TextView nome, mensagem, horario;
        OnConversaListener onConversaListener;

        public ViewHolderConversa(@NonNull View itemView, OnConversaListener onConversaListener) {
            super(itemView);

            foto = itemView.findViewById(R.id.iv_foto_itemlistaconversa);
            nome = itemView.findViewById(R.id.tv_nome_itemlistaconversa);
            mensagem = itemView.findViewById(R.id.tv_mensagem_itemlistaconversa);
            horario = itemView.findViewById(R.id.tv_horario_itemlistaconversa);

            this.onConversaListener = onConversaListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onConversaListener.onConversaClick(getAdapterPosition());
        }
    }

    public interface OnConversaListener {
        void onConversaClick(int position);
    }
}

