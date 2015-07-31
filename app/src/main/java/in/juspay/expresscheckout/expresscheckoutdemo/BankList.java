package in.juspay.expresscheckout.expresscheckoutdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 1/6/15.
 */
public class BankList {
    static String[] bankList = {"Axis Bank",
            "Bank of India",
            "Bank of Maharashtra",
            "Central Bank Of India",
            "Corporation Bank",
            "Development Credit Bank",
            "Federal Bank",
            "HDFC Bank",
            "ICICI Netbanking",
            "Industrial Development Bank of India",
            "Indian Bank",
            "IndusInd Bank",
            "Indian Overseas Bank",
            "Jammu and Kashmir Bank",
            "Karnataka Bank",
            "Karur Vysya",
            "State Bank of Bikaner and Jaipur",
            "State Bank of Hyderabad",
            "State Bank of India",
            "State Bank of Mysore",
            "State Bank of Travancore",
            "South Indian Bank",
            "Union Bank of India",
            "United Bank Of India",
            "Vijaya Bank",
            "Yes Bank",
            "CityUnion",
            "Canara Bank",
            "State Bank of Patiala",
            "Citi Bank NetBanking",
            "Deutsche Bank",
            "Kotak Bank",
            "Dhanalaxmi Bank",
            "ING Vysya Bank"};

    public static List<String> getBankNames(){
        List<String> list= new ArrayList<String>();
        for(int i=0; i<bankList.length; i++)
            list.add(bankList[i]);
        return list;
    }
}

