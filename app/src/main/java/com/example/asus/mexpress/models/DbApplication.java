package com.example.asus.mexpress.models;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ASUS on 11/11/2017.
 */

public class DbApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("mexpress.realm")
                .build();

        Realm.setDefaultConfiguration(configuration);
    }
}

