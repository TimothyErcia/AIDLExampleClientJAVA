package com.example.aidlexampleclientjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aidlexamplejava.*;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private View viewChanger;
    private Button btn1, btn2;
    private IAIDLColorInterface colorInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            colorInterface = IAIDLColorInterface.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: SUCCESS");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        viewChanger = findViewById(R.id.viewChanger);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindFromAIDL();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int color = colorInterface.getColor();
                    viewChanger.setBackgroundColor(color);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void bindFromAIDL() {
        Intent i = new Intent("AIDLService");
        i.setPackage("com.example.aidlexamplejava");
        bindService(i, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}