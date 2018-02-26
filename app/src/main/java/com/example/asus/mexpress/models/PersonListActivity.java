package com.example.asus.mexpress.models;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asus.mexpress.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class PersonListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Realm myRealm;
    private TextView phoneNumber, title;
    private SessionManager session;
    private ImageButton addPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        Realm.init(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        title = (TextView) findViewById(R.id.lb_client_list);
        addPerson = (ImageButton) findViewById(R.id.btn_add_person);
        this.myRealm = Realm.getDefaultInstance();
        ArrayList<Person> list_persons = this.getListPersonByUserType();
        PersonListAdapter personListAdapter = new PersonListAdapter(list_persons);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_client);
        this.recyclerView.setAdapter(personListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private ArrayList<Person> getListPersonByUserType() {
        Type userType = null;
        User userInSesion = this.myRealm.where(User.class)
                .equalTo("username", session.getUser())
                .equalTo("type", session.getType()).findFirst();
        RealmResults<Person> list = null;
        if (session.getType().equals("Client")) {
            title.setText("Delivery Man List");
            addPerson.setVisibility(View.GONE);
            userType = Type.DELIVERY_MAN;
            list = this.myRealm.where(Person.class)
                    .equalTo("type", userType.toString())
                    .findAll();
        } else if (session.getType().equals("Administrator")) {
            title.setText("Person List");
            list = this.myRealm.where(Person.class).findAll();
        } else {
            userType = Type.CLIENT;
            list = this.myRealm.where(Person.class)
                    .equalTo("type", userType.toString())
                    .equalTo("userId", userInSesion.getId())
                    .findAll();
        }

        ArrayList<Person> list_persons = new ArrayList<>();
        for (Person c : list) {
            list_persons.add(c);
        }
        return list_persons;
    }

    public void callClient(View view) {
        CardView row = (CardView) view.getParent().getParent();
        TextView phone_number = (TextView) row.findViewById(R.id.txt_phone);
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + phone_number.getText()));
        startActivity(i);
    }

    public void addClient(View view) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}
