package in.juspay.expresscheckout.expresscheckout;


import android.util.Log;
//import in.juspay.express_checkout.model.AddCardResponse;
//import in.juspay.express_checkout.model.PaymentResponse;
//import in.juspay.express_checkout.model.AddCardRequest;
//import in.juspay.express_checkout.model.JuspayError;
//import in.juspay.express_checkout.model.PaymentMethodType;
//import in.juspay.express_checkout.model.PaymentRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import in.juspay.expresscheckout.expresscheckout.model.AddCardRequest;
import in.juspay.expresscheckout.expresscheckout.model.AddCardResponse;
import in.juspay.expresscheckout.expresscheckout.model.JuspayError;
import in.juspay.expresscheckout.expresscheckout.model.PaymentMethodType;
import in.juspay.expresscheckout.expresscheckout.model.PaymentRequest;
import in.juspay.expresscheckout.expresscheckout.model.PaymentResponse;

public class ExpressCheckout {

    public enum Environment {
        DEVELOPMENT("http://local.api.juspay.in"),
        PRODUCTION("https://api.juspay.in"),
        SANDBOX("https://sandbox.juspay.in");

        private final String baseUrl;

        Environment(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

    }


    private static String LOG_TAG = "ExpressCheckout";
    private static int connectionTimeout = 10 * 1000;
    private static int readTimeout = 10 * 1000;

    private String baseUrl;
    private String merchantId;

