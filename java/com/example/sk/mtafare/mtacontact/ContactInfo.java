package com.example.sk.mtafare.mtacontact;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;

import com.example.sk.mtafare.BaseNavigationDrawerSetupActivity;
import com.example.sk.mtafare.R;

public class ContactInfo extends BaseNavigationDrawerSetupActivity {

    TabLayout tablayoutContact;
    TabItem tabitemPhoneFragment; /* Unused since changing from Button listener */
    TabItem tabitemEmailFragment;                   /*    to    */
    TabItem tabitemRegularMailFragment;               /* TabLayout listener */
    private static int tabCurrentPosition;

    public ContactInfo() {}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tabCurrentPosition = tablayoutContact.getSelectedTabPosition(); // Save active tab position before device rotation to be able to restore it
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Contact MTA");
        setContentView(R.layout.activity_contact_info);

        tablayoutContact = findViewById(R.id.contact_tablayout);
        tabitemPhoneFragment = findViewById(R.id.phone_contact);
        tabitemEmailFragment = findViewById(R.id.e_mail_contact);
        tabitemRegularMailFragment = findViewById(R.id.regular_mail_contact);

        tablayoutContact.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getTabPositionClicked(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) { // Will refresh the fragment by removing and re-adding if reselected
                getTabPositionClicked(tab.getPosition());
            }
        });

        if (savedInstanceState == null) { // Default initial fragment view to MapSubwayFragment if none created / selected
            getTabPositionClicked(0);
        }
        else { // Grab last active tab position and restore it
            TabLayout.Tab tab = tablayoutContact.getTabAt(tabCurrentPosition);
            if (tab != null) {
                tab.select();
            }
        }

        configureNavigationDrawer(this.getApplicationContext());
    }

    private void getTabPositionClicked(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                PhoneContactFragment phoneContactFragment = new PhoneContactFragment();
                addCreatedFragment(fragmentTransaction, phoneContactFragment);
                break;
            case 1:
                EmailContactFragment emailContactFragment = new EmailContactFragment();
                addCreatedFragment(fragmentTransaction, emailContactFragment);
                break;
            case 2:
                RegularMailContact regularMailContact = new RegularMailContact();
                addCreatedFragment(fragmentTransaction, regularMailContact);
                break;

        }
    }

    private void addCreatedFragment(FragmentTransaction fragmentTransaction, Fragment fragmentObject) {
        fragmentTransaction.add(R.id.contact_fragment_container, fragmentObject); // Make sure to import the correct fragment package 'android.app.Fragment'
        fragmentTransaction.commit();
    }
}
