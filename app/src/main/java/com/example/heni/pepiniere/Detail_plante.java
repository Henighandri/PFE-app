package com.example.heni.pepiniere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Detail_plante extends AppCompatActivity {
static TextView desc,name_p,temperature,humidite,q_eau;
    int temp,hum,eau;

 static ProgressBar temp_Progress,q_Progress, humd_Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_plante);

      name_p=(TextView) findViewById(R.id.name);
        desc=(TextView) findViewById(R.id.desc);
        temperature=(TextView) findViewById(R.id.temp);
        humidite=(TextView) findViewById(R.id.hum);
        q_eau=(TextView) findViewById(R.id.q_eau);
      temp_Progress=(ProgressBar)findViewById(R.id.progress_tem);
        q_Progress=(ProgressBar)findViewById(R.id.progress_eau);
        humd_Progress=(ProgressBar)findViewById(R.id.progress_hum);
        Button b_graphi=(Button)findViewById(R.id.b_graph);
        ImageView imageView=(ImageView)findViewById(R.id.img);


        Intent i=getIntent();
        String nom =i.getStringExtra("name_p");
        get_info_detail.list_plante.clear();

        Random rand = new Random();
        int n = rand.nextInt(4);
        switch (n){
            case 1: imageView.setBackgroundResource(R.drawable.back1);break;
            case 2: imageView.setBackgroundResource(R.drawable.back2);break;
            case 3: imageView.setBackgroundResource(R.drawable.back3);break;
            case 4: imageView.setBackgroundResource(R.drawable.back4);break;
        }


        try {
            plante result= new get_info_detail(Detail_plante.this).execute(nom).get();
             temp=result.getTemperature();
             hum=result.getHumidite();
             eau=result.getQ_eau();
           name_p.setText(result.getName());
            desc.setText(result.getDescription());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
       if (eau==-1 && temp ==-1 && hum == -1 ){
           Toast.makeText(this, "tableau vide ", Toast.LENGTH_SHORT).show();
          // finish();

       }else {
           temperature.setText(Integer.toString(temp) + "°C");
           temp_Progress.setProgress(temp);

           humidite.setText(Integer.toString(hum) + "%");
           humd_Progress.setProgress(hum);

           q_eau.setText(Integer.toString(eau) + "ml");
           q_Progress.setProgress(eau);
       }

        b_graphi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i=new Intent(Detail_plante.this, statistique.class);
                 startActivity(i);
             }
         });



    }
}