    private void initConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Make a HEAD request to keep a connection alive in the pool.
                try {
                    URL url = new URL(baseUrl);
                    HttpsURLConnection connection = null;
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.setConnectTimeout(connectionTimeout);
                    connection.setReadTimeout(readTimeout);
                    connection.setRequestMethod("HEAD");
                    connection.getContentLength();
                } catch (IOException e) {
                    Log.w(LOG_TAG, "HEAD request to cache connection failed. May be network is flaky.", e);
                }
            }
        }).start();
    }

    public ExpressCheckout(Environment env, String merchantId) {
        this.baseUrl = env.getBaseUrl();
        this.merchantId = merchantId;
        initConnection();
    }

    private String serializeParams(Map<String, String> parameters) {

        StringBuilder bufferUrl = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                if (entry.getValue() != null) {
                    bufferUrl.append(entry.getKey());
                    bufferUrl.append("=");
                    bufferUrl.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    bufferUrl.append("&");
                } else {
                    Log.w(LOG_TAG, "Param " + entry.getKey() + "'s value is null");
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding exception while trying to construct payload", e);
        }
        return bufferUrl.toString();
    }

    /**
     * It opens the connection to the given endPoint and
     * returns the http response as String.
     *
     * @param endPoint - The HTTP URL of the request
     * @return HTTP response as string
     */
    private String makeServiceCall(String endPoint, String encodedParams) throws IOException, JuspayError {

        HttpsURLConnection connection;
        StringBuilder buffer = new StringBuilder();

        URL url = new URL(endPoint);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(encodedParams.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        System.out.println("makeServiceCall Connection" + connection);
        // Setup the POST payload
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(encodedParams);
        wr.flush();
        wr.close();

        if (connection.getResponseCode() == 200) {
            // Read the response
            InputStream inputStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null)
                buffer.append(line);
            return buffer.toString();
        } else {
            InputStream errorStream = connection.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null)
                buffer.append(line);
            Log.e(LOG_TAG, buffer.toString());
            throw new JuspayError("HTTP Code:" + connection.getResponseCode() + " error." +
                    "Response contents:" + buffer.toString());
        }
    }

    protected Double getDoubleValue(Object inputObject) {
        if (inputObject instanceof Long) {
            return ((Long) inputObject).doubleValue();
        } else if (inputObject instanceof Double) {
            return ((Double) inputObject);
        } else {
            System.out.println("Can't seem to understand the input");
            return null;
        }
    }

    /**
     * Creates a new card
     *
     * @param addCardRequest - AddCardRequest with all the required params
     * @return AddCardResponse for the given request.
     */
    public AddCardResponse addCard(AddCardRequest addCardRequest) throws IOException, JuspayError {
        //Get the required params
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("merchant_id", merchantId);
        params.put("customer_id", addCardRequest.getCustomerId());
        params.put("customer_email", addCardRequest.getCustomerEmail());
        params.put("card_number", String.valueOf(addCardRequest.getCardNumber()));
        params.put("card_exp_year", String.valueOf(addCardRequest.getCardExpYear()));
        params.put("card_exp_month", String.valueOf(addCardRequest.getCardExpMonth()));
        params.put("name_on_card", addCardRequest.getNameOnCard() != null ? addCardRequest.getNameOnCard() : "");
        params.put("nickname", addCardRequest.getNickname() != null ? addCardRequest.getNickname() : "");

        String serializedParams = serializeParams(params);
        String url = baseUrl + "/card/add";

        String response = makeServiceCall(url, serializedParams);
        JSONObject json = null;
        try {
            json = (JSONObject) new JSONTokener(response).nextValue();

            AddCardResponse addCardResponse = new AddCardResponse();
            addCardResponse.setCardToken(json.getString("card_token"));
            addCardResponse.setCardReference(json.getString("card_reference"));

            return addCardResponse;
        } catch (JSONException e) {
            throw new JuspayError("Error parsing Juspay response:" + response, e);
        }
    }

    private PaymentResponse createPaymentResponse(JSONObject jsonResponse) throws JSONException {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus(jsonResponse.getString("status"));
        paymentResponse.setTxnId(jsonResponse.getString("txn_id"));
        paymentResponse.setOrderId(jsonResponse.getString("order_id"));
        JSONObject authDetail = jsonResponse.getJSONObject("payment").getJSONObject("authentication");
        paymentResponse.setRedirectUrlHttpMethod(authDetail.getString("method"));
        paymentResponse.setRedirectUrl(authDetail.getString("url"));
        paymentResponse.setRedirectUrlPostParams(authDetail.optString("params"));
        return paymentResponse;
    }

    private LinkedHashMap<String, String> createRequestParams(PaymentRequest paymentRequest) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();

        params.put("merchant_id", merchantId);
        params.put("order_id", paymentRequest.getOrderId());
        params.put("payment_method_type", paymentRequest.getPaymentMethodType());
        if (paymentRequest.getPaymentMethod() != null)
            params.put("payment_method", paymentRequest.getPaymentMethod());
        params.put("redirect_after_payment", paymentRequest.getRedirectAfterPayment().toString());
        params.put("format", paymentRequest.getFormat());


        if (PaymentMethodType.CARD.name().equals(paymentRequest.getPaymentMethodType())) {
            if (paymentRequest.getCardToken() != null) {
                params.put("card_token", paymentRequest.getCardToken());
            } else {
                params.put("card_number", paymentRequest.getCardNumber());
                params.put("card_exp_year", paymentRequest.getCardExpYear());
                params.put("card_exp_month", paymentRequest.getCardExpMonth());
                params.put("name_on_card", paymentRequest.getNameOnCard() != null ? paymentRequest.getNameOnCard() : "");
            }
            params.put("card_security_code", paymentRequest.getCardSecurityCode());
        } else if (!PaymentMethodType.NB.name().equals(paymentRequest.getPaymentMethodType())) {
            throw new IllegalStateException("Payment Method Type should be CARD / NB");
        }
        return params;
    }

    /**
     * Initiates the payment call and returns the response of Payment
     *
     * @param paymentRequest - PaymentRequest with all required params
     * @return PaymentResponse for the given request
     */
    public PaymentResponse makePayment(PaymentRequest paymentRequest) throws JuspayError, IOException {

        LinkedHashMap<String, String> params = createRequestParams(paymentRequest);

        String serializedParams = serializeParams(params);
        String url = baseUrl + "/txns";

        Log.d(LOG_TAG, "Serialized params: " + serializedParams);
        String response = makeServiceCall(url, serializedParams);

        try {
            JSONObject jsonResponse = (JSONObject) new JSONTokener(response).nextValue();

            return createPaymentResponse(jsonResponse);
        } catch (JSONException e) {
            throw new JuspayError("Error parsing Juspay response:" + response, e);
        }
    }
}