package com.example.exchangerates;

import java.util.ArrayList;
import java.util.HashMap;

interface View {
    void showExchangeRates(ArrayList<HashMap<String, String>> currencyList);
    void showConverterCurrencyList(ArrayList<HashMap<String, String>> currencyList);
    void showExchange();
    void showConverter();
    void updateSelectedCurrency(HashMap<String, String> currency);
    void showToast(int messageId);
    void showConversionResult(String result);
}