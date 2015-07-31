package in.juspay.expresscheckout.expresscheckoutdemo;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.Toast;


public class Pay extends FragmentActivity {

    static final int NUMBER_OF_LISTS = 7;

    MyAdapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        try {
            mPager.setAdapter(mAdapter);
        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(), "Null Pointer", Toast.LENGTH_SHORT).show();
        }
    }
}

