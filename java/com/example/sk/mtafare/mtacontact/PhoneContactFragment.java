package com.example.sk.mtafare.mtacontact;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sk.mtafare.R;

public class PhoneContactFragment extends Fragment {
    private TextView contact_phoneTextView;

    public PhoneContactFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_contact_phone_layout, container, false);

       contact_phoneTextView = (TextView) returnView.findViewById(R.id.contact_phoneTextView);
        setPhoneContactData();

        return returnView;
    }

    private void setPhoneContactData() {


        for (int i = 0; i < getResources().getStringArray(R.array.contacts_phone_numbers).length; i++) {
            contact_phoneTextView.append(getResources().getTextArray(R.array.contacts_phone_numbers)[i]);
            contact_phoneTextView.append("\n\n");
        }
    }
}
