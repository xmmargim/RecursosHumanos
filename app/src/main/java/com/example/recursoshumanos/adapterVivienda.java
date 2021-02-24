package com.example.recursoshumanos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

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
                .inflate(R.layout.empleado_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position,
                                    @NonNull Vivienda vivienda) {
        holder.mNombre.setText(vivienda.nombre);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNombre;
        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mNombre = (TextView) mView.findViewById(R.id.txtNombre);

        }
    }
}