# CoinOneAPIAdapter
Library that helps you to implement CoinOne API easily



## How to use

First Add jitpack.io on yout root `build.gradle`:
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        jcenter()
    }
}
```


Add the following dependency on your app's `build.gradle`:
```gradle
compile 'com.github.itsnothingg:CoinOneAPIAdapter:-SNAPSHOT'
```

First of all, make sure you have these permissions granted in order for this lib to work.
```java
Manifest.permission.INTERNET
```


First thing you'll have to do is to set access key and secret key provided by Coin One by using the following code
```java
CoinOneAPIAdapter.getInstance().setAccessToken("yourAccessToken");
CoinOneAPIAdapter.getInstance().setSecretToken("yourSecretToken");
```
you can do it anywhere on your code, but mostly on application onCreate() or activity onCreate().

Then now you are ready to use Coin One API without caring nonce, signature, payload encoding. All you have to do is simple as follows
```java
/**
 * @param url is the endpoint.
 * @param body is Map<String, String> object with value you want inside.
 * you don't have to provide nonce and access-token field since it is auto-inserted.
 * @param callback is AsyncHttpResponseHandler object.
**/
CoinOneAPIAdapter.getInstance().call(<url>,<body>,<callback>);
```

### getting balance
```java
CoinOneAPIAdapter.getInstance().call("https://api.coinone.co.kr/v2/account/balance/" ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String byteToString = new String(responseBody,0,responseBody.length);
                try {
                    JSONObject jsonObject = new JSONObject(byteToString);
                    String remainingBTC = jsonObject.getJSONObject("btc").getString("balance");
                    String remainingKRW = jsonObject.getJSONObject("krw").getString("balance");
                    Log.i("TAG","your remaining bitcoin is : " + remainingBTC + "\n"
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
```

### buy
```java
Map<String, String> body = new HashMap<>();
body.put("price", "" + 5000000);
body.put("qty", "" +  0.0001);

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
```

### sell
```java
Map<String, String> body = new HashMap<>();
body.put("price", "" + 5000000);
body.put("qty", "" +  0.0001);

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
```

please take a look at sample module for more details.


