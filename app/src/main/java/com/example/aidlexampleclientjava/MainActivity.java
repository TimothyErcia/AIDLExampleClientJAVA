package com.example.aidlexampleclientjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aidlexampleclientjava.databinding.ActivityMainBinding;
import com.example.aidlexamplejava.*;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ServiceConnection serviceConnection = new IAIDLColorConnection();
    private ActivityMainBinding viewbinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewbinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewbinding.getRoot());
        bindFromAIDL();

        viewbinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int color = IAIDLColorConnection.colorInterface.getColor();
                    viewbinding.viewChanger.setBackgroundColor(color);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindFromAIDL() {
        viewbinding.progressCircular.setProgress(1, true);
        Intent i = new Intent("AIDLService");
        i.setPackage("com.example.aidlexamplejava");
        bindService(i, serviceConnection, BIND_AUTO_CREATE);
        setUIInterval();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private void setUIInterval() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int progress = 1;
            @Override
            public void run() {
                progress+=80;
                viewbinding.progressCircular.setProgress(progress, true);
                handler.postDelayed(this,1000);
                if(progress==100) {
                    handler.removeCallbacks(this);
                }
            }
        };
        handler.postDelayed(runnable, 1000);

    }
}