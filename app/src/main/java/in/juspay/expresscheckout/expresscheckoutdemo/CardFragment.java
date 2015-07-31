package in.juspay.expresscheckout.expresscheckoutdemo;

/**
 * Created by rohan on 1/6/15.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.juspay.godel.core.Godel;
import in.juspay.godel.core.GodelParams;
import in.juspay.godel.core.JuspayCallBack;

/**
 * Created by rohan on 1/6/15.
 */
public class CardFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    List<String> savedCards, expiryMonth, expiryYear;
    Spinner savedCardsSpinner;
    EditText cardNumber, cardExpiryMonth, cardExpiryYear;
    ProgressDialog ringProgressDialog;
    String redirectUrl, postData;
    Button next;
    boolean hasCalled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.card_fragment, container, false);
        init(v);
        getJsonData();
        setSpinner();
        return v;
    }

    private void init(View v){
        next = (Button) v.findViewById(R.id.bCard);
        next.setOnClickListener(this);

        savedCardsSpinner= (Spinner) v.findViewById(R.id.spSavedCards);
        savedCardsSpinner.setOnItemSelectedListener(this);

        cardNumber= (EditText) v.findViewById(R.id.etCardNumber);
        cardExpiryMonth= (EditText) v.findViewById(R.id.etExpiryMonth);
        cardExpiryYear= (EditText) v.findViewById(R.id.etExpiryYear);

        savedCards= new ArrayList<String>();
        expiryMonth= new ArrayList<String>();
        expiryYear= new ArrayList<String>();

        postData= null;
        hasCalled= false;
    }

    private void setSpinner() {
        ArrayAdapter savedCardsAdapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, savedCards);
        savedCardsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        savedCardsSpinner.setAdapter(savedCardsAdapter);
    }

    private void getJsonData(){
        Intent intent= getActivity().getIntent();
        String cardListResponse= intent.getStringExtra("cardList");
        try {
            JSONObject object = new JSONObject(cardListResponse);
            JSONObject obj = new JSONObject(object.optString("cards"));
            JSONArray arr = new JSONArray(obj.optString("cards"));
            for (int i = 0; i < arr.length(); i++) {
                savedCards.add(arr.getJSONObject(i).optString("card_number"));
                expiryMonth.add(arr.getJSONObject(i).optString("card_exp_month"));
                expiryYear.add( arr.getJSONObject(i).optString("card_exp_year") );
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "Unable to parse JSON", Toast.LENGTH_SHORT).show();
            Log.d("CardFragment","Unable to parse JSON");
            e.printStackTrace();
        }
    }

    public void launchRingDialog() {
        ringProgressDialog = ProgressDialog.show(getActivity(), "Express Checkout", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                func_pay();
            }
        }).start();
    }

    public void func_pay(){
        ServiceHandler serviceHandler = new ServiceHandler(4);
        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute());

        try {
            String response = asyncTask.get();
            response= response.substring(15, response.length());
            Log.d("NetBankingFragment", response);
            JSONObject payJson= new JSONObject(response);
            if(payJson.has("redirectUrl")) {
                //Successfully added the card, start Godel
                if(payJson.optString("redirectUrlHttpMethod").equals("POST"))
                    postData= payJson.optString("redirectUrlHttpMethod");
                redirectUrl= payJson.optString("redirectUrl");
                startGodelActivity();
            }
        } catch (Exception e) {
            Log.d("func_pay_NetBanking","Exception Caught !");
//            Toast.makeText(getActivity(), "Unable to make call.\n" + "Check your internet connection.",
//                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void startGodelActivity(){
        GodelParams godelParams= new GodelParams();
        godelParams.setActionBarIcon(getResources().getDrawable(R.drawable.juspay_logo_white));
        godelParams.setActionBarBackgroundColor(new ColorDrawable(0xFF0000BB));

        godelParams.setMerchantId("expressCheckoutDemo");
        godelParams.setClientId("expressCheckoutDemo_android");
        godelParams.setTransactionId("1234567");
        godelParams.setOrderId("1234567");
        Log.d("TEST!","url i am sending was "+redirectUrl);
        godelParams.setUrl(redirectUrl);
        if(postData!= null) {
            godelParams.setPostData(postData);
            postData= null;
        }

        godelParams.setDisplayNote("Recharge with RS 10");
        godelParams.setCustomerId("customerId");
        godelParams.setCustomerEmail("customerEmail");
        godelParams.setRemarks("Remarks for Netbanking Transaction");
        godelParams.setCustomerPhoneNumber("customerPhoneNumber");

        //Define callback
        JuspayCallBack callBack = new JuspayCallBack() {
            Intent i= new Intent(getActivity(), Result.class);
            @Override
            public void ontransactionAborted() {
                if(ringProgressDialog!=null)
                    ringProgressDialog.dismiss();
                if(!hasCalled) {
                    i.putExtra("transactionResult", "Aborted");
                    startActivity(i);
                    hasCalled= true;
                }
            }

            @Override
            public void endUrlReached(String url) {
                if(url.contains("status=CHARGED")){
                    i.putExtra("transactionResult", "Successful");
                }else{
                    i.putExtra("transactionResult", "Unsuccessful");
                }
                if(ringProgressDialog!=null)
                    ringProgressDialog.dismiss();
                if(!hasCalled) {
                    startActivity(i);
                    hasCalled= true;
                }
            }

        };
        String [] endUrl = {".*payment_response.*"};
        Godel.setEndUrls(endUrl);
        Godel.start(getActivity(), godelParams, callBack);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bCard) {
            launchRingDialog();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cardNumber.setText(savedCards.get(position));
        cardExpiryMonth.setText(expiryMonth.get(position));
        cardExpiryYear.setText(expiryYear.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}