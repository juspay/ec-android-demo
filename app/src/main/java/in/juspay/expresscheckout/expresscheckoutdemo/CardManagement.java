package in.juspay.expresscheckout.expresscheckoutdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 2/6/15.
 */
public class CardManagement extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener{

    ListView cards;
    List<String> cardList, nameList, expiryMonthList, expiryYearList;
    int currentPosition;
//    ArrayAdapter<String> cardAdapter;
    Button addCard;
    String cardListResponse;
    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_management);

        init();
        setActionBar();
        getJsonData();
        setListData();
    }

    private void getJsonData(){
        Intent intent= getIntent();
        cardListResponse= intent.getStringExtra("cardList");
        try {
            JSONObject object = new JSONObject(cardListResponse);
            JSONObject obj = new JSONObject(object.optString("cards"));
            JSONArray arr = new JSONArray(obj.optString("cards"));
            for (int i = 0; i < arr.length(); i++) {
                cardList.add( arr.getJSONObject(i).optString("card_number") );
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Unable to parse JSON", Toast.LENGTH_SHORT).show();
            Log.d("CardManagement","Unable to parse JSON");
            e.printStackTrace();
        }
    }

    public void func_deleteCard(){
        ServiceHandler serviceHandler = new ServiceHandler(3);

        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute(new String[]{"9999999993", "9898"}));
        try {
            String response = asyncTask.get();
            Log.d("response_main", response);
//            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            JSONObject obj= new JSONObject(response);
            JSONObject deletedJson= new JSONObject(obj.optString("card"));
            if(deletedJson.optBoolean("deleted")){
                cardList.remove(currentPosition);
//                cardAdapter.notifyDataSetChanged();
            }
            func_CardManagement();
//            getJsonData();
//            setListData();
            Intent i= new Intent(CardManagement.this, CardManagement.class);
            i.putExtra("cardList", cardListResponse);
            startActivity(i);
            ringProgressDialog.dismiss();
            finish();

        } catch (Exception e) {
            Log.d("func_deleteCard","Exception Caught !");
//            Toast.makeText(getApplicationContext(), "Unable to make call.\n" + "Check your internet connection.",
//                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setListData(){
        ArrayAdapter<String>  cardAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cardList);
        cards.setAdapter(cardAdapter);
        cards.setOnItemClickListener(this);
    }

    private void createDeleteAlertBox()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Card");
        builder.setMessage("Are you sure you want to delete " + cardList.get(currentPosition) + " ?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                deleteCard();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setOnCancelListener(null);
        builder.show();
    }
    private void createAlertBox()
    {
        //Place proper name and details here
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Card");
//        builder.setMessage("Name : " + nameList.get(currentPosition) + "\nCard No. : "  + cardList.get(currentPosition)
//                + "\nExpiry : " + expiryMonthList.get(currentPosition) + "/" + expiryYearList.get(currentPosition));
        builder.setMessage("Name : " + "Rohan Sarkar" + "\nCard No. : "  + "98xxxxxxxxxx7665"
                + "\nExpiry : " + 6 + "/" + 34);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                createDeleteAlertBox();
            }
        });
        builder.setOnCancelListener(null);
        builder.show();
    }

    private void func_CardManagement(){
        ServiceHandler serviceHandler = new ServiceHandler(2);

        AsyncTask<String, Void, String> asyncTask = (serviceHandler.execute(new String[]{"9999999993"}));
        try {
            cardListResponse = asyncTask.get();
            Log.d("func_CardManagement_AddCard", cardListResponse);
        } catch (Exception e) {
            Log.d("func_CardManagement_AddCard","Exception Caught !");
//            Toast.makeText(CardManagement.this, "Unable to make call.\n" + "Check your internet connection.",
//                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void deleteCard() {
        ringProgressDialog = ProgressDialog.show(CardManagement.this, "Express Checkout", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                func_deleteCard();
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentPosition= position;
        createAlertBox();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bAddCard) {
            Intent i = new Intent(getApplicationContext(), AddCard.class);
            startActivity(i);
            finish();
        }
    }

    private void init(){
        cards= (ListView) findViewById(R.id.lvCards);
        addCard= (Button) findViewById(R.id.bAddCard);
        addCard.setOnClickListener(this);

        currentPosition=0;
        cardList= new ArrayList<String>();
        nameList= new ArrayList<String>();
        expiryMonthList= new ArrayList<String>();
        expiryYearList= new ArrayList<String>();
    }
}
