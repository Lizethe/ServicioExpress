package com.example.asus.mexpress.presenters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.asus.mexpress.Interfaces.ILoginPresentx;
import com.example.asus.mexpress.Interfaces.ILoginView;
import com.example.asus.mexpress.R;
import com.example.asus.mexpress.models.WelcomeActivity;

/**
 * Created by Lizeth Ovando Velasquez on 10/11/2017.
 */

public class LoginPresentex implements ILoginPresentx {
    private Context context;
    private ILoginView iLoginView;

    public LoginPresentex(Context context, ILoginView iLoginView) {
        this.context = context;
        this.iLoginView = iLoginView;
    }

    @Override
    public boolean validateFields(String user, String pwd) {
        boolean corret = true;
        if (TextUtils.isEmpty(user)) {
            corret = false;
            this.iLoginView.setErrorUser(context.getString(R.string.err_user));
        }

        if (TextUtils.isEmpty(pwd)) {
            corret = false;
            this.iLoginView.setErrorPassword(context.getString(R.string.err_password));
        }
        return corret;
    }
}
