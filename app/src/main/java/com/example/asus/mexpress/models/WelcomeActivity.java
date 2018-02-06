package com.example.asus.mexpress.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.asus.mexpress.R;

public class WelcomeActivity extends AppCompatActivity {
    private  TextView session_info;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        session = new SessionManager(getApplicationContext());
        session_info = (TextView) findViewById(R.id.sesion_info);
        session_info.setText(getApplicationContext().getString(R.string.txt_welcome)+" "+session.getUser());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void registerClient(View view) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    public void registerOrder(View view) {
        System.out.println("Hola");
    }

    public void listClients(View view) {
        Intent i = new Intent(getApplicationContext(), ClientListActivity.class);
        startActivity(i);
    }

    public void listOrders(View view) {
        System.out.println("Hola");
    }

    public void showLocations(View view) {
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(i);
    }
}
