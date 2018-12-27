package com.example.sk.mtafare;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LogsAdapter extends ArrayAdapter<LogItem> {

    private LayoutInflater mInflater;
    private ArrayList<LogItem> log;

    LogsAdapter(Context context, int textViewResourceId, ArrayList<LogItem> objects) {
        super(context, textViewResourceId, objects);
        this.log = objects;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.logs_list, null);
        Resources res = v.getResources();

        TextView log_orderTextView = v.findViewById(R.id.log_order);
        TextView log_timeTextView = v.findViewById(R.id.log_time);
        TextView log_faretypeTextView = v.findViewById(R.id.log_faretype);
        TextView log_balanceTextView = v.findViewById(R.id.log_balance);
        TextView log_ridesTextView = v.findViewById(R.id.log_rides);
        TextView log_isNewCardTextView = v.findViewById(R.id.log_isNewCard);
        TextView log_costTextView = v.findViewById(R.id.log_cost);

        LogItem i = log.get(position);

        log_orderTextView.setText(res.getString(R.string.log_order, Integer.toString(position + 1)));
        log_timeTextView.setText(res.getString(R.string.log_time, i.getTime()));
        log_faretypeTextView.setText(res.getString(R.string.log_faretype, i.getFaretype()));
        log_balanceTextView.setText(res.getString(R.string.log_balance, ValueUtilities.setCashFormat(i.getBalance(), false)));
        log_ridesTextView.setText(res.getString(R.string.log_rides, Integer.toString(i.getRides())));
        log_isNewCardTextView.setText(res.getString(R.string.log_isNewCard, i.getIsNewCard()));
        log_costTextView.setText(res.getString(R.string.log_cost, ValueUtilities.setCashFormat(i.getCost(), false)));

        return v;
    }
}