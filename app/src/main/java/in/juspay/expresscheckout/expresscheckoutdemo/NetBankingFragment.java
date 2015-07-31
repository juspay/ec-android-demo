package in.juspay.expresscheckout.expresscheckoutdemo;

/**
 * Created by rohan on 1/6/15.
 */


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import in.juspay.godel.core.Godel;
import in.juspay.godel.core.GodelParams;
import in.juspay.godel.core.JuspayCallBack;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 1/6/15.
 */
public class NetBankingFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Button next;
    Spinner netBankingSpinner;
    List<String> netBankingOptions;
    TextView display;
    String redirectUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.netbanking_fragment, container, false);

        next= (Button) v.findViewById(R.id.bNetBanking);
        next.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                func_pay();
            }
        });
        netBankingSpinner= (Spinner) v.findViewById(R.id.spNetBanking);
        netBankingSpinner.setOnItemSelectedListener(this);
        display= (TextView) v.findViewById(R.id.tvDisplay);

        setSpinner();

        return v;
    }

    public void func_pay(){
        ServiceHandler serviceHandler = new ServiceHandler(6);
        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute());

        try {
            String response = asyncTask.get();
            response= response.substring(15, response.length());
            Log.d("NetBankingFragment", response);
            JSONObject payJson= new JSONObject(response);
            if(payJson.has("redirectUrl")) {
                //Successfully added the card
                //Intent part here
                redirectUrl= payJson.optString("redirectUrl");
                JSONObject postJson = new JSONObject(payJson.getString("redirectUrlPostParams"));
                String postData = "";
                for(int i = 0; i<postJson.names().length(); i++){
                    postData+=postJson.names().getString(i)+"="+postJson.get(postJson.names().getString(i))+"&";
                }
                GodelParams godelParams= new GodelParams();
                godelParams.setActionBarIcon(getResources().getDrawable(R.drawable.juspay_logo_white));
                godelParams.setActionBarBackgroundColor(new ColorDrawable(0xFF0000BB));

                godelParams.setMerchantId("expressCheckoutDemo");
                godelParams.setClientId("expressCheckoutDemo_android");
                godelParams.setTransactionId("1234567");
                godelParams.setOrderId("1234567");
                godelParams.setUrl(redirectUrl);
                if(postData!= null) {
                    godelParams.setPostData(postData);
                    Log.d("TEST!","POst Data : "+postData);
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

                    }

                    @Override
                    public void endUrlReached(String url) {
                        if(url.contains("status=CHARGED")){
                            i.putExtra("transactionResult", "Successful");
                        }else{
                            i.putExtra("transactionResult", "Unsuccessful");
                        }
                    }

                };
                String [] endUrl = {".*payment_response.*"};
                Godel.setEndUrls(endUrl);
                Godel.start(getActivity(), godelParams, callBack);
            }
        } catch (Exception e) {
            Log.d("func_pay_NetBanking","Exception Caught !");
            Toast.makeText(getActivity(), "Unable to make call.\n" + "Check your internet connection.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setSpinner(){
        netBankingOptions= new ArrayList<String>();
        netBankingOptions= BankList.getBankNames();

        ArrayAdapter netBankingOptionAdapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, netBankingOptions);
        netBankingOptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        netBankingSpinner.setAdapter(netBankingOptionAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.bNetBanking){
            //Not working yet
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        Toast.makeText(getActivity(), netBankingOptions.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}