package com.example.heni.pepiniere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class formulaire_plante extends AppCompatActivity  {
  static ListView ls_plante;
   static ArrayAdapter adapter;
    //static List<plante> list_Plante;
    String  name_plante="" ;
    boolean etat =false;
    static List<String> list_p;
    Button valider,creat_plante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_plante);

        ls_plante =(ListView) findViewById(R.id.sp_plante);

        valider=(Button)findViewById(R.id.valide);
        creat_plante=(Button)findViewById(R.id.cr_plante);

        ls_plante.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       list_p = new ArrayList<>();

        //recuperer le nom de plante de la base
        try {
            list_p= new  FetchData(formulaire_plante.this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter=new ArrayAdapter(formulaire_plante.this,R.layout.row_item,R.id.check,list_p);
       ls_plante.setAdapter(formulaire_plante.adapter);


       ls_plante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            /*  if (etat==false) {
                  ((CheckedTextView) view).setChecked(!etat);*/

                  name_plante=((CheckedTextView)view).getText().toString();

                  /*etat=true;
              }else {
                  ((CheckedTextView) view).setChecked(!etat);
                  etat=false;
              }*/
           }
       });



        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i =getIntent();


                    i.putExtra("name_p",name_plante);

                    setResult(RESULT_OK,i);
                    finish();




            }
        });

        creat_plante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(formulaire_plante.this, creer_plante.class);
                intent.putExtra("content","formulaire_plante");
               startActivity(intent);


            }
        });



    }



    public void click_refresh(View view) {
        recreate();
    }
}
