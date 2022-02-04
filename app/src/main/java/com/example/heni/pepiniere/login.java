package com.example.heni.pepiniere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class login extends AppCompatActivity {
EditText login,psw;
Button connexion;
    String verif_connextion ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(EditText)findViewById(R.id.login);
        psw=(EditText)findViewById(R.id.psw);
        connexion=(Button)findViewById(R.id.connexion);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String log=login.getText().toString();
                String mot_de_passe =psw.getText().toString();


                try {
                     verif_connextion=new CRUD(login.this).execute("login",log,mot_de_passe).get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (verif_connextion.equals("1")){
                    Intent intent=new Intent(login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
