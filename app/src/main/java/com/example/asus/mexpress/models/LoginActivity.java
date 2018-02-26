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

import io.realm.Realm;
import io.realm.RealmQuery;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private EditText user, pwd;
    private ILoginPresentx loginPresentx;
    private SessionManager session;
    private Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.txt_user);
        pwd = (EditText) findViewById(R.id.txt_password);
        session = new SessionManager(getApplicationContext());
        this.loginPresentx = new LoginPresentex(getApplicationContext(), this);
        Realm.init(getApplicationContext());
        this.myRealm = Realm.getDefaultInstance();
    }

    public void validateFields(View view) {
        String lUsername = this.user.getText().toString();
        String lPasswd = this.pwd.getText().toString();
        boolean validate = this.loginPresentx.validateFields(lUsername, this.pwd.getText().toString());
        User userReg = this.myRealm.where(User.class)
                .equalTo("username", lUsername)
                .equalTo("password", lPasswd)
                .findFirst();
        System.out.println("Usuario: " + userReg);
        if (validate && userReg != null) {
            session.saveUser(lUsername);
            session.saveType(userReg.getType());
            Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(i);
        } else {
            this.user.setError(this.getApplicationContext().getString(R.string.err_user_pass));
        }
    }

    public void createAccountUser(View view) {
        Intent i = new Intent(getApplicationContext(), CreateAccountUserActivity.class);
        startActivity(i);
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
