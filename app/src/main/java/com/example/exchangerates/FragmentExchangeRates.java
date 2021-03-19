package com.example.exchangerates;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void showRates(ArrayList<HashMap<String, String>> currencyList) {

        ListAdapter adapter;

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
