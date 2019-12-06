package com.example.venadostest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.venadostest.R;
import com.example.venadostest.utils.RoundedTransformation;
import com.example.venadostest.vo.Jugador;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JugadoresAdapter extends BaseAdapter {

    private Context context;
    List<Jugador> lista;

    public JugadoresAdapter(Context context, List<Jugador> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imgJugador = (ImageView) view.findViewById(R.id.img_jugador);
        TextView nombreJugador = (TextView) view.findViewById(R.id.tv_nombre);
        TextView posicionJugador = (TextView)view.findViewById(R.id.tv_posicion);

        final Jugador jugador = (Jugador) getItem(position);

        nombreJugador.setText(jugador.getName());
        posicionJugador.setText(jugador.getPosition());
        Picasso.with(context)
                .load(jugador.getImage())
                .transform(new RoundedTransformation(100, 0))
                .fit()
                .into(imgJugador);

        return view;
    }
}