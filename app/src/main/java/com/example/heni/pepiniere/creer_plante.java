package com.example.heni.pepiniere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class creer_plante extends AppCompatActivity {
private Button valider;
private  EditText nom,temperature,humidite;
String new_name,temp,hum,verif_update,verif_insert;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree_plante);
        valider=(Button)findViewById(R.id.valide);
        nom=(EditText)findViewById(R.id.nom);
        temperature=(EditText)findViewById(R.id.tempe);
        humidite=(EditText)findViewById(R.id.hum);
       i= getIntent();

       valider.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new_name = nom.getText().toString();
               temp = temperature.getText().toString();
               hum = humidite.getText().toString();


               if (i.getStringExtra("content").equals("MainActivity")) {


                   String old_name=i.getStringExtra("name");
                   int pos = i.getIntExtra("pos",-1);
                   
                   if (test()) {
                       try {
                           
                           verif_update= new CRUD(creer_plante.this).execute("update", old_name,new_name, temp, hum).get();
                      
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       } catch (ExecutionException e) {
                           e.printStackTrace();
                       }
                       
                       if (verif_update.equals("1")) {
                           MainActivity.lstPlante.set(pos, new plante(new_name, R.drawable.back1));
                           Toast.makeText(creer_plante.this, "success", Toast.LENGTH_SHORT).show();
                           new save_shard(creer_plante.this, MainActivity.lstPlante).save_liste();
                           finish();
                       }else{
                           Toast.makeText(creer_plante.this, "erreur de modification", Toast.LENGTH_SHORT).show();
                       }
                   }

               } else if (i.getStringExtra("content").equals("formulaire_plante")) {
                   if (test()) {
                       try {

                           verif_insert  =new CRUD(creer_plante.this).execute("inser data", new_name, temp, hum).get();


                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       } catch (ExecutionException e) {
                           e.printStackTrace();
                       }

                       if (verif_insert.equals("1")) {
                           finish();
                       }else{
                           Toast.makeText(creer_plante.this, "erreur d'insertion", Toast.LENGTH_SHORT).show();
                       }


                   }

               }
           }

       });






    }

    public  boolean test(){

        Boolean verif =false;
        if ((new_name!=null)&&(!new_name.contains(" "))){
            verif=true;
        }else {
            Toast.makeText(creer_plante.this, "nom n'est pas valide", Toast.LENGTH_SHORT).show();
            verif=false ;
        }

        if ((Integer.parseInt(temp)>10)&&(Integer.parseInt(temp)<40)){
            verif=true;
        }else {
            Toast.makeText(creer_plante.this, "10<temperature<40", Toast.LENGTH_SHORT).show();
            verif=false ;
        }

        if ((Integer.parseInt(hum)>10)&&(Integer.parseInt(hum)<100)){
            verif=true;
        }else {
            Toast.makeText(creer_plante.this, "10<humidite<100", Toast.LENGTH_SHORT).show();
            verif=false ;
        }
        return verif ;
    }

}
