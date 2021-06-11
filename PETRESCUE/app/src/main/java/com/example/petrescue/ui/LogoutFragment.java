package com.example.petrescue.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petrescue.R;

public class LogoutFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.logout();
        getActivity().onBackPressed();
        return null;
    }

    private void logout() {
        new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.warning_icon)
                .setTitle("Sair")
                .setMessage("Tem certeza que deseja sair??")
                .setPositiveButton("Sim", (dialog, which) -> getActivity().finish())
                .setNegativeButton("Não", null)
                .show();
    }
}
