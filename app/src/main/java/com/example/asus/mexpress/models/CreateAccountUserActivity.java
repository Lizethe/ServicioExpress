package com.example.asus.mexpress.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.asus.mexpress.Interfaces.ILoginPresentx;
import com.example.asus.mexpress.Interfaces.ILoginView;
import com.example.asus.mexpress.R;
import com.example.asus.mexpress.presenters.LoginPresentex;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CreateAccountUserActivity extends AppCompatActivity {
    private Spinner userType;
    private EditText name, username, pwd, pwdAgain;
    private SessionManager session;
    private Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_user);
        userType = (Spinner) findViewById(R.id.list_type);
        ArrayAdapter<CharSequence> userTypeList = ArrayAdapter
                .createFromResource(this, R.array.list_user_type, R.layout.support_simple_spinner_dropdown_item);
        userTypeList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        userType.setAdapter(userTypeList);
        name = (EditText) findViewById(R.id.txt_name_user);
        username = (EditText) findViewById(R.id.txt_username);
        pwd = (EditText) findViewById(R.id.txt_password);
        pwdAgain = (EditText) findViewById(R.id.txt_passwordagain);
        session = new SessionManager(getApplicationContext());
        Realm.init(getApplicationContext());
        this.myRealm = Realm.getDefaultInstance();
    }

    public void createAccountUser(View view) {
        String user_local = this.username.getText().toString();
        boolean valid = this.validateFields();
        if (valid) {
            this.myRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    saveUser(realm);
                }
            });
        }
    }

    private boolean validateFields(){
        String lName = this.name.getText().toString();
        String lUsername = this.username.getText().toString();
        String lPwd = this.pwd.getText().toString();
        String lPwdAgain = this.pwdAgain.getText().toString();

        if (TextUtils.isEmpty(lName)) {
            this.name.setError(this.getApplicationContext().getString(R.string.err_rname));
            return false;
        }

        if (TextUtils.isEmpty(lUsername)) {
            this.username.setError(this.getApplicationContext().getString(R.string.err_user));
            return false;
        }else{
            RealmQuery<User> user = this.myRealm.where(User.class).equalTo("username", lUsername);
            if(user.count() > 0){
                this.username.setError(this.getApplicationContext().getString(R.string.err_username_exists));
                return false;
            }

        }

        if (TextUtils.isEmpty(lPwd)) {
            this.pwd.setError(this.getApplicationContext().getString(R.string.err_password));
            return false;
        }

        if (TextUtils.isEmpty(lPwdAgain) || !lPwd.equals(lPwdAgain)) {
            this.pwdAgain.setError(this.getApplicationContext().getString(R.string.err_passwordagain));
            return false;
        }

        return true;
    }

    private void saveUser(Realm realm) {
        String user_local = this.username.getText().toString();
        User user = realm.createObject(User.class, UUID.randomUUID().toString());
        user.setName(this.name.getText().toString());
        user.setUsername(this.username.getText().toString());
        user.setPassword(this.pwd.getText().toString());
        user.setType(this.userType.getSelectedItem().toString());
        session.saveUser(user_local);
        Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(i);
    }
}
