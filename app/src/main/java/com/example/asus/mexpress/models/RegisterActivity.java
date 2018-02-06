package com.example.asus.mexpress.models;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.net.Uri;

import com.example.asus.mexpress.Interfaces.IRegisterClientPresentex;
import com.example.asus.mexpress.Interfaces.IRegisterClientView;
import com.example.asus.mexpress.R;
import com.example.asus.mexpress.presenters.RegisterClientPresentex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegisterActivity extends AppCompatActivity implements IRegisterClientView {
    private EditText name, lastName, phoneNumber, location, birthday;
    IRegisterClientPresentex iRegisterPresentex;
    private ImageView photo;
    private Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private Realm myRealm;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.photo = (ImageView) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        this.name = (EditText) findViewById(R.id.txt_name);
        this.lastName = (EditText) findViewById(R.id.txt_lastname);
        this.phoneNumber = (EditText) findViewById(R.id.txt_phonenumber);
        this.location = (EditText) findViewById(R.id.txt_location);
        this.birthday = (EditText) findViewById(R.id.txt_birthday);
        this.birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        birthday.setText(day + "/" + month + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
        this.iRegisterPresentex = new RegisterClientPresentex(getApplicationContext(), this);

        Realm.init(getApplicationContext());
        this.myRealm = Realm.getDefaultInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            photo.setImageURI(imageUri);
        }
    }

    public void validateRegisterFields(View view) {
        String name = this.name.getText().toString();
        String lastName = this.lastName.getText().toString();
        String phoneNumber = this.phoneNumber.getText().toString();
        String location = this.location.getText().toString();
        String birthday = this.birthday.getText().toString();
        boolean valid = this.iRegisterPresentex.validateRegisterFields(name, lastName, phoneNumber, location, birthday);

        if (valid) {
            this.myRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    saveClient(realm);
                }
            });
        }
    }

    private void saveClient(Realm realm) {
        Client client = realm.createObject(Client.class, UUID.randomUUID().toString());
        client.setName(this.name.getText().toString());
        client.setLastName(this.lastName.getText().toString());
        client.setPhoneNumber(Integer.parseInt(this.phoneNumber.getText().toString()));
        client.setLocation(this.location.getText().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date local_birthday = formatter.parse(this.birthday.getText().toString());
            client.setBirthday(local_birthday);
        }catch (ParseException e) {
            e.printStackTrace();
        }


        RealmResults<Client> list = this.myRealm.where(Client.class).findAll();
        Intent i = new Intent(getApplicationContext(), ClientListActivity.class);
        startActivity(i);
    }

    @Override
    public void setErrorName(String message) {
        this.name.setError(message);
    }

    @Override
    public void setErrorLastName(String message) {
        this.lastName.setError(message);
    }

    @Override
    public void setErrorPhoneNumber(String message) {
        this.phoneNumber.setError(message);
    }

    @Override
    public void setErrorLocation(String message) {
        this.location.setError(message);
    }

    @Override
    public void setErrorBirthday(String message) {
        this.birthday.setError(message);
    }
}