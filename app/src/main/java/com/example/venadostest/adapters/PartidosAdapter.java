package com.example.venadostest.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.venadostest.R;
import com.example.venadostest.utils.MyUtils;
import com.example.venadostest.vo.PartidoResumen;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PartidosAdapter extends RecyclerView.Adapter<PartidosAdapter.PartidosViewHolder> {

    List<PartidoResumen> listaPartidos;
    public PartidosAdapter (List<PartidoResumen> listaPartidos){
        this.listaPartidos = listaPartidos;
        mesValue = null;
    }
    private static String mesValue;
    Context context = null;

    public static class PartidosViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView fecha;
        TextView equipo1;
        TextView equipo2;
        TextView marcador;
        ImageView img1;
        ImageView img2;
        TextView mes;
        LinearLayout line;


        PartidosViewHolder (View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);

            equipo1 = (TextView) itemView.findViewById(R.id.tv_equipo1);
            equipo2 = (TextView) itemView.findViewById(R.id.tv_equipo2);
            fecha = (TextView) itemView.findViewById(R.id.tv_fecha);
            img1 = (ImageView) itemView.findViewById(R.id.img_equipo1);
            img2 = (ImageView)itemView.findViewById(R.id.img_equipo2);
            marcador = (TextView) itemView.findViewById(R.id.tv_marcador);
            mes = (TextView) itemView.findViewById(R.id.tv_month);
            line = (LinearLayout)itemView.findViewById(R.id.lin_layout);
        }

    }

    @Override
    public int getItemCount() {
        return listaPartidos.size();
    }

    @NonNull
    @Override
    public PartidosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_partidos, viewGroup,false);
        PartidosViewHolder p = new PartidosViewHolder(view);
        context = viewGroup.getContext();
        return p;
    }

    @Override
    public void onBindViewHolder(@NonNull PartidosViewHolder partidosViewHolder, int position) {
        try {
        PartidoResumen myP = (PartidoResumen)listaPartidos.get(position);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM");
            String strDate = dateFormat.format(myP.getDate());
            Log.i("PartidosAdapterEj","Oponente: " + myP.getOpponent() + " fecha: " +strDate.replace(" ",""));
            Log.i("PartidosAdapterEj","posición: "+ position + " Mes: "+ mesValue);

            partidosViewHolder.equipo1.setText(myP.getOpponent());
            partidosViewHolder.equipo2.setText("Venados F.C.");
            partidosViewHolder.fecha.setText(strDate);
            partidosViewHolder.marcador.setText(myP.getAway_score() + " - " + myP.getHome_score());
            partidosViewHolder.mes.setText("");

            Picasso.with(context)
                    .load(myP.getOpponent_image())
                    .into(partidosViewHolder.img1);

            Picasso.with(context)
                    .load(myP.getImage())
                    .into(partidosViewHolder.img2);

            dateFormat = new SimpleDateFormat("MM");
            String value = dateFormat.format(myP.getDate());
            if(position == 0){
                mesValue = dateFormat.format(myP.getDate());
                partidosViewHolder.mes.setText(MyUtils.getMonth(Integer.parseInt(mesValue)));
                Log.i("PartidosAdapterEj","posición 0");
                partidosViewHolder.line.setVisibility(View.VISIBLE);
            }else if(!mesValue.equals(value)){
                Log.i("PartidosAdapterEj","Mes " + mesValue + "es diferente a Value : " + value);
                mesValue = dateFormat.format(myP.getDate());
                partidosViewHolder.mes.setText(MyUtils.getMonth(Integer.parseInt(mesValue)));
                partidosViewHolder.line.setVisibility(View.VISIBLE);
            }else{
                partidosViewHolder.mes.setText("");
                partidosViewHolder.line.setVisibility(View.GONE);
                Log.i("PartidosAdapterEj","GONE");
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.i("PartidosAdapterEj","Error" + e.getMessage());
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
