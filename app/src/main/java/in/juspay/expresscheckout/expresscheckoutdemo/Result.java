package in.juspay.expresscheckout.expresscheckoutdemo;

/**
 * Created by rohan on 1/6/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by rohan on 1/6/15.
 */
public class Result extends ActionBarActivity {
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        result= (TextView)findViewById(R.id.tvResult);
        setActionBar();
        getResult();
    }

    private void getResult(){
        Intent i= getIntent();
        result.setText(i.getStringExtra("transactionResult"));
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