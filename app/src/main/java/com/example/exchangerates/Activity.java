package com.example.exchangerates;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity extends AppCompatActivity implements View {

    private Presenter presenter;
    private FragmentConverter fragmentConverter;
    private FragmentExchangeRates fragmentExchangeRates;
    private ImageButton reloadButton;
    private ImageButton converterButton;
    private ImageButton exchangeButton;
    private OnClick onClickListener;
    private TextView heading;

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

        fragmentExchangeRates = new FragmentExchangeRates();
        fragmentConverter = new FragmentConverter(presenter);
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.fragment_container, fragmentExchangeRates);
        fragmentManager.commit();

        onClickListener = new OnClick();

        exchangeButton = findViewById(R.id.exchange_button);
        exchangeButton.setOnClickListener(onClickListener);
        exchangeButton.setClickable(false);

        reloadButton = findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(onClickListener);

        converterButton = findViewById(R.id.converter_button);
        converterButton.setOnClickListener(onClickListener);
        heading = findViewById(R.id.heading);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showExchangeRates(ArrayList<HashMap<String, String>> currencyList) {
        fragmentExchangeRates.showRates(currencyList);
    }

    @Override
    public void showConverterCurrencyList(ArrayList<HashMap<String, String>> currencyList) {
        fragmentConverter.showCurrencyList(currencyList);
    }

    @Override
    public void showExchange() {
        exchangeButton.setClickable(false);
        converterButton.setClickable(true);

        FragmentTransaction fragmentManager = getSupportFragmentManager()
                .beginTransaction();
        fragmentManager.replace(R.id.fragment_container, fragmentExchangeRates);
        fragmentManager.commit();
    }

    @Override
    public void showConverter() {
        exchangeButton.setClickable(true);
        converterButton.setClickable(false);
        fragmentConverter.currency = null;
        //fragmentConverter.sumInRubles.setText("");


        FragmentTransaction fragmentManager = getSupportFragmentManager()
                .beginTransaction();
        fragmentManager.replace(R.id.fragment_container, fragmentConverter);
        fragmentManager.commit();
    }

    @Override
    public void updateSelectedCurrency(HashMap<String, String> currency) {
        fragmentConverter.updateSelectedCurrency(currency);
    }

    @Override
    public void showToast(int messageId) {
        Toast toast = Toast.makeText(getApplicationContext(), getString(messageId), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showConversionResult(String result) {
        fragmentConverter.showConversionResult(result);
    }

    private class OnClick implements android.view.View.OnClickListener {

        @Override
        public void onClick(android.view.View v) {

            switch (v.getId()) {
                case R.id.converter_button:
                    presenter.onConverterClick();
                    heading.setText(getString(R.string.converter));
                    break;
                case R.id.exchange_button:
                    presenter.onExchangeClick();
                    heading.setText(getString(R.string.app_name));
                    break;
                case R.id.reload_button:
                    presenter.onReloadClick();
                    heading.setText(getString(R.string.app_name));
                default:
                    break;
            }
        }
    }


}