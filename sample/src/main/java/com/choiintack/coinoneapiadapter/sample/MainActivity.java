package com.choiintack.coinoneapiadapter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.choiintack.coinoneapiadapter.CoinOneAPIAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * You can set your token on anywhere you want
         * ex) application create()..
         */
        CoinOneAPIAdapter.getInstance().setAccessToken("yourAccessToken");
        CoinOneAPIAdapter.getInstance().setSecretToken("yourSecretToken");

        // buy(0.001F, 5000000);
        // sell(0.001F, 5000000);
        // getCompletedOrders()
         getBalance();
    }

    public void sell(float qty, int price) {
        Map<String, String> body = new HashMap<>();
        body.put("price", "" + price);
        body.put("qty", "" +  qty);

        CoinOneAPIAdapter.getInstance().call("https://api.coinone.co.kr/v2/order/limit_sell/", body ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String byteToString = new String(responseBody,0,responseBody.length);
                Log.i("response", byteToString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String byteToString = new String(responseBody,0,responseBody.length);
                Log.i("error_response", byteToString);
            }
        });
    }

    public void buy(float qty, int price) {
        Map<String, String> body = new HashMap<>();
        body.put("price", "" + price);
        body.put("qty", "" +  qty);

        CoinOneAPIAdapter.getInstance().call("https://api.coinone.co.kr/v2/order/limit_buy/", body ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String byteToString = new String(responseBody,0,responseBody.length);
                Log.i("response", byteToString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String byteToString = new String(responseBody,0,responseBody.length);
                Log.i("error_response", byteToString);
            }
        });
    }

    public void getCompletedOrders() {

        CoinOneAPIAdapter.getInstance().call("https://api.coinone.co.kr/v2/order/complete_orders/" ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String byteToString = new String(responseBody,0,responseBody.length);
                Log.i("response", byteToString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String byteToString = new String(responseBody,0,responseBody.length);
                Log.i("error_response", byteToString);
            }
        });
    }

    public void getBalance() {
        CoinOneAPIAdapter.getInstance().call("https://api.coinone.co.kr/v2/account/balance/" ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String byteToString = new String(responseBody,0,responseBody.length);
                try {
                    JSONObject jsonObject = new JSONObject(byteToString);
                    String remainingBTC = jsonObject.getJSONObject("btc").getString("balance");
                    String remainingKRW = jsonObject.getJSONObject("krw").getString("balance");
                    updateText("your remaining bitcoin is : " + remainingBTC + "\n"
                        + "your remaining krw is : ₩" + remainingKRW );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("response", byteToString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String byteToString = new String(responseBody,0,responseBody.length);

                Log.i("error_response", byteToString);
            }
        });
    }

    void updateText(String text) {
        TextView textView = (TextView) findViewById(R.id.balance);
        textView.setText(text);
    }
}
