package com.example.heni.pepiniere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import static com.example.heni.pepiniere.MainActivity.lstPlante;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.myViewHolder>  {
    private Context mcontext;
    private List<plante> mdata;

    //constructeur
    public RecycleViewAdapter(Context mcontext, List<plante> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }



    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView;
       static ImageView imageView ;
        CardView cardView;



         myViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.card_text);
            imageView =(ImageView)itemView.findViewById(R.id.card_img);

            cardView =(CardView) itemView.findViewById(R.id.card_view);


            cardView.setOnCreateContextMenuListener(this);


         }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.add(this.getAdapterPosition(),121,0,"suprimer");
            contextMenu.add(this.getAdapterPosition(),122,1,"modifier");

        }
    }




    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        //lier la cardVIewItem avec l'adaptateur
        LayoutInflater mInflater= LayoutInflater.from(mcontext);
        view=mInflater.inflate(R.layout.cardview_item_plante,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {

        holder.textView.setText(mdata.get(position).getName());//afficher le nom de la plante


        Random rand = new Random();
        int n = rand.nextInt(4);
        switch (n){
            case 1: holder.imageView.setBackgroundResource(R.drawable.back1);break;
            case 2: holder.imageView.setBackgroundResource(R.drawable.back2);break;
            case 3: holder.imageView.setBackgroundResource(R.drawable.back3);break;
            case 4: holder.imageView.setBackgroundResource(R.drawable.back4);break;
        }





       //envoyer les donnees vers Detail_plante



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mcontext,Detail_plante.class);
                i.putExtra("name_p",mdata.get(position).getName());
                i.putExtra("pos",position);

                mcontext.startActivity(i);
            }
        });










    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public  void suprimerItem(int pos){

        new CRUD(mcontext).execute("delete",mdata.get(pos).getName());
         lstPlante.remove(pos);
        new save_shard(mcontext,lstPlante).save_liste();

        notifyDataSetChanged();

    }


   /* public  void save_liste( List<plante> lstPlante){
        SharedPreferences sharedPreferences =  mcontext.getSharedPreferences("save_plante",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json = gson.toJson(lstPlante);
        editor.putString("key_plant",json);
        editor.apply();
    }*/




}
