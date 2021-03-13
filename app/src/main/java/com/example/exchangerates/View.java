package com.example.exchangerates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Map;

public class View extends AppCompatActivity {

    private Model.ExchangeRates list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}