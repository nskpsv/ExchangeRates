package com.example.exchangerates;
//private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Model {

    private ExchangeRates exchangeRates;

    public void loadExchangeRates() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExchangeRatesApi api = retrofit.create(ExchangeRatesApi.class);
        Call<ExchangeRates> exRates = api.getExchangeRates();
        exRates.enqueue(new Callback<ExchangeRates>() {

        @Override
        public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
            if (response.isSuccessful()) {
                //exchangeRates = response.body();
                System.out.println("Printing currency list:");
                System.out.println(response.body().getCurrencyList().toString());
            } else {
                try {
                    System.out.println(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ExchangeRates> call, Throwable t) {
            System.out.println("Failure" + t);
        }
    });
    }

    public ExchangeRates getExchangeRates() {
        return exchangeRates;
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
    }
}
