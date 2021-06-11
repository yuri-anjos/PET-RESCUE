package com.example.petrescue.domain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.R;
import com.example.petrescue.domain.AnimalPIN;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterPin extends RecyclerView.Adapter<AdapterPin.ViewHolderPin>{

    private List<AnimalPIN> data;
    private OnPinListener onPinListener;

    public AdapterPin(List<AnimalPIN> data, OnPinListener onPinListener) {
        this.data = data;
        this.onPinListener = onPinListener;
    }

    @NonNull
    @Override
    public ViewHolderPin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_pin, parent, false);
        return new ViewHolderPin(view, onPinListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderPin holder, int position) {
        AnimalPIN animalPin = this.data.get(position);
        holder.distancia.setText("10m");
        holder.tipoPin.setText(String.valueOf(animalPin.getTipoPIN()));
        holder.tipoAnimal.setText(String.valueOf(animalPin.getTipoAnimal()));
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolderPin extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView distancia;
        TextView tipoPin;
        TextView tipoAnimal;
        OnPinListener onPinListener;

        public ViewHolderPin(@NonNull View itemView, OnPinListener onPinListener) {
            super(itemView);

            distancia = itemView.findViewById(R.id.tv_distancia);
            tipoPin = itemView.findViewById(R.id.tv_tipo_pin_lista);
            tipoAnimal = itemView.findViewById(R.id.tv_tipo_animal_lista);

            this.onPinListener = onPinListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onPinListener.onPinClick(getAdapterPosition());
        }
    }

    public interface OnPinListener {
        void onPinClick(int position);
    }

}


