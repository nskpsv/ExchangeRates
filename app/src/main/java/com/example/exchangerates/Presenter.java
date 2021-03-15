package com.example.exchangerates;

public class Presenter {

    private Model model;
    private View view;

    Presenter(Model model) {
        this.model = model;
    }

    public void onAttach(View view) {
        this.view = view;
        loadRates();
    }

    public void onDettach() {
        this.view = null;
    }

    private Model.TransferExchangeRates callback = new Model.TransferExchangeRates() {
        @Override
        public void transfer(Model.ExchangeRates e) {
            view.showRates(e);
        }
    };
    private void loadRates() {
        model.loadExchangeRates(callback);


    }
}
