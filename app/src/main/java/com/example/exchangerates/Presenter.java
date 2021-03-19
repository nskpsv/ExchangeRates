package com.example.exchangerates;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;

public class Presenter {

    private Model model;
    private View view;

    Presenter(Model model) {
        this.model = model;
    }

    public void onAttach(View view) {
        this.view = view;
        onExchangeClick();
    }

    public void onDettach() {
        this.view = null;
    }

    public void onConverterListClick(int position) {

        Model.TransferExchangeRates callback = currencyList -> {
            HashMap<String, String> currency = currencyList.get(position);
            view.updateSelectedCurrency(currency);
        };
        model.loadExchangeRates(callback);
    }

    public void onConverterClick() {
            view.showConverter();

        Model.TransferExchangeRates callback = currencyList -> view.showConverterCurrencyList(currencyList);
        model.loadExchangeRates(callback);
    }

    public void onReloadClick() {
        onExchangeClick();
    }

    public void onExchangeClick() {
        view.showExchange();

        Model.TransferExchangeRates callback = currencyList -> view.showExchangeRates(currencyList);
        model.loadExchangeRates(callback);
    }

    public void onConvertButtonClick(String sum, HashMap<String, String> currency) {
        if (sum.isEmpty()) {
            view.showToast(R.string.toast_empty_sum);
        } else if (currency == null) {
            view.showToast(R.string.toast_choose_currency);
        } else {
            double s = Double.parseDouble(sum);
            double v = Double.parseDouble(currency.get("value"));
            int n = Integer.parseInt(currency.get("nominal").split(" ")[1]);
            s = (n * s) / v;
            view.showConversionResult(String.format("%.2f", s));
        }
    }
}
