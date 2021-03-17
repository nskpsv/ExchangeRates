package com.example.exchangerates;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentExchangeRates extends Fragment {

    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exchange_rates_fragment, null);

        list = v.findViewById(R.id.exchange_rates_list);

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        adapter = new SimpleAdapter(
                getActivity(),
                currencyList, R.layout.exchange_list_item,
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
