package com.example.exchangerates;

interface View {
    void showRates(Model.ExchangeRates rates);
    void showExchange();
    void showConverter();
}