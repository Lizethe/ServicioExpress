package com.example.asus.mexpress.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.mexpress.R;

public class WelcomeActivity extends AppCompatActivity {
    private TextView session_info;
    private SessionManager session;
    private Button register, list, maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        session = new SessionManager(getApplicationContext());
        session_info = (TextView) findViewById(R.id.sesion_info);
        session_info.setText(getApplicationContext().getString(R.string.txt_welcome) + " " + session.getUser());
        register = (Button) findViewById(R.id.btn_registerclient);
        list = (Button) findViewById(R.id.btn_clientlist);
        maps = (Button) findViewById(R.id.btn_locations);
        this.verifyUserType();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void verifyUserType() {
        String type = session.getType();
        if (type.equalsIgnoreCase("Client") || type.equalsIgnoreCase("Cliente")) {
            register.setVisibility(View.GONE);
            list.setText(getApplicationContext().getString(R.string.view_deliverymanlist));
            maps.setText(getApplicationContext().getString(R.string.view_deliverymanlocation));
        } else if (type.equalsIgnoreCase("Administrator")) {
            register.setVisibility(View.GONE);
            list.setText(getApplicationContext().getString(R.string.view_personlist));
            maps.setText(getApplicationContext().getString(R.string.view_personlocation));
        }
    }

    public void registerClient(View view) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    public void listClients(View view) {
        Intent i = new Intent(getApplicationContext(), PersonListActivity.class);
        startActivity(i);
    }

    public void showLocations(View view) {
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("origin", "location_button");
        startActivity(i);
    }

    public void sessionDestroy(View view) {
        session.destroy();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}
