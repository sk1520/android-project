package com.example.sk.mtafare.mtacontact;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sk.mtafare.R;

public class RegularMailContact extends Fragment {
    private TextView contact_mail;

    public RegularMailContact() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_contact_regular_mail_layout, container, false);

        contact_mail = (TextView) returnView.findViewById(R.id.contact_mail);
        setRagularMailData();

        return returnView;
    }

    private void setRagularMailData() {
        for (int i = 0; i < getResources().getStringArray(R.array.contacts_mail).length; i++) {
            contact_mail.append(getResources().getTextArray(R.array.contacts_mail)[i]);
            contact_mail.append("\n\n");
        }
    }

}
