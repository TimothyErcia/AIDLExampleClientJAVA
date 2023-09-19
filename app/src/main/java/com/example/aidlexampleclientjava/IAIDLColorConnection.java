package com.example.aidlexampleclientjava;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.aidlexamplejava.IAIDLColorInterface;

public class IAIDLColorConnection implements ServiceConnection {

    public static IAIDLColorInterface colorInterface;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        colorInterface = IAIDLColorInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        colorInterface = null;
    }
}
