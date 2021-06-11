package com.example.petrescue.domain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.AnimalPIN;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        LatLng userLatLng = new LatLng(-27.8339308, -50.1366267);
        LatLng pinLatLng = new LatLng(animalPin.getLocalizacao().getLatitude(), animalPin.getLocalizacao().getLongitude());

        holder.distancia.setText(new DecimalFormat("##.#").format(distanceBetween(pinLatLng, userLatLng)));
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

    public double distanceBetween(LatLng origin, LatLng destination) {

        // Conversão de graus pra radianos das latitudes
        double firstLatToRad = Math.toRadians(origin.latitude);
        double secondLatToRad = Math.toRadians(destination.latitude);

        // Diferença das longitudes
        double deltaLongitudeInRad = Math.toRadians(destination.longitude - origin.longitude);

        // Cálculo da distância entre os pontos

        return Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)
                * Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
                * Math.sin(secondLatToRad))
                * 6371;
    }

}


