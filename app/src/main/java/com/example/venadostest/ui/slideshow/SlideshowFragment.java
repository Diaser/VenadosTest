package com.example.venadostest.ui.slideshow;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.venadostest.MainActivity;
import com.example.venadostest.R;
import com.example.venadostest.adapters.JugadoresAdapter;
import com.example.venadostest.conn.AppConnection;
import com.example.venadostest.utils.RoundedTransformation;
import com.example.venadostest.vo.Jugador;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private final String url = "https://venados.dacodes.mx/api/players";
    List<Jugador> listJugadores = null;
    Context context;
    PopupWindow popupWindow;
    @BindView(R.id.grid) GridView gridView;
    @BindView(R.id.linear_layout_jugadores) LinearLayout linearLayout;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        unbinder = ButterKnife.bind(this, root);

        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        context = getActivity();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new AsnckJugadores().execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowDetails(listJugadores.get(position));
            }
        });
        MainActivity.tabLayout.setVisibility(View.GONE);
    }

    private class AsnckJugadores extends AsyncTask<String, Void, Void> {

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            jsonObject = AppConnection.conectWebService(url);

            try {
                if (jsonObject.has("data"))
                    jsonObject = jsonObject.getJSONObject("data");
                    if (jsonObject.has("team"))
                        jsonObject = jsonObject.getJSONObject("team");
                        if(jsonObject.has("forwards")){
                            jsonArray = jsonObject.getJSONArray("forwards");
                            listJugadores = new ArrayList<>();
                            for(int i=0;i<jsonArray.length(); i++){
                                JSONObject jsonPlayer = jsonArray.getJSONObject(i);
                                if(jsonPlayer != null){
                                    Jugador jugador =  new Jugador();
                                    jugador.setName(jsonPlayer.getString("name"));
                                    jugador.setFirst_surname(jsonPlayer.getString("first_surname"));
                                    jugador.setSecond_surname(jsonPlayer.getString("second_surname"));
                                    if(jsonPlayer.has("birthday")){
                                        String dateStr = jsonPlayer.getString("birthday");
                                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                                        Date birthDate = sdf.parse(dateStr);
                                        jugador.setBirthday(birthDate);
                                    }
                                    jugador.setBirth_place(jsonPlayer.getString("birth_place"));
                                    jugador.setWeight((float) jsonPlayer.getDouble("weight"));
                                    jugador.setHeight((float) jsonPlayer.getDouble("height"));
                                    jugador.setPosition(jsonPlayer.getString("position"));
                                    jugador.setPosition_short(jsonPlayer.getString("position_short"));
                                    jugador.setNumber(jsonPlayer.getInt("number"));
                                    jugador.setLast_team(jsonPlayer.getString("last_team"));
                                    jugador.setImage(jsonPlayer.getString("image"));

                                    listJugadores.add(jugador);
                                }
                            }
                        }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fillGrid();
        }
    }

    public void fillGrid(){
        if(listJugadores!=null && listJugadores.size()>0){
            JugadoresAdapter jugadoresAdapter =  new JugadoresAdapter(context, listJugadores);
            gridView.setAdapter(jugadoresAdapter);
        }
    }

    private void ShowDetails(Jugador player){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.players_alert,null);
        popupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        if(Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }

        TextView tvNombre = customView.findViewById(R.id.tv_nombre);
        TextView tvPosicion = customView.findViewById(R.id.tv_posicion);
        ImageView imgSalir = customView.findViewById(R.id.img_salir);

        TextView tvFechaNac = customView.findViewById(R.id.tv_fecha_nac);
        TextView tvLugarNac = customView.findViewById(R.id.tv_lugar_nac);
        TextView tvPeso = customView.findViewById(R.id.tv_peso);
        TextView tvAltura = customView.findViewById(R.id.tv_altura);
        TextView tvEquipoAnt = customView.findViewById(R.id.tv_equipo_anterior);
        ImageView img_player = customView.findViewById(R.id.img_jugador);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        tvNombre.setText(player.getFirst_surname());
        tvPosicion.setText(player.getPosition());
        tvFechaNac.setText(dateFormat.format(player.getBirthday()));
        tvLugarNac.setText(player.getBirth_place());
        tvPeso.setText(String.valueOf(player.getWeight()));
        tvAltura.setText(String.valueOf(player.getHeight()));
        tvEquipoAnt.setText(player.getLast_team());

        Picasso.with(context)
                .load(player.getImage())
                .transform(new RoundedTransformation(100, 0))
                .fit()
                .into(img_player);

        imgSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(linearLayout, Gravity.CENTER,0,0);
    }
}