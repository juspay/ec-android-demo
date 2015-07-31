package in.juspay.expresscheckout.expresscheckoutdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by rohan on 2/6/15.
 */
public class AddCard extends ActionBarActivity implements View.OnClickListener{

    Button add;
    EditText cardNumber;
    String response;
    TextView display;
    ProgressDialog ringProgressDialog;
    boolean isProgressGoingOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);

        init();
        setActionBar();
    }

    private void init(){
        cardNumber= (EditText) findViewById(R.id.etCardNumber);
        add= (Button) findViewById(R.id.bAdd);
        add.setOnClickListener(this);
        display= (TextView) findViewById(R.id.tvDisplay);
        isProgressGoingOn= false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bAdd:
                if(cardNumber.getText().toString().trim().length()!=0) {
                    launchRingDialog();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Enter Card Number", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        func_CardManagement();
        if(isProgressGoingOn) {
            ringProgressDialog.dismiss();
            isProgressGoingOn= false;
        }
        Intent i= new Intent(getApplicationContext(), CardManagement.class);
        i.putExtra("cardList", response);
        startActivity(i);
        finish();
    }

    public void launchRingDialog() {
        ringProgressDialog = ProgressDialog.show(AddCard.this, "Express Checkout", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        isProgressGoingOn= true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                func_addCard();
            }
        }).start();
    }

    public void func_addCard(){
        ServiceHandler serviceHandler = new ServiceHandler(5);
        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute());
        try {
            response = asyncTask.get();
            response= response.substring(15, response.length());
            Log.d("AddCard", response);
            JSONObject addCardJson= new JSONObject(response);
            if(addCardJson.has("cardToken")) {
                //Successfully added the card
                //Intent part here
//                Toast.makeText(AddCard.this, cardNumber.getText().toString() + " added successfully",
//                        Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

        } catch (Exception e) {
            Log.d("func_cardReader","Exception Caught !");
//            Toast.makeText(AddCard.this, "Unable to make call.\n" + "Check your internet connection.",
//                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void func_CardManagement(){
        ServiceHandler serviceHandler = new ServiceHandler(2);

        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute(new String[]{"9999999993"}));
        try {
            response = asyncTask.get();
            Log.d("func_CardManagement_AddCard", response);
        } catch (Exception e) {
            Log.d("func_CardManagement_AddCard","Exception Caught !");
            Toast.makeText(AddCard.this, "Unable to make call.\n" + "Check your internet connection.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setActionBar(){
        ActionBar action= getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
    }
}
