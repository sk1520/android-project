package com.example.sk.mtafare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemsAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] typeOfPurchase;
    String[] prices;
    String[] description;

    public ItemsAdapter(Context c, String[] types, String[] price, String[] desc) {
        typeOfPurchase = types;
        prices = price;
        description = desc;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return typeOfPurchase.length;
    }

    @Override
    public Object getItem(int position) {
        return typeOfPurchase[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_list,null);
        TextView typeofCardTextView = (TextView) v.findViewById(R.id.typeofCardTextView);
        TextView priceofCardTextView = (TextView) v.findViewById(R.id.priceofCardTextView);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);

        String type = typeOfPurchase[position];
        String desc = description[position];
        String cost = prices[position];

        typeofCardTextView.setText(type);
        priceofCardTextView.setText(cost);
        descriptionTextView.setText(desc);
        return v;
    }
}
