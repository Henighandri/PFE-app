package com.example.heni.pepiniere;

import android.content.Context;
import android.os.AsyncTask;

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

public class control_level_water  extends AsyncTask<String ,Void, List<plante>> {
    Context context;
    private String string_url;
    private String JSON_STRING = "";
    private String name = "";
    private  int niv_eau;


    static List<plante> list = new ArrayList<>();

    //constructeur
    public control_level_water(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {



    }

    @Override
    protected List<plante> doInBackground(String... params) {
        //effacer la liste
        list.clear();




        //definir l'adresse et le chemin de fichier php
        string_url = "http://" + MainActivity.ip + "/php/get_water_level.php";


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
            JSONArray JA = jsonObject.getJSONArray("control_water_level");


            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = JA.getJSONObject(i);
                name = (String) JO.get("name_plant");
                niv_eau =  JO.getInt("niveau_eau");
                list.add(new plante(name,niv_eau));


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
    protected void onPostExecute(List<plante> result) {
        super.onPostExecute(result);



    }
}
