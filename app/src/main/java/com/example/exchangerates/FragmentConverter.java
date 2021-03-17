package com.example.exchangerates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class FragmentConverter extends Fragment {

    ListView currencyList;
    EditText summInRubles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.converter_fragment, null);

        currencyList = v.findViewById(R.id.currency_list);
        summInRubles = v.findViewById(R.id.sumInRubles);

        return v;
    }
}