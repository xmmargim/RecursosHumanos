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

public class adapterEmpleado extends FirestoreRecyclerAdapter<Empleado,
        adapterEmpleado.ViewHolder> {

    private Context frAct;

// private final OnLugaresInteractionListener mListener;

    public adapterEmpleado(FragmentActivity activity, @NonNull
            FirestoreRecyclerOptions<Empleado> options) {
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
                                    @NonNull Empleado empleado) {
        holder.mNombre.setText(empleado.nombre);
        holder.mApellidos.setText(empleado.apellidos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNombre;
        public final TextView mApellidos;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mNombre = (TextView) mView.findViewById(R.id.txtNombre);
            mApellidos = (TextView) mView.findViewById(R.id.txtApellidos);

        }
    }
}