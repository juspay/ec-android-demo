package in.juspay.expresscheckout.expresscheckoutdemo;

import android.app.Activity;
import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rohan on 1/6/15.
 */
public class CreateOrder extends ActionBarActivity implements View.OnClickListener{

    EditText amount;
    String response;
    TextView responseText;
    ProgressDialog ringProgressDialog;
    boolean isSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new);

        init();
        setActionBar();
    }

    private void init(){
        responseText= (TextView) findViewById(R.id.tvResponse);
        Button go= (Button) findViewById(R.id.bTest);
        Button manageCards= (Button) findViewById(R.id.bManageCards);
        go.setOnClickListener(this);
        manageCards.setOnClickListener(this);
        amount= (EditText) findViewById(R.id.etAmount);
    }

    public void func_createOrder()
    {
        ServiceHandler serviceHandler = new ServiceHandler(1);

        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute(new String[]{amount.getText().toString(),"9999999993"}));
        try {
            response = asyncTask.get();
            Log.d("response_main", response);
//            responseText.setText(response);
            //Intent for next Activity
            Intent i = new Intent(getApplicationContext(), Pay.class);
            i.putExtra("response_create_order", response);
            func_CardManagementWithoutIntent();
            i.putExtra("cardList", response);
            startActivity(i);
            if(ringProgressDialog.isShowing())
                ringProgressDialog.dismiss();

        } catch (Exception e) {
            Log.d("func_createOrder","Exception Caught !");
            if(ringProgressDialog != null) {
                ringProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to make call.\nCheck your internet connection.",
                        Toast.LENGTH_SHORT).show();
            }
            e.printStackTrace();
        }
    }

    private void func_CardManagement(){
        ServiceHandler serviceHandler = new ServiceHandler(2);

        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute(new String[]{"9999999993"}));
        try {
            response = asyncTask.get();
            Log.d("func_CardManagement_CreateOrder", response);
            //Intent part here
            Intent intentManagement= new Intent(getApplicationContext(), CardManagement.class);
            intentManagement.putExtra("cardList",response);
            startActivity(intentManagement);
            ringProgressDialog.dismiss();
        } catch (Exception e) {
            Log.d("func_CardManagement_CreateOrder","Exception Caught !");
//            Toast.makeText(getApplicationContext(), "Unable to make call.\n" + "Check your internet connection.",
//                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void func_CardManagementWithoutIntent(){
        ServiceHandler serviceHandler = new ServiceHandler(2);

        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute(new String[]{"9999999993"}));
        try {
            response = asyncTask.get();
            Log.d("func_CardManagement_CreateOrder", response);
            ringProgressDialog.dismiss();
        } catch (Exception e) {
            Log.d("func_CardManagement_CreateOrder","Exception Caught !");
//            Toast.makeText(getApplicationContext(), "Unable to make call.\n" + "Check your internet connection.",
//                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.bTest){
            if(amount.getText().toString().trim().length()!=0) {
                launchRingDialogCreate();
            }
            else{
                Toast.makeText(getApplicationContext(), "Enter the amount", Toast.LENGTH_SHORT).show();
            }
        }

        if(v.getId()==R.id.bManageCards){
            launchRingDialogManageCards();
        }
    }

    public void launchRingDialogCreate() {
        ringProgressDialog = ProgressDialog.show(CreateOrder.this, "Express Checkout",	"Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                func_createOrder();
            }
        }).start();
    }
    public void launchRingDialogManageCards() {
        ringProgressDialog = ProgressDialog.show(CreateOrder.this, "Express Checkout",	"Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                func_CardManagement();
            }
        }).start();
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
                finish();
                break;
        }

        return true;
    }

    private void setActionBar(){
        ActionBar action= getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
    }
}
