package com.example.sk.mtafare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MtaLogs extends BaseNavigationDrawerSetupActivity {
    //Database db;
    Button buttonDeleteLogs; //CREATE BUTTON TO DELETE HISTORY
    ListView priceInfoListView; //SHOW LOGS IN LISTVIEW
    TextView emptyListTextView; //WHEN LISTVIEW IS EMPTY DISPLAY TEXT
    ArrayList<LogItem> log = new ArrayList<LogItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("History"); //SET TITLE OF ACTIVITY
        setContentView(R.layout.activity_mta_logs); //SETUP ACTIVITY
        emptyListTextView = findViewById(R.id.emptyListTextView); //SETUP EMPTY TEXT
        priceInfoListView = findViewById(R.id.priceInfoListView); //SETUP LIST OF HISTORY
        priceInfoListView.setEmptyView(emptyListTextView);
        buttonDeleteLogs = findViewById(R.id.deleteMtaLogs); //DELETE BUTTON FOR HISTORY
        final Database db = Database.getInstance(this.getApplicationContext());
        db.insertLogIntoList(log);
        buttonDeleteLogs.setOnClickListener(new View.OnClickListener() { //BUTTON TO DELETE
            @Override
            public void onClick(View view) {
                db.deleteMtaLog(); //DELETE THE LOG HISTORY
                recreate();//REFRESH ACTIVITY WHEN DELETE BUTTON IS CLICKED
            }
        });

        LogsAdapter logsAdapter = new LogsAdapter(this, R.layout.logs_list, log);
        priceInfoListView.setAdapter(logsAdapter);


        configureNavigationDrawer(this.getApplicationContext());
    }

}
