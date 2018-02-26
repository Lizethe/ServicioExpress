package com.example.asus.mexpress.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ASUS on 26/02/2018.
 */

public class Location extends RealmObject {
    @PrimaryKey
    private String id;
    private double latitude;
    private double longitude;
}
