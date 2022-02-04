package com.example.heni.pepiniere;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class setting extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {
Spinner spinner,bluetooth_spinner;
List<plante> lstPlante;
String plante,ssid,psw,ip;
String adresse_bluetooth;
Button send,connect;
EditText E_ssid,E_psw,E_ip;
LinearLayout linearLayout;

    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";
    private BluetoothSocket btSocket = null;

    private OutputStream outStream = null;
    private static final String TAG = "bluetooth1";


// SPP UUID service

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        spinner=(Spinner)findViewById(R.id.spinner);
        bluetooth_spinner=(Spinner)findViewById(R.id.bluetooth);
       E_ip=(EditText)findViewById(R.id.ip);
       E_psw=(EditText)findViewById(R.id.passe_wifi);
       E_ssid=(EditText)findViewById(R.id.ssid);
        send=(Button) findViewById(R.id.send);
        connect=(Button) findViewById(R.id.connect);
        linearLayout=(LinearLayout)findViewById(R.id.linearlayout);


        spinner.setOnItemSelectedListener(this);
        bluetooth_spinner.setOnItemSelectedListener(this);





        Intent intent = getIntent();
        String [] list_name = intent.getStringArrayExtra("string-array");


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_name);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(arrayAdapter);


        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null)
        {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }
        pairedDevicesList();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssid=E_ssid.getText().toString();
                psw=E_psw.getText().toString();
                ip=E_ip.getText().toString();
            sendData(plante+"|"+ssid+"|"+psw+"|"+ip);

            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                   if( connect()){
                       linearLayout.setVisibility(View.VISIBLE);

                   }


            }
        });



    }




//***********************************************************

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list_blue = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list_blue.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the adresse_bluetooth
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter bluetooth_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_blue);
        bluetooth_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        bluetooth_spinner.setAdapter(bluetooth_adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId() == R.id.spinner)
        {
           plante= ((TextView) view).getText().toString();
        }
        else if(adapterView.getId() == R.id.bluetooth)
        {

            // Get the device MAC adresse_bluetooth, the last 17 chars in the View
            String info = ((TextView) view).getText().toString();
             adresse_bluetooth =info.substring(info.length() - 17);


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {




    }




    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        if(Build.VERSION.SDK_INT >= 10){

            try {

                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });

                return (BluetoothSocket) m.invoke(device, MY_UUID);

            } catch (Exception e) {

                Log.e(TAG, "Could not create Insecure RFComm Connection",e);

            }

        }

        return  device.createRfcommSocketToServiceRecord(MY_UUID);

    }











    private void errorExit(String title, String message){

        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();

        finish();

    }



    private void sendData(String message) {

        byte[] msgBuffer = message.getBytes();

        try {

            outStream.write(msgBuffer);

        } catch (IOException e) {



            Toast.makeText(this, "error transmission", Toast.LENGTH_SHORT).show();

            // errorExit("Fatal Error", msg);

        }

    }
    public  Boolean connect(){
        Boolean verif;

        BluetoothDevice device = myBluetooth.getRemoteDevice(adresse_bluetooth);


        try {

            btSocket = createBluetoothSocket(device);

        } catch (IOException e1) {

            // errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
            Toast.makeText(this, "Error create socket", Toast.LENGTH_SHORT).show();

        }





        myBluetooth.cancelDiscovery();




        try {

            btSocket.connect();

            verif=true;
            Toast.makeText(this, "Connection ok", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Non Connection", Toast.LENGTH_SHORT).show();
           verif=false;

            try {

                btSocket.close();


            } catch (IOException e2) {
                // Toast.makeText(this, "Non Connection", Toast.LENGTH_SHORT).show();



            }



        }





        Log.d(TAG, "...Create Socket...");



        try {

            outStream = btSocket.getOutputStream();

        } catch (IOException e) {

            errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");

        }

        return verif;
    }

}
