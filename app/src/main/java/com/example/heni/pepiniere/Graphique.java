package com.example.heni.pepiniere;

import android.graphics.Color;
import android.graphics.Paint;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graphique {
    Paint paint ;
    LineGraphSeries<DataPoint> series , series_hum,series_q_eau;
    float x[];
    float y[];




    public Graphique(float x[] , float y[] ) {
        this.x=x;
        this.y=y;


    }
    public  void traçage_hum(){



        series_hum = new LineGraphSeries<DataPoint>(getDataPoint());
        series_hum.setTitle("humidite");
        series_hum.setDrawDataPoints(true);
        series_hum.setDataPointsRadius(10);
        series_hum.setAnimated(true);
        series_hum.setDrawBackground(true);
        series_hum.setColor(Color.argb(255, 60, 60, 255));
        series_hum.setBackgroundColor(Color.argb(100, 119, 119, 204));
        series_hum.setDrawDataPoints(true);

        statistique.graphView.addSeries(series_hum);


      /*  series_q_eau = new LineGraphSeries<DataPoint>(getData_q_eau());
        series_q_eau.setTitle("quantite d'eau");
        series_q_eau.setDrawDataPoints(true);
        series_q_eau.setDataPointsRadius(10);
        series_q_eau.setAnimated(true);
        series_q_eau.setDrawBackground(true);
        series_q_eau.setColor(Color.argb(185, 0, 204, 205));
        series_q_eau.setBackgroundColor(Color.argb(100, 102, 255, 255));
        series_q_eau.setDrawDataPoints(true);

        statistique.graphView.addSeries(series_q_eau);

        series = new LineGraphSeries<DataPoint>(getData_temp());
        series.setTitle("temperature");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setAnimated(true);
        series.setDrawBackground(true);
        series.setColor(Color.argb(255, 255, 60, 60));
        series.setBackgroundColor(Color.argb(210, 204, 100, 100));
        series.setDrawDataPoints(true);

        statistique.graphView.addSeries(series);*/


    }
    public void traçage_tem(){
        series = new LineGraphSeries<DataPoint>(getDataPoint());
        series.setTitle("temperature");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setAnimated(true);
        series.setDrawBackground(true);
        series.setColor(Color.argb(255, 255, 60, 60));
        series.setBackgroundColor(Color.argb(210, 204, 100, 100));
        series.setDrawDataPoints(true);

        statistique.graphView.addSeries(series);

    }
    public void traçage_q_eau(){
        series_q_eau = new LineGraphSeries<DataPoint>(getDataPoint());
        series_q_eau.setTitle("quantite d'eau");
        series_q_eau.setDrawDataPoints(true);
        series_q_eau.setDataPointsRadius(10);
        series_q_eau.setAnimated(true);
        series_q_eau.setDrawBackground(true);
        series_q_eau.setColor(Color.argb(185, 0, 204, 205));
        series_q_eau.setBackgroundColor(Color.argb(100, 102, 255, 255));
        series_q_eau.setDrawDataPoints(true);
        statistique.graphView.addSeries(series_q_eau);

    }


    private DataPoint[] getDataPoint() {
        DataPoint[] dp=new DataPoint[x.length];
        for (int i=0;i< x.length;i++){
            dp[i]=new DataPoint(x[i],y[i]);


        }
        return dp ;
    }


}
