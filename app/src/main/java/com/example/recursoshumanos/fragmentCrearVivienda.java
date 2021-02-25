package com.example.recursoshumanos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentCrearVivienda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentCrearVivienda extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore firebaseFirestore;
    private Button btnAddVivienda;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentCrearVivienda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment crearVivienda.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentCrearVivienda newInstance(String param1, String param2) {
        fragmentCrearVivienda fragment = new fragmentCrearVivienda();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseFirestore = firebaseFirestore.getInstance();
        final View root = inflater.inflate(R.layout.fragment_crear_vivienda,container,false);
        btnAddVivienda = root.findViewById(R.id.btnCrearVivienda);
        btnAddVivienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearVivienda(root);
                Toast.makeText(getContext(), "Se ha añadido correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
    public void crearVivienda(View v) {
        TextView editNombre = v.findViewById(R.id.editNombre);
        String newNombre = editNombre.getText().toString();
        String newResidente = "Agregar Residente";

        Map<String, Object> vivienda = new HashMap<>();
        vivienda.put("nombre", newNombre);
        vivienda.put("residente","Agregar Residente");

        firebaseFirestore.collection("viviendas")
                .add(vivienda)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}