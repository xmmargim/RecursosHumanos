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
 * Use the {@link fragmentVivienda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentVivienda extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rvVivienda;
    FirebaseFirestore miFirebase;
    adapterVivienda adapter;
    View mview;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentVivienda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentVivienda.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentVivienda newInstance(String param1, String param2) {
        fragmentVivienda fragment = new fragmentVivienda();
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
        View root = inflater.inflate(R.layout.fragment_vivienda, container, false);
        Context context = root.getContext();
        rvVivienda = (RecyclerView) root;
        rvVivienda.setLayoutManager(new LinearLayoutManager(context));
        miFirebase = FirebaseFirestore.getInstance();

        Query query = miFirebase.collection("viviendas");
        FirestoreRecyclerOptions<Vivienda> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Vivienda>().setQuery(query, Vivienda.class).build();
        adapter = new adapterVivienda(getActivity(), firestoreRecyclerOptions);
        adapter.notifyDataSetChanged();
        rvVivienda.setAdapter(adapter);

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