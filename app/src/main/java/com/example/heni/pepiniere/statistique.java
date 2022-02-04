package com.example.heni.pepiniere;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;

public class statistique extends AppCompatActivity {
    static GraphView graphView;
    ListView listView;
    float[] y_temp ,y_hum,y_q_eau,x;
    float max_y,max_d_eau,max_temp,max_hum;
    Button zoom,curseur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

        graphView=(GraphView)findViewById(R.id.graphV);
        zoom=(Button)findViewById(R.id.zoom);
        curseur=(Button)findViewById(R.id.cursor);

       /* graphView.setTitle("graphique");
        // set manual X bounds
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("x axis");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("y axis");*/
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0.0);


        graphView.getViewport().setXAxisBoundsManual(true);
       graphView.getViewport().setMinX(0.0);


       // graphView.getCursorMode().setTextColor(Color.parseColor("#fcfcfc"));

zoom.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
         graphView.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        graphView.setCursorMode(false);
    }
});

curseur.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        graphView.getViewport().setScalable(false); // enables horizontal zooming and scrolling
         graphView.getViewport().setScalableY(false); // enables vertical zooming and scrolling
        graphView.setCursorMode(true);
    }
});







        int length=get_info_detail.list_plante.size();

         x= new float[length];

        y_temp = new float[length];
         y_hum = new float[length];
         y_q_eau = new float[length];
        for(int i = 0; i < length; i++) {
            y_temp[i] = get_info_detail.list_plante.get(i).getTemperature();
            y_hum[i] = get_info_detail.list_plante.get(i).getHumidite();
            y_q_eau[i] = get_info_detail.list_plante.get(i).getQ_eau();
            x[i]=i+1;

        }

         max_temp=getMax(y_temp);
         max_hum=getMax(y_hum);
         max_d_eau=getMax(y_q_eau);

        final float max_table[]={max_temp,max_hum,max_d_eau};
         max_y=getMax(max_table);

        graphView.getViewport().setMaxX(x.length);
        graphView.getViewport().setMaxY(max_y);

        listView=(ListView)findViewById(R.id.list);

        final String courbe[]={"tout","temperature","humidite","q_eau"};

        ArrayAdapter<String> adapter= new ArrayAdapter<>(statistique.this,android.R.layout.simple_list_item_1,courbe);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=courbe[position];



                Graphique graph_q_eau = new Graphique(x,y_q_eau);
                Graphique graph_hum = new Graphique(x,y_hum);
                Graphique graph_tmp = new Graphique(x,y_temp);

                if (name.equals("tout")){
                    graphView.getViewport().setMaxY(max_y);
                    graphView.removeAllSeries();

                    graphView.getLegendRenderer().setVisible(true);
                    graphView.getLegendRenderer().setTextColor(Color.parseColor("#fcfcfc"));
                    graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    graph_tmp.traçage_tem();
                    graph_hum.traçage_hum();
                    graph_q_eau.traçage_q_eau();

                }else if (name.equals("temperature")){
                    graphView.getViewport().setMaxY(max_temp);
                    graphView.removeAllSeries();

                    graphView.getLegendRenderer().setVisible(true);
                    graphView.getLegendRenderer().setTextColor(Color.parseColor("#fcfcfc"));
                    graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    graph_tmp.traçage_tem();

                }else if (name.equals("humidite")){
                    graphView.getViewport().setMaxY(max_hum);
                    graphView.removeAllSeries();

                    graphView.getLegendRenderer().setVisible(true);
                    graphView.getLegendRenderer().setTextColor(Color.parseColor("#fcfcfc"));
                    graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    graph_hum.traçage_hum();

                }else if (name.equals("q_eau")){
                    graphView.getViewport().setMaxY(max_d_eau);
                    graphView.removeAllSeries();

                    graphView.getLegendRenderer().setVisible(true);
                    graphView.getLegendRenderer().setTextColor(Color.parseColor("#fcfcfc"));
                    graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    graph_q_eau.traçage_q_eau();

                }
            }
        });





    }

float getMax(float table[]){

        float max=0;
        for (int i=0;i<table.length;i++){
            if (table[i]>max){
                max=table[i];
            }
        }
   return max;

}

}

