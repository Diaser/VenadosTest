package com.example.venadostest.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.venadostest.MainActivity;
import com.example.venadostest.R;
import com.example.venadostest.adapters.PartidosAdapter;
import com.example.venadostest.vo.PartidoResumen;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    RecyclerView recyclerView;
    PartidosAdapter partidosAdapter = null;
    List<PartidoResumen> listaPartidos= null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = (RecyclerView)root.findViewById(R.id.recycler_view);
        if(getArguments()!=null){
            if(getArguments().getSerializable("ARRAY")!=null) {
                try {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    listaPartidos = (ArrayList)getArguments().getSerializable("ARRAY");
                    partidosAdapter = new PartidosAdapter(listaPartidos);
                    recyclerView.setAdapter(partidosAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.tabLayout.setVisibility(View.VISIBLE);
    }
}