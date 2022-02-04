package com.example.heni.pepiniere;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class get_info_detail extends AsyncTask<String ,Void,plante> {
    String string_url,data="",JSON_STRING,name ;
    int humidite,temperature,q_eau;
    plante  myplante ;
   static List<plante> list_plante=new ArrayList<>();
String desc;

    Context context;
    public get_info_detail(Context context) {
        this.context=context;
    }

    @Override
    protected plante doInBackground(String... params) {


        String name_plante=params[0];

       string_url = "http://"+ MainActivity.ip+"/php/get_detail_plante.php";


        try {//ouvrir une connection
            URL url = new URL(string_url);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name_plante,"UTF-8");
            //envoyer data dans bufferedWriter
            bufferedWriter.write(data);

            //fermer la connection
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));//creation d'un nouveau bufferedReader

            JSON_STRING = bufferedReader.readLine();
            //lire des donnees de buffredReader



            JSONObject jsonObject = new JSONObject(JSON_STRING);

           if(jsonObject.getString("success").equals("1")) {
               JSONArray JA = jsonObject.getJSONArray(name_plante);

               for (int i = 0; i < JA.length(); i++) {
                   JSONObject JO = JA.getJSONObject(i);
                   temperature = JO.getInt("temperature");
                   humidite = JO.getInt("humidite");
                   q_eau = JO.getInt("q_eau");
                   desc = JO.getString("description");
                   myplante = new plante(name_plante, q_eau, temperature, humidite, desc);
                   list_plante.add(myplante);

               }
           }else {
               myplante = new plante(name_plante, -1, -1, -1, "");
               return myplante;
           }





            //fermer la connexion
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();




            return list_plante.get(list_plante.size() - 1);

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
    protected void onPostExecute(plante result) {
        super.onPostExecute(result);

    }
}
