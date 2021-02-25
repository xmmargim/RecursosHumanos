package com.example.recursoshumanos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentAgregarEmpleado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentAgregarEmpleado extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rvAgregarEmpleado;
    FirebaseFirestore miFirebase;
    adapterAgregarEmpleado adapter;
    View mview;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentAgregarEmpleado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentAgregarEmpleado.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentAgregarEmpleado newInstance(String param1, String param2) {
        fragmentAgregarEmpleado fragment = new fragmentAgregarEmpleado();
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
        View root = inflater.inflate(R.layout.fragment_agregar_empleado, container, false);
        String id = getArguments().getString("idVivienda");
        Context context = root.getContext();
        rvAgregarEmpleado = (RecyclerView) root;
        rvAgregarEmpleado.setLayoutManager(new LinearLayoutManager(context));

        miFirebase = FirebaseFirestore.getInstance();

        Query query = miFirebase.collection("empleados");
        FirestoreRecyclerOptions<Empleado> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Empleado>().setQuery(query, Empleado.class).build();
        adapter = new adapterAgregarEmpleado(getActivity(), firestoreRecyclerOptions, id);
        adapter.notifyDataSetChanged();
        rvAgregarEmpleado.setAdapter(adapter);

        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}