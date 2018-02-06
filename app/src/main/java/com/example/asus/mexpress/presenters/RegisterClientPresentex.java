package com.example.asus.mexpress.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.example.asus.mexpress.Interfaces.IRegisterClientPresentex;
import com.example.asus.mexpress.Interfaces.IRegisterClientView;
import com.example.asus.mexpress.R;

import java.util.regex.*;

/**
 * Created by ASUS on 12/11/2017.
 */

public class RegisterClientPresentex implements IRegisterClientPresentex {
    private Context context;
    private IRegisterClientView iRegisterClientView;

    public RegisterClientPresentex(Context context, IRegisterClientView iRegisterClientView) {
        this.context = context;
        this.iRegisterClientView = iRegisterClientView;
    }

    @Override
    public boolean validateRegisterFields(String name, String lastName, String phoneNumber, String location, String birthday) {
        boolean nameIsValid = nameIsValid(name, "name");
        boolean lastNameIsValid = nameIsValid(lastName, "lastName");
        boolean phoneNumberIsValid = phoneNumberIsValid(phoneNumber);

        return nameIsValid && lastNameIsValid && phoneNumberIsValid;
    }

    public boolean nameIsValid(String name, String field) {
        Pattern p = Pattern.compile(".*[0-9].*");
        Matcher m = p.matcher(name);
        boolean number = m.find();
        if (TextUtils.isEmpty(name) || number) {
            switch (field) {
                case "name":
                    this.iRegisterClientView.setErrorName(context.getString(R.string.err_rname));
                    break;
                case "lastName":
                    this.iRegisterClientView.setErrorLastName(context.getString(R.string.err_rlastname));
                    break;
                default:
                    break;
            }
            return false;
        }
        return true;
    }

    public boolean phoneNumberIsValid(String phone){
        Pattern p = Pattern.compile(".*[0-9].*");
        Matcher m = p.matcher(phone);
        boolean number = m.find();
        if(number) {
            return true;
        }else {
            this.iRegisterClientView.setErrorPhoneNumber(context.getString(R.string.err_rphonenumber));
            return false;
        }
    }
}
