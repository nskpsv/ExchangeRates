package com.example.exchangerates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentConverter extends Fragment {

    FragmentConverter(Presenter p) { presenter = p; }

    ListView converterCurrencyList;
    EditText sumInRubles;
    Presenter presenter;
    TextView selectedCurrency;
    TextView afterConvert;
    Button convertButton;
    HashMap<String, String> currency;


    @Override
    public void onResume() {
        super.onResume();
        clearConverter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.converter_fragment, null);

        converterCurrencyList = v.findViewById(R.id.currency_list);
        converterCurrencyList.setOnItemClickListener(new OnConverterListClick());
        sumInRubles = v.findViewById(R.id.sumInRubles);
        selectedCurrency = v.findViewById(R.id.selected_currency);
        afterConvert = v.findViewById(R.id.after_convert);
        convertButton = v.findViewById(R.id.convert_button);
        convertButton.setOnClickListener(new OnConvertButtonClick());

        return v;
    }

    public void showConversionResult(String result) {
        afterConvert.setText(result);
    }

    public class OnConvertButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            presenter.onConvertButtonClick(sumInRubles.getText().toString(), currency);
        }
    }

    public class OnConverterListClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
            presenter.onConverterListClick(position);
        }
    }

    public void updateSelectedCurrency(HashMap<String, String> currency) {
        this.currency = currency;
        selectedCurrency.setText(currency.get("currencyCode") + " " + currency.get("currencyName"));
    }

    public void clearConverter() {
        currency = null;
        selectedCurrency.setText(null);
        sumInRubles.setText(null);
    }

    public void showCurrencyList(ArrayList<HashMap<String, String>> currencyList) {

        ListAdapter adapter;

        adapter = new SimpleAdapter(
                getActivity(),
                currencyList,
                R.layout.convert_list_item,
                new String[] {
                        "currencyCode",
                        "currencyName",
                        "value" },
                new int[] {
                        R.id.currency_code,
                        R.id.currency_name,
                        R.id.value
                });
        converterCurrencyList.setAdapter(adapter);
    }
}