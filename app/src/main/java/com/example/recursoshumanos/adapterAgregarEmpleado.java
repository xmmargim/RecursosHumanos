package com.example.recursoshumanos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class adapterAgregarEmpleado extends FirestoreRecyclerAdapter<Empleado,
        adapterAgregarEmpleado.ViewHolder> {

    private Context frAct;
    private String id;
    private FirebaseFirestore miFirebase;

// private final OnLugaresInteractionListener mListener;

    public adapterAgregarEmpleado(FragmentActivity activity, @NonNull
            FirestoreRecyclerOptions<Empleado> options, String id) {
        super(options);
        frAct = activity;
        this.id = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agregarempleado_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position,
                                    @NonNull Empleado empleado) {
        holder.mNombre.setText(empleado.nombre);
        holder.mApellidos.setText(empleado.apellidos);
        holder.mAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miFirebase = FirebaseFirestore.getInstance();
                final DocumentReference doc = miFirebase.collection("viviendas").document(id);
                doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            /*Vivienda vivienda = documentSnapshot.toObject(Vivienda.class);
                            vivienda.residente = holder.mNombre.getText().toString();*/
                            doc.update("residente",holder.mNombre.getText().toString());

                            NavController navController =
                                    Navigation.findNavController((Activity) frAct, R.id.nav_host_fragment);
                            NavigationView navigationView = ((Activity)
                                    frAct).findViewById(R.id.nav_view);
                            NavigationUI.setupWithNavController(navigationView, navController);
                            navController.navigate(R.id.fragmentVivienda);

                        }
                    }
                });


            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNombre;
        public final TextView mApellidos;
        public final Button mAgregar;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mNombre = (TextView) mView.findViewById(R.id.txtNombre);
            mApellidos = (TextView) mView.findViewById(R.id.txtResidente);
            mAgregar = (Button) mView.findViewById(R.id.btnAgregar);
        }
    }
}