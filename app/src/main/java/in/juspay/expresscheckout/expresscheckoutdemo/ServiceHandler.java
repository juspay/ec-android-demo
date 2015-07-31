package in.juspay.expresscheckout.expresscheckoutdemo;

/**
 * Created by rohan on 2/6/15.
 */

import android.os.AsyncTask;
import android.util.Log;
import in.juspay.expresscheckout.expresscheckout.ExpressCheckout;
import in.juspay.expresscheckout.expresscheckout.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ServiceHandler extends AsyncTask<String, Void, String>{

    private String baseURL= "https://juspay-express.herokuapp.com";
    private static final String LOG_TAG= "Service Handler";
    public int option;
    static ExpressCheckout juspayEC = new ExpressCheckout(ExpressCheckout.Environment.PRODUCTION, "juspay_test");



    public ServiceHandler(int i){
        option= i;
    }
    @Override
    protected String doInBackground(String... params) {
        switch(option)
        {
            case 1:
                return this.createOrder(params[0], params[1]);
            case 2:
                return this.listCards(params[0]);
            case 3:
                return this.deleteCard(params[0], params[1]);
            case 4:
                try {
                    return this.testNewCardPaymentFlow();
                } catch (JuspayError juspayError) {
                    juspayError.printStackTrace();
                    Log.d(LOG_TAG, juspayError.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, e.toString());
                }
            case 5:
                try {
                    return this.testAddCard();
                } catch (JuspayError juspayError) {
                    juspayError.printStackTrace();
                    Log.d(LOG_TAG, juspayError.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, e.toString());
                }
            case 6:
                try {
                    return this.netBankingPaymentFlow();
                } catch (JuspayError juspayError) {
                    juspayError.printStackTrace();
                    Log.d(LOG_TAG, juspayError.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, e.toString());
                }
            default:
                return null;

        }
    }

    private static String netBankingPaymentFlow()throws JuspayError, IOException{
        PaymentRequest netBankingPayReq = new PaymentRequest();
        netBankingPayReq.setOrderId("1433232497936");
        netBankingPayReq.setPaymentMethodType(PaymentMethodType.NB.name());
        netBankingPayReq.setPaymentMethod(NBPaymentMethod.NB_ICICI.name());

        PaymentResponse netBankingPayResp = juspayEC.makePayment(netBankingPayReq);
        return netBankingPayResp.toString();
    }
    private static String testNewCardPaymentFlow() throws JuspayError, IOException {
        System.out.println("--------Testing New Card Flow");

        // Stored Card Payment Example
        PaymentRequest cardPayReq = new PaymentRequest();
        cardPayReq.setOrderId("1433232497936");
        cardPayReq.setPaymentMethodType(PaymentMethodType.CARD.name());
        cardPayReq.setCardNumber("4242424242424242");
        cardPayReq.setCardExpMonth("09");
        cardPayReq.setCardExpYear("2017");
        cardPayReq.setCardSecurityCode("111");
        cardPayReq.setSaveToLocker(true); // Will be saved when 3d-secure/OTP verification step completes.

        PaymentResponse cardPayResp = juspayEC.makePayment(cardPayReq);
        Log.i(LOG_TAG, "Card Payment response:  " + cardPayResp);
        return cardPayResp.toString();
    }

    private static String testAddCard() throws JuspayError, IOException {
        System.out.println("--------Testing Add Card");

        // Adding card from client, directly with Juspay
        AddCardRequest acr = new AddCardRequest();
        acr.setCustomerId("C3659650");
        acr.setCardNumber("4422442244224422");
        acr.setCardExpMonth("08");
        acr.setCardExpYear("2016");
        acr.setNameOnCard("KK");
        AddCardResponse resp = juspayEC.addCard(acr);
        Log.i(LOG_TAG, "Add card response:  " + resp);
        return resp.toString();
    }

    public String createOrder(String amount, String authToken)
    {
        String s=null;
        String url= baseURL + "/init_payment?amount="+amount + "&auth_token=" + authToken;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try{
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity= null;
            entity= response.getEntity();
            s= EntityUtils.toString(entity);
            Log.d("Logout response", s);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return s;
    }

    public String listCards(String authToken){
        {
            String s=null;
            String url= baseURL + "/list_cards?auth_token=" + authToken;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            try{
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = httpClient.execute(request);

                HttpEntity entity= null;
                entity= response.getEntity();
                s= EntityUtils.toString(entity);
                Log.d("Logout response", s);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        }
    }

    public String deleteCard(String authToken, String cardToken){
            String s=null;
            String url= baseURL + "/delete_cards?auth_token=" + authToken + "&card_token=" + cardToken;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            try{
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = httpClient.execute(request);

                HttpEntity entity= null;
                entity= response.getEntity();
                s= EntityUtils.toString(entity);
                Log.d("Logout response", s);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
    }
}