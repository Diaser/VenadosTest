package com.example.venadostest.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.venadostest.MainActivity;
import com.example.venadostest.R;
import com.example.venadostest.adapters.PartidosAdapter;
import com.example.venadostest.vo.PartidoResumen;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {


    PartidosAdapter partidosAdapter = null;
    List<PartidoResumen> listaPartidos= null;
    private Unbinder unbinder;
    Context context;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        context = getContext();
        Log.i("MyLog","onCreate Home");
        if(getArguments()!=null){
            if(getArguments().getSerializable("ARRAY")!=null) {
                listaPartidos = (ArrayList)getArguments().getSerializable("ARRAY");
                fill();
            }
        }else if(MainActivity.listaMX != null && MainActivity.listaMX.size()>0){
            listaPartidos = MainActivity.listaMX;
            fill();
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setHasFixedSize(true);
        MainActivity.tabLayout.setVisibility(View.VISIBLE);
    }

    private void fill(){
        try {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            Collections.sort(listaPartidos);
            partidosAdapter = new PartidosAdapter(listaPartidos);
            recyclerView.setAdapter(partidosAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}