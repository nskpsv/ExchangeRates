package com.example.exchangerates;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

public class Activity extends AppCompatActivity implements View {

    private Presenter presenter;
    private FragmentConverter fragmentConverter;
    private FragmentExchangeRates fragmentExchangeRates;
    private ImageButton reloadButton;
    private ImageButton converterButton;
    private ImageButton exchangeButton;
    private OnClick onClickListener;

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

        fragmentExchangeRates = new FragmentExchangeRates();
        fragmentConverter = new FragmentConverter();
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.fragment_container, fragmentExchangeRates);
        fragmentManager.commit();

        presenter = new Presenter(new Model());

        onClickListener = new OnClick();

        exchangeButton = findViewById(R.id.exchange_button);
        exchangeButton.setOnClickListener(onClickListener);
        exchangeButton.setClickable(false);

        reloadButton = findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(onClickListener);

        converterButton = findViewById(R.id.converter_button);
        converterButton.setOnClickListener(onClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showRates(Model.ExchangeRates rates) {
        fragmentExchangeRates.showRates(rates);
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

        FragmentTransaction fragmentManager = getSupportFragmentManager()
                .beginTransaction();
        fragmentManager.replace(R.id.fragment_container, fragmentConverter);
        fragmentManager.commit();
    }

    private class OnClick implements android.view.View.OnClickListener {

        @Override
        public void onClick(android.view.View v) {

            switch (v.getId()) {
                case R.id.converter_button:
                    presenter.onConverterClick();
                    break;
                case R.id.exchange_button:
                    presenter.onExchangeClick();
                    break;
                case R.id.reload_button:
                    presenter.onReloadClick();
                default:
                    break;
            }
        }
    }


}