package com.example.heni.pepiniere;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {


    String[] list_name ;

  static   List<plante> lstPlante;
    private BluetoothAdapter myBluetooth = null;

    ImageView ajouter;
    ImageButton configuration;

   static RecycleViewAdapter myadapter;
    String name_plane;
    public static String ip ="192.168.1.7";






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       ajouter = (ImageView) findViewById(R.id.img_ajouter);
       configuration=(ImageButton)findViewById(R.id.configuration) ;





        //recuperer les donnee de sharedPreference

      new save_shard(MainActivity.this,lstPlante).load_list();


        //adapter la list lstplante dans recycleView
        final RecyclerView myrecycleview = (RecyclerView) findViewById(R.id.recycleview);
        myadapter = new RecycleViewAdapter(this, lstPlante);
        myrecycleview.setLayoutManager(new GridLayoutManager(this, 2));
        myrecycleview.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();






        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this, formulaire_plante.class);
                startActivityForResult(intent, 1);



            }
        });



     configuration.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             myBluetooth = BluetoothAdapter.getDefaultAdapter();
             if(!myBluetooth.isEnabled())
             {
                 //Ask to the user turn the bluetooth on
                 Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                 startActivityForResult(turnBTon,2);
             }else{

                 list_name=new String[lstPlante.size()];
                 for (int pos =0;pos<lstPlante.size();pos++){
                     list_name[pos]=lstPlante.get(pos).getName();
                 }


                 Intent i=new Intent(MainActivity.this, setting.class);
                 i.putExtra("string-array", list_name);

                 startActivity(i);
             }


         }
     });






    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode==RESULT_OK) {
           //reception du donnee de l'activite formulaire_plante
           name_plane = data.getStringExtra("name_p");
           verif_plante();


               lstPlante.add(new plante(name_plane, R.drawable.back1));
               new CRUD(MainActivity.this).execute("create table",name_plane);
               //enregistre la liste dans sharedPreference
              // save_liste();
            new save_shard(MainActivity.this,lstPlante).save_liste();



        }else if (requestCode==2 && resultCode==RESULT_OK){

             list_name=new String[lstPlante.size()];
            for (int pos =0;pos<lstPlante.size();pos++){
                list_name[pos]=lstPlante.get(pos).getName();
            }


            Intent i=new Intent(MainActivity.this, setting.class);
            i.putExtra("string-array", list_name);

            startActivity(i);
        }

    }
    void verif_plante(){
        int ref=1;
        for (int i=0;i<lstPlante.size();i++){
            if (lstPlante.get(i).getName().equals(name_plane)){
                name_plane=name_plane+""+Integer.toString(ref);
                ref++;

            }

        }

    }





    @Override
    protected void onResume(){

        super.onResume();
        //mise a jour de la liste
        myadapter.notifyDataSetChanged();

    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {


         switch (item.getItemId()){
             case 121 :
                 AlertDialog.Builder builder= new AlertDialog.Builder(this);
                 builder.setMessage("Voulez-vous supprimer ?")
                         .setIcon(R.drawable.ic_delete)
                         .setTitle(lstPlante.get(item.getGroupId()).getName())
                         .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 myadapter.suprimerItem(item.getGroupId());
                                 myadapter.notifyDataSetChanged();
                             }
                         })
                         .setNegativeButton("non", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                             }
                         }).show();

                       return true;

             case 122 : Intent i=new Intent(MainActivity.this,creer_plante.class);
                        i.putExtra("content","MainActivity");
                        i.putExtra("name",lstPlante.get(item.getGroupId()).getName());
                        i.putExtra("pos",item.getGroupId());
                        startActivity(i);
                        return true;

           default: return super.onContextItemSelected(item);
         }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
       /* Intent br=new Intent(MainActivity.this,My_service.class);
        startService(br);*/
    }
}