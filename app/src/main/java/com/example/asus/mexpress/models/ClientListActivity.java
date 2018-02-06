package com.example.asus.mexpress.models;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.mexpress.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ClientListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Realm myRealm;
    private TextView phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        Realm.init(getApplicationContext());
        this.myRealm = Realm.getDefaultInstance();
        RealmResults<Client> list = this.myRealm.where(Client.class).findAll();
        ArrayList<Client> list_clients = new ArrayList<>();
        int i = 0;
        for (Client c : list) {
            System.out.println(c);
            list_clients.add(c);
        }
        ClientListAdapter clientListAdapter = new ClientListAdapter(list_clients);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_client);
        this.recyclerView.setAdapter(clientListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void callClient(View view){
        CardView row = (CardView)view.getParent().getParent();
        TextView phone_number = (TextView) row.findViewById(R.id.txt_phone);
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:"+phone_number.getText()));
        startActivity(i);
    }

    public void addClient(View view){
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}
