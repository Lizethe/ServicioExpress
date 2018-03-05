package com.example.asus.mexpress.models;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.net.Uri;
import android.widget.TextView;

import com.example.asus.mexpress.Interfaces.IRegisterClientPresentex;
import com.example.asus.mexpress.Interfaces.IRegisterClientView;
import com.example.asus.mexpress.R;
import com.example.asus.mexpress.presenters.RegisterClientPresentex;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegisterActivity extends AppCompatActivity implements IRegisterClientView {
    private EditText name, lastName, phoneNumber, address, birthday;
    IRegisterClientPresentex iRegisterPresentex;
    private ImageView photo, map;
    private Uri imageUri;
    private TextView title, location_id;
    private static final int PICK_IMAGE = 100;
    private static final Integer REQUEST_MAP = 52;
    public static final String LATITUDE_IDENTIFIER = "latitude";
    public static final String LONGITUDE_IDENTIFIER = "longitude";

    private Realm myRealm;
    private RecyclerView recyclerView;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        session = new SessionManager(getApplicationContext());
        this.photo = (ImageView) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        this.map = (ImageView) findViewById(R.id.img_button_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });
        title = (TextView) findViewById(R.id.lb_register);
        if (session.getType().equals("Administrator")) {
            title.setText("Person Register");
        }
        this.name = (EditText) findViewById(R.id.txt_name);
        this.lastName = (EditText) findViewById(R.id.txt_lastname);
        this.phoneNumber = (EditText) findViewById(R.id.txt_phonenumber);
        this.address = (EditText) findViewById(R.id.txt_location);
        this.location_id = (TextView) findViewById(R.id.lb_location_id);
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

    private void openMaps() {
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("origin","register_activity");
        startActivityForResult(i, REQUEST_MAP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            photo.setImageURI(imageUri);
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_MAP) {
            final double latitude = data.getDoubleExtra(LATITUDE_IDENTIFIER, 0d);
            final double longitude = data.getDoubleExtra(LONGITUDE_IDENTIFIER, 0d);

            this.myRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    saveLocation(realm, latitude, longitude);
                    map.setBackgroundColor(2);
                }
            });
        }
    }

    private void saveLocation(Realm realm, Double latitude, Double longitude) {
        Location location = realm.createObject(Location.class, UUID.randomUUID().toString());
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location_id.setText(location.getId());
    }


    public void validateRegisterFields(View view) {
        String name = this.name.getText().toString();
        String lastName = this.lastName.getText().toString();
        String phoneNumber = this.phoneNumber.getText().toString();
        String location = this.address.getText().toString();
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
        Person person = realm.createObject(Person.class, UUID.randomUUID().toString());
        person.setName(this.name.getText().toString());
        person.setLastName(this.lastName.getText().toString());
        person.setPhoneNumber(Integer.parseInt(this.phoneNumber.getText().toString()));
        person.setAddress(this.address.getText().toString());
        person.setLocationId(location_id.getText().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (session.getType().equals("Client")) {
            person.setType(Type.DELIVERY_MAN.toString());
        } else {
            person.setType(Type.CLIENT.toString());
        }
        try {
            Date local_birthday = formatter.parse(this.birthday.getText().toString());
            person.setBirthday(local_birthday);
            Bitmap bmp = ((BitmapDrawable) photo.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            person.setPhoto(byteArray);
            User userInSesion = this.myRealm.where(User.class)
                    .equalTo("username", session.getUser())
                    .equalTo("type", session.getType()).findFirst();
            person.setUserId(userInSesion.getId());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        RealmResults<Person> list = this.myRealm.where(Person.class).findAll();
        Intent i = new Intent(getApplicationContext(), PersonListActivity.class);
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
        this.address.setError(message);
    }

    @Override
    public void setErrorBirthday(String message) {
        this.birthday.setError(message);
    }
}
