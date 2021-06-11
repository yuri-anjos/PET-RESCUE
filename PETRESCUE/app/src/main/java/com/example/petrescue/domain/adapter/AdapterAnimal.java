package com.example.petrescue.domain.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.R;
import com.example.petrescue.domain.Animal;

import java.util.List;

public class AdapterAnimal extends RecyclerView.Adapter<AdapterAnimal.ViewHolderAnimal> {

    private List<Animal> data;
    private OnAnimalListener animalListener;

    public AdapterAnimal(List<Animal> data, OnAnimalListener onAnimalListener) {
        this.data = data;
        this.animalListener = onAnimalListener;
    }

    @NonNull
    @Override
    public ViewHolderAnimal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_adocoes, parent, false);
        return new ViewHolderAnimal(view, animalListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAnimal holder, int position) {
        Animal animal = this.data.get(position);

        holder.nome.setText(animal.getNome());
        holder.sitacaoAdocao.setText("Situacao: " + animal.getSituacaoAdocao().toString());
        holder.tipoAnimal.setText(animal.getTipoAnimal().toString());
        holder.sexo.setText(animal.getSexo().toString());
        if (animal.getFoto() != null) {
            byte[] imgBytes = Base64.decode(animal.getFoto(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            holder.foto.setImageBitmap(bitmap);
            holder.foto.setRotation(90);
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolderAnimal extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView foto;
        TextView nome, sitacaoAdocao, tipoAnimal, sexo;
        OnAnimalListener onAnimalListener;

        public ViewHolderAnimal(@NonNull View itemView, OnAnimalListener onAnimalListener) {
            super(itemView);

            foto = itemView.findViewById(R.id.iv_foto_animal_itemlistaadocao);
            nome = itemView.findViewById(R.id.tv_nome_animal_itemlistaadocao);
            sitacaoAdocao = itemView.findViewById(R.id.tv_situacao_adocao_animal_itemlistaadocao);
            tipoAnimal = itemView.findViewById(R.id.tv_tipo_animal_itemlistaadocao);
            sexo = itemView.findViewById(R.id.tv_sexo_animal_itemlistaadocao);

            this.onAnimalListener = onAnimalListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAnimalListener.onAnimalClick(getAdapterPosition());
        }
    }

    public interface OnAnimalListener {
        void onAnimalClick(int position);
    }
}
