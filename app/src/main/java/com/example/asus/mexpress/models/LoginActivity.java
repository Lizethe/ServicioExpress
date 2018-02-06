package com.example.asus.mexpress.models;

/**
 * Created by Lizeth Ovando Velasquez on 10/11/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.asus.mexpress.Interfaces.ILoginPresentx;
import com.example.asus.mexpress.Interfaces.ILoginView;
import com.example.asus.mexpress.R;
import com.example.asus.mexpress.presenters.LoginPresentex;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private EditText user, pwd;
    private ILoginPresentx loginPresentx;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.txt_user);
        pwd = (EditText) findViewById(R.id.txt_password);
        session = new SessionManager(getApplicationContext());
        this.loginPresentx = new LoginPresentex(getApplicationContext(), this);
    }

    public void validateFields(View view){
        String user_local = this.user.getText().toString();
        boolean validate = this.loginPresentx.validateFields(user_local, this.pwd.getText().toString());
        session.saveUser(user_local);
        if (validate){
            Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void setErrorUser(String message) {
        user.setError(message);
    }

    @Override
    public void setErrorPassword(String message) {
        pwd.setError(message);
    }
}
