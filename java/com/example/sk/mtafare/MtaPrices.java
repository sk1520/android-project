package com.example.sk.mtafare;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;

public class MtaPrices extends BaseNavigationDrawerSetupActivity {

    ListView priceInfoListView;
    String[] typeOfPurchase;
    String[] prices;
    String[] description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MTA Prices");
        setContentView(R.layout.activity_mta_prices);
        Resources res = getResources();
        priceInfoListView = (ListView) findViewById(R.id.priceInfoListView);
        typeOfPurchase = res.getStringArray(R.array.typeOfPurchase);
        prices = res.getStringArray(R.array.prices);
        description = res.getStringArray(R.array.description);

        ItemsAdapter itemsAdapter = new ItemsAdapter(this, typeOfPurchase, prices, description);
        priceInfoListView.setAdapter(itemsAdapter);

        configureNavigationDrawer(this.getApplicationContext());
    }
}
