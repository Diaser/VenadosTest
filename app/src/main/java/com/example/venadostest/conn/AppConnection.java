package com.example.venadostest.conn;

import android.os.Build;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AppConnection {

    private static void disableConnectionReuseIfNecessary() {
        // see HttpURLConnection API doc
        if (Integer.parseInt(Build.VERSION.SDK)
                < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    public static JSONObject conectWebService(String url){

        disableConnectionReuseIfNecessary();
        //HttpClient httpClient = new DefaultHttpClient();
        HttpURLConnection urlConnection = null;

        try{
            URL urlToRequest = new URL(url);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            urlConnection.setRequestProperty("Accept","application/json");

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            }else if (statusCode != HttpURLConnection.HTTP_OK && statusCode != 201) {
                // handle any other errors, like 404, 500,..
                Log.i("AppConnection","statusCode: "+ statusCode );
                JSONObject myJson = new JSONObject();
                myJson.put("ERROR", String.valueOf(statusCode));
                return  myJson;
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            Log.i("AppConnection", "i will return");
            String response = getResponseText(in);
            Log.i("AppConnection","respuesta: "+ response);
            return new JSONObject(response);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
