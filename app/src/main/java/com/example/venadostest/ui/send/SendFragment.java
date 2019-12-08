package com.example.venadostest.ui.send;

import android.os.AsyncTask;
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

import com.example.venadostest.MainActivity;
import com.example.venadostest.R;
import com.example.venadostest.conn.AppConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    private final String url = "https://venados.dacodes.mx/api/statistics";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new AsynckGetTable().execute();
        MainActivity.tabLayout.setVisibility(View.GONE);
    }

    private class AsynckGetTable extends AsyncTask<String, Void, Void> {
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
                if (jsonObject.has("data")) {
                    jsonObject = jsonObject.getJSONObject("data");
                    if (jsonObject.has("games")) {

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
        }
    }
}