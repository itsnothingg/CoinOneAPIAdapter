package com.choiintack.coinoneapiadapter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by choiintack on 2017. 10. 15..
 */

public class CoinOneAPIAdapter {

    private static volatile CoinOneAPIAdapter singletonInstance = null;

    private AsyncHttpClient client;

    private String accessToken = "";

    private String secretToken = "";


    public static CoinOneAPIAdapter getInstance(){
        if(singletonInstance==null){
            synchronized (CoinOneAPIAdapter.class){
                if(singletonInstance==null){
                    singletonInstance = new CoinOneAPIAdapter();
                }
            }
        }
        return singletonInstance;
    }

    public CoinOneAPIAdapter() {
        client = new AsyncHttpClient();
        client.addHeader("content-type", "application/json");
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public void call(String url, ResponseHandlerInterface responseHandler) {
        this.call(url, null, responseHandler);
    }



    public void call(String url, Map<String, String> body, ResponseHandlerInterface responseHandler) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("access_token", this.accessToken);
            payload.put("nonce", System.currentTimeMillis());
            if (body != null) {
                for (Map.Entry<String, String> entry : body.entrySet()) {
                    payload.put(entry.getKey(), entry.getValue());
                }
            }
            String payloadEncode = EncoderHelper.base64Encode(payload.toString());
            String signature = EncoderHelper.signatureEncode(this.secretToken.toUpperCase(), payloadEncode);

            client.addHeader("X-COINONE-PAYLOAD", payloadEncode);
            client.addHeader("X-COINONE-SIGNATURE", signature);
            client.post(url, responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }









}










