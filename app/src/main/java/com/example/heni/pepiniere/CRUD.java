package com.example.heni.pepiniere;

import android.content.Context;
import android.os.AsyncTask;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class CRUD extends AsyncTask<String ,Void,String> {
    Context context;

    String string_url,JSON_STRING,success;

    public CRUD(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {

}

    @Override
    protected String doInBackground(String... prams) {
        String params =prams[0];

        String data="" ;
      if (params.equals("create table"))
      {
          //definir l'adresse et le chemin de fichier php
          string_url="http://"+ MainActivity.ip+"/php/create_table.php";
          String name_plante=prams[1];
          try {
               data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name_plante,"UTF-8");
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }
    }else if (params.equals("update")) {
          string_url = "http://"+ MainActivity.ip+"/php/update.php";
          String name_plante=prams[1];
          String new_name=prams[2];
          String temperature=prams[3];
          String humid=prams[4];

          try {
              data= URLEncoder.encode("name_plante","UTF-8")+"="+URLEncoder.encode(name_plante,"UTF-8")+"&"+
                      URLEncoder.encode("new_name","UTF-8")+"="+URLEncoder.encode(new_name,"UTF-8")+"&"+
                      URLEncoder.encode("temperature","UTF-8")+"="+URLEncoder.encode(temperature,"UTF-8")+"&"+
                      URLEncoder.encode("humidite","UTF-8")+"="+URLEncoder.encode(humid,"UTF-8");
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }

      }else if (params.equals("inser data")){
          string_url="http://"+ MainActivity.ip+"/php/insert_plante.php";
          String name_plante=prams[1];
          String temperature=prams[2];
          String humid=prams[3];

          try {
               data= URLEncoder.encode("name_plante","UTF-8")+"="+URLEncoder.encode(name_plante,"UTF-8")+"&"+
                      URLEncoder.encode("temperature","UTF-8")+"="+URLEncoder.encode(temperature,"UTF-8")+"&"+
                      URLEncoder.encode("humidite","UTF-8")+"="+URLEncoder.encode(humid,"UTF-8");


          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }

          }else if (params.equals("delete")){
          string_url="http://"+ MainActivity.ip+"/php/delete_plante.php";
          String name_plante=prams[1];
          try {
              data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name_plante,"UTF-8");
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }

      }else if (params.equals("login")){
          string_url="http://"+ MainActivity.ip+"/php/connexion.php";
          String login=prams[1];
          String psw=prams[2];


          try {
              data= URLEncoder.encode("login","UTF-8")+"="+URLEncoder.encode(login,"UTF-8")+"&"+
                      URLEncoder.encode("psw","UTF-8")+"="+URLEncoder.encode(psw,"UTF-8");



          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }

      }

        try {//ouvrir une connection
            URL url = new URL(string_url);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

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

            success =  jsonObject.getString("success");
           inputStream.close();
            return success;

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
    protected void onPostExecute(String result) {

    }
}
