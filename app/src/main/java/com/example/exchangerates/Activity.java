package com.example.exchangerates;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity extends AppCompatActivity implements View {

    private Presenter presenter;
    private ListView list;

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDettach();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(new Model());
        list = findViewById(R.id.exchange_rates_list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showRates(Model.ExchangeRates rates) {

        ArrayList<HashMap<String, String>> currencyList = new ArrayList<>();
        HashMap<String, String> currency;
        ListAdapter adapter;
        double value;
        double previous;

        for (String key : rates.getCurrencyList().keySet()) {
            currencyList.add(rates.getCurrencyList().get(key).getMapCurrency());
        }

        for (int i = 0; i < currencyList.size(); i++) {
            currency = currencyList.get(i);

            currency.replace("nominal", "за " + currency.get("nominal") + " ед.");
            currency.replace("currencyCode", currency.get("currencyCode") + "/RUB");

            value = Double.valueOf(currency.get("value"));
            previous = Double.valueOf(currency.get("previousValue"));

            if (value >= previous) {
                currency.replace("previousValue", "+" + String.format("%.4f", (value - previous)));
            } else {
                currency.replace("previousValue", "" + String.format("%.4f", (value - previous)));
            }

            currencyList.set(i, currency);
        }

        adapter = new SimpleAdapter(this, currencyList, R.layout.list_item,
                new String[] {
                        "currencyCode",
                        "nominal",
                        "currencyName",
                        "value",
                        "previousValue"},

                new int[] {
                        R.id.currency_code,
                        R.id.nominal,
                        R.id.currency_name,
                        R.id.value,
                        R.id.dynamics});

        list.setAdapter(adapter);


    }
}