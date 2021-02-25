package com.example.recursoshumanos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;

public class adapterVivienda extends FirestoreRecyclerAdapter<Vivienda,
        adapterVivienda.ViewHolder> {

    private Context frAct;

// private final OnLugaresInteractionListener mListener;

    public adapterVivienda(FragmentActivity activity, @NonNull
            FirestoreRecyclerOptions<Vivienda> options) {
        super(options);
        frAct = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vivienda_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position,
                                    @NonNull final Vivienda vivienda) {
        holder.mNombre.setText(vivienda.nombre);
        holder.mResidente.setText(vivienda.residente);
        holder.mResidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentSnapshot lugarDocument =
                        getSnapshots().getSnapshot(holder.getAdapterPosition());
                final String id = vivienda.id;
                Bundle bundle = new Bundle();
                bundle.putString("idVivienda", lugarDocument.getId());
                NavController navController =
                        Navigation.findNavController((Activity) frAct, R.id.nav_host_fragment);
                NavigationView navigationView = ((Activity)
                        frAct).findViewById(R.id.nav_view);
                NavigationUI.setupWithNavController(navigationView, navController);
                navController.navigate(R.id.fragmentAgregarEmpleado, bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNombre;
        public final TextView mResidente;
        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mNombre = (TextView) mView.findViewById(R.id.txtNombre);
            mResidente = (TextView) mView.findViewById(R.id.txtResidente);

        }
    }
}