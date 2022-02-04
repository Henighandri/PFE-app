package com.example.heni.pepiniere;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchData extends AsyncTask<String ,Void,List<String>> {
    Context context;
    private String string_url;
    private String JSON_STRING = "";
    private String name = "",niv_eau;


    static List<String> list = new ArrayList<>();
    String data = "";
    // String params1;

    //constructeur
    public FetchData(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {



    }

    @Override
    protected List<String> doInBackground(String... params) {
        //effacer la liste
        list.clear();




            //definir l'adresse et le chemin de fichier php
            string_url = "http://" + MainActivity.ip + "/php/get_all_plante.php";


        try {
            URL url = new URL(string_url);//convertir string_url vers URL
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//ouvrir une connection
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));//creation d'un nouveau bufferedReader

            String line = "";
            //lire des donnees de buffredReader
            while (null != line) {
                line = bufferedReader.readLine();
                JSON_STRING = JSON_STRING + line;


            }

            //obtenir les donnees dans une liste
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            //JSONArray JA = jsonObject.getJSONArray("info_plante");
            JSONArray JA = jsonObject.getJSONArray("info_plante");


            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = JA.getJSONObject(i);
                name = (String) JO.get("name");
                list.add(name);


            }


            //fermer la connection
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return list;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }


        return null;


    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<String> result) {
        super.onPostExecute(result);



    }
}