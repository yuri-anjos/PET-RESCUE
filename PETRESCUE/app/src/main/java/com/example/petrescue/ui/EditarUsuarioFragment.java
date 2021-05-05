package com.example.petrescue.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petrescue.R;
import com.example.petrescue.domain.Usuario;

public class EditarUsuarioFragment extends Fragment {

    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_editar_usuario, container, false);

//        this.usuario = getArguments().getParcelable("usuario");
//        Toast.makeText(getActivity(), this.usuario.getNome(), Toast.LENGTH_SHORT).show();


        return root;
    }
}
