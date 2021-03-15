package com.example.exchangerates;
//private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

        @Override
        public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
            if (response.isSuccessful()) {
                callback.transfer(response.body());
            } else { System.out.println(response.errorBody()); }
        }

        @Override
        public void onFailure(Call<ExchangeRates> call, Throwable t) {
            System.out.println("Failure" + t);
        }
    });
    }

    interface TransferExchangeRates {
        void transfer(ExchangeRates e);
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
}
