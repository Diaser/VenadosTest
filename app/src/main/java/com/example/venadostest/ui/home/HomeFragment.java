package com.example.venadostest.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import com.example.venadostest.R;
import com.example.venadostest.adapters.PartidosAdapter;
import com.example.venadostest.conn.AppConnection;
import com.example.venadostest.vo.PartidoResumen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    PartidosAdapter partidosAdapter = null;
    List<PartidoResumen> listaPartidos= null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        recyclerView = (RecyclerView)root.findViewById(R.id.recycler_view);
        Log.i("MyLog","onCreate Home");
        if(getArguments()!=null){
            if(getArguments().getSerializable("ARRAY")!=null) {
                try {
                    Log.i("MyLog","Argumentos");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    listaPartidos = (ArrayList)getArguments().getSerializable("ARRAY");
                    Collections.sort(listaPartidos);
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

        recyclerView.setHasFixedSize(true);

    }


}