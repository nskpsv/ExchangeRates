package com.example.exchangerates;
//private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Model {

    public void loadExchangeRates(TransferExchangeRates callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExchangeRatesApi api = retrofit.create(ExchangeRatesApi.class);
        Call<ExchangeRates> exRates = api.getExchangeRates();
        exRates.enqueue(new Callback<ExchangeRates>() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
            if (response.isSuccessful()) {
                callback.transfer(getCurrencyList(response.body()));
            } else { System.out.println(response.errorBody()); }
        }

        @Override
        public void onFailure(Call<ExchangeRates> call, Throwable t) {
            System.out.println("Failure" + t);
        }
    });
    }

    interface TransferExchangeRates {
        void transfer(ArrayList<HashMap<String, String>> currencyList);
    }

    private interface ExchangeRatesApi {
        @GET ("daily_json.js")
        Call<ExchangeRates> getExchangeRates();
    }

    public class ExchangeRates {

        @SerializedName("Timestamp")
        private String timeStamp;

        @SerializedName("Valute")
        private Map<String, Currency> currencyList;

        public String getTimeStamp() {
            return timeStamp;
        }
        public Map<String, Currency> getCurrencyList() {
            return currencyList;
        }
    }


   public class Currency {

       @SerializedName("CharCode")
       private String currencyCode;

       @SerializedName("Nominal")
       private int nominal;

       @SerializedName("Name")
       private String currencyName;

       @SerializedName("Value")
       private double value;

       @SerializedName("Previous")
       private double previousValue;

       public String getCurrencyCode() {
           return currencyCode;
       }
       public int getNominal() {
           return nominal;
       }
       public String getCurrencyName() {
           return currencyName;
       }
       public double getValue() {
           return value;
       }
       public double getPreviousValue() {
           return previousValue;
       }

       public HashMap<String, String> getMapCurrency() {

           HashMap<String, String> result = new HashMap<String, String>();
           result.put("currencyCode", currencyCode);
           result.put("nominal", String.valueOf(nominal));
           result.put("currencyName", currencyName);
           result.put("value", String.valueOf(value));
           result.put("previousValue", String.valueOf(previousValue));

           return result;
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<HashMap<String, String>> getCurrencyList(ExchangeRates rates) {

        ArrayList<HashMap<String, String>> currencyList = new ArrayList<>();
        HashMap<String, String> currency;
        double value;
        double previous;

        for (String key : rates.getCurrencyList().keySet()) {
            currencyList.add(rates.getCurrencyList().get(key).getMapCurrency());
        }

        for (int i = 0; i < currencyList.size(); i++) {
            currency = currencyList.get(i);

            currency.replace("nominal", "за " + currency.get("nominal") + " ед.");

            value = Double.valueOf(currency.get("value"));
            previous = Double.valueOf(currency.get("previousValue"));

            if (value >= previous) {
                currency.replace("previousValue", "+" + String.format("%.4f", (value - previous)));
            } else {
                currency.replace("previousValue", "" + String.format("%.4f", (value - previous)));
            }

            currencyList.set(i, currency);
        }
        return currencyList;
    }
}
