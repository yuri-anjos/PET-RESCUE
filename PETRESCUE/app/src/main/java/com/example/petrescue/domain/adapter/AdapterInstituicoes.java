package com.example.petrescue.domain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.R;
import com.example.petrescue.domain.Usuario;
import com.example.petrescue.service.RoundedCornersTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterInstituicoes extends RecyclerView.Adapter<AdapterInstituicoes.ViewHolderInstituicao>{

    private List<Usuario> data;
    private AdapterInstituicoes.OnInstituicaoListener onInstituicaoListener;

    public AdapterInstituicoes(List<Usuario> data, OnInstituicaoListener onInstituicaoListener) {
        this.data = data;
        this.onInstituicaoListener = onInstituicaoListener;
    }

    @NonNull
    @Override
    public AdapterInstituicoes.ViewHolderInstituicao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_instituicao, parent, false);
        return new AdapterInstituicoes.ViewHolderInstituicao(view, onInstituicaoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInstituicoes.ViewHolderInstituicao holder, int position) {
        Usuario usuario = this.data.get(position);

        holder.nome.setText(usuario.getNome());
        holder.descricao.setText(usuario.getDescricao());
        if (usuario.getFoto() != null && usuario.getFoto().length() > 0) {
            Picasso.get()
                    .load(usuario.getFoto()).transform(new RoundedCornersTransform())
                    .placeholder(R.drawable.instituicoes_icon)
                    .error(R.drawable.instituicoes_icon)
                    .resize(150, 150)
                    .centerCrop()
                    .into(holder.foto);
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolderInstituicao extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView foto;
        TextView nome, descricao;
        AdapterInstituicoes.OnInstituicaoListener onInstituicaoListener;

        public ViewHolderInstituicao(@NonNull View itemView, AdapterInstituicoes.OnInstituicaoListener onInstituicaoListener) {
            super(itemView);

            nome = itemView.findViewById(R.id.tv_nome_itemlistainstituicao);
            descricao = itemView.findViewById(R.id.tv_descricao_itemlistainstituicao);
            foto = itemView.findViewById(R.id.iv_foto_itemlistainstituicao);

            this.onInstituicaoListener = onInstituicaoListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onInstituicaoListener.onInstituicaoListener(getAdapterPosition());
        }
    }

    public interface OnInstituicaoListener {
        void onInstituicaoListener(int position);
    }
}