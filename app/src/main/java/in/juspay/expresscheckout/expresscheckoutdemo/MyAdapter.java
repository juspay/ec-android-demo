package in.juspay.expresscheckout.expresscheckoutdemo;

/**
 * Created by rohan on 1/6/15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by clive on 25-May-14.
 * www.101apps.co.za
 */
public class MyAdapter extends FragmentStatePagerAdapter {

    public MyAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    //    returns the number of views available
    @Override
    public int getCount() {
        return 2;
    }

    // when swiping returns a fragment with the object identified by position
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                CardFragment card= new CardFragment();
                return card;
            case 1:
                NetBankingFragment netBanking= new NetBankingFragment();
                return netBanking;
        }
        CardFragment c= new CardFragment();
        return c;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Credit/Debit Cards";
            case 1:
                return "Net Banking";
        }
        return "Default";
    }
}