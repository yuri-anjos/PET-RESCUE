package com.example.petrescue.domain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.R;
import com.example.petrescue.domain.Vaquinha;

import java.util.List;

public class AdapterVaquinha extends RecyclerView.Adapter<AdapterVaquinha.ViewHolderVaquinha> {

    private List<Vaquinha> data;
    private OnVaquinhaListener vaquinhaListener;

        public AdapterVaquinha(List<Vaquinha> data, OnVaquinhaListener onVaquinhaListener) {
        this.data = data;
        this.vaquinhaListener=onVaquinhaListener;
    }

    @NonNull
    @Override
    public ViewHolderVaquinha onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_vaquinhas, parent, false);
        return new ViewHolderVaquinha(view, vaquinhaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVaquinha holder, int position) {
        Vaquinha vaquinha = this.data.get(position);
        holder.titulo.setText(vaquinha.getTitulo());
        holder.descricao.setText(vaquinha.getDescricao());
        holder.inicio.setText(vaquinha.getInicio().toString());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolderVaquinha extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titulo, descricao, inicio;
        OnVaquinhaListener onVaquinhaListener;

        public ViewHolderVaquinha(@NonNull View itemView, OnVaquinhaListener onVaquinhaListener) {
            super(itemView);

            titulo = itemView.findViewById(R.id.tv_titulo_itemlistavaquinha);
            descricao = itemView.findViewById(R.id.tv_descricao_itemlistavaquinha);
            inicio = itemView.findViewById(R.id.tv_inicio_itemlistavaquinha);

            this.onVaquinhaListener = onVaquinhaListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onVaquinhaListener.onVaquinhaClick(getAdapterPosition());
        }
    }

    public interface OnVaquinhaListener{
        void onVaquinhaClick(int position);
    }
}
