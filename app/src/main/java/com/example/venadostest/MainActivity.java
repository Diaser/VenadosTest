package com.example.venadostest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.venadostest.adapters.PartidosAdapter;
import com.example.venadostest.conn.AppConnection;
import com.example.venadostest.ui.gallery.GalleryFragment;
import com.example.venadostest.ui.home.HomeFragment;
import com.example.venadostest.ui.home.HomeViewModel;
import com.example.venadostest.vo.PartidoResumen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private HomeViewModel homeViewModel;
    PartidosAdapter partidosAdapter = null;
    HomeFragment homeFragment = null;
    List<PartidoResumen> listaAsenso  = null;
    List<PartidoResumen> listaMX = null;
    private final String partidos = "https://venados.dacodes.mx/api/games";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_lay);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast  t = null;
                switch (tab.getPosition()) {
                    case 0:

                        if(listaMX != null && listaMX.size()>0 ){

                            ArrayList<PartidoResumen> arrayList = new ArrayList<>(listaMX.size());
                            arrayList.addAll(listaMX);
                            Bundle args = new Bundle();
                            args.putSerializable("ARRAY",arrayList);

                            homeFragment = new HomeFragment();
                            FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();


                            fragmentTransaction.replace(R.id.nav_host_fragment, homeFragment);
                            fragmentTransaction.addToBackStack(null);
                            homeFragment.setArguments(args);
                            // Commit the transaction
                            fragmentTransaction.commit();
                        }else{
                            t = Toast.makeText(getApplicationContext(),"Espere un momento por favor",Toast.LENGTH_SHORT);
                            t.show();
                        }
                        break;
                    case 1:
                        ArrayList<PartidoResumen> arrayList = new ArrayList<>(listaAsenso.size());
                        arrayList.addAll(listaAsenso);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAY",arrayList);

                        GalleryFragment galleryFragment = new GalleryFragment();
                        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.nav_host_fragment, galleryFragment);
                        transaction.addToBackStack(null);
                        galleryFragment.setArguments(args);
                        transaction.commit();

                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //En nav_home estan las imagenes del menu de navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        new AsynckGetPartidos().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private class AsynckGetPartidos extends AsyncTask<String, Void, Void> {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("MyLog","PreExcecute");
        }

        @Override
        protected Void doInBackground(String... strings) {
            jsonObject = AppConnection.conectWebService(partidos);
            try{
                if(jsonObject.has("data")){
                    jsonObject = jsonObject.getJSONObject("data");
                    if(jsonObject.has("games")){
                        jsonArray = jsonObject.getJSONArray("games");
                        PartidoResumen resumen = null;
                        listaMX = new ArrayList<>();
                        listaAsenso= new ArrayList<>();
                        if(jsonArray!=null)
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject jpartido = jsonArray.getJSONObject(i);
                                resumen = new PartidoResumen();
                                if(jpartido.has("local"))
                                    resumen.setLocal(jpartido.getBoolean("local"));
                                if(jpartido.has("opponent"))
                                    resumen.setOpponent(jpartido.getString("opponent"));
                                if(jpartido.has("opponent_image")) {
                                    resumen.setOpponent_image(jpartido.getString("opponent_image"));
                                }
                                if(jpartido.has("image"))
                                {
                                    resumen.setImage(jpartido.getString("image"));
                                }
                                if(jpartido.has("datetime")){
                                    String dateStr = jpartido.getString("datetime");
                                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                                    Date birthDate = sdf.parse(dateStr);
                                    resumen.setDate(birthDate);
                                }
                                if(jpartido.has("league"))
                                    resumen.setLeague(jpartido.getString("league"));

                                if(jpartido.has("home_score"))
                                    resumen.setHome_score(jpartido.getString("home_score"));
                                if(jpartido.has("away_score"))
                                    resumen.setAway_score(jpartido.getString("away_score"));


                                if(resumen.getLeague().equals("Copa MX")) {
                                    listaMX.add(resumen);
                                    //listaMX.add(p);
                                }
                                else if(resumen.getLeague().equals("Ascenso MX"))
                                    listaAsenso.add(resumen);
                            }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ArrayList<PartidoResumen> arrayList = new ArrayList<>(listaMX.size());
            arrayList.addAll(listaMX);
            Bundle args = new Bundle();
            args.putSerializable("ARRAY",arrayList);

            homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();


            fragmentTransaction.replace(R.id.nav_host_fragment, homeFragment);
            fragmentTransaction.addToBackStack(null);
            homeFragment.setArguments(args);
            // Commit the transaction
            fragmentTransaction.commit();
            Log.i("MyLog","onPostExcecute");
        }
    }
}
