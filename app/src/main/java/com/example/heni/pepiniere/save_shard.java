package com.example.heni.pepiniere;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.heni.pepiniere.MainActivity.lstPlante;

public class save_shard {
    Context context;
    List<plante> planteList;

    public save_shard(Context context, List<plante> planteList) {
        this.context = context;
        this.planteList = planteList;
    }
    public save_shard(Context context) {
        this.context = context;

    }
    public  void save_liste(){
        SharedPreferences sharedPreferences = (SharedPreferences) context.getSharedPreferences("save_plante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json = gson.toJson(lstPlante);
        editor.putString("key_plant",json);
        editor.apply();

    }

    public  List<plante>  load_list(){
        SharedPreferences sharedPreferences =(SharedPreferences) context.getSharedPreferences("save_plante", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json = sharedPreferences.getString("key_plant",null);
        Type type = new TypeToken<ArrayList<plante>>(){}.getType();
        lstPlante =gson.fromJson(json,type);
        if (lstPlante == null){
            lstPlante=new ArrayList<>();
        }
        return lstPlante;
    }

}
