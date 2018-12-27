package com.example.sk.mtafare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 FIXME:
    FIXME - [X] *** Code where the value from text is grabbed and checked if empty can fail (user can put a comma or decimal with no # ... causing crash)
                    ^can use regex to filter out value / use comparison to check if decimal present

 TODO:
    TODO - [/] error checking
    TODO - [ ] check if adding mta card purchasing messed up calculations
    TODO - [ ] figure out to what decimal place mta card stores balance
    TODO - [/] <?> Data storage
    TODO - [] find correct maximum limit for cost result
    TODO - [] finish status info xml and java file

    TODO - [] implement crash reporter (just use existing library like ARCA)
    TODO - [/] implement feedback system/activity
    TODO - [] create different value folders with dimensions and test them (values / values-small / values-normal / values-large / values-xlarge)
    TODO - [/] finish fragments for MTA status
    TODO - [] finish navigation menu related activities
    TODO - [] create styles.xml sizes for multiple layout screen values
    TODO - [] change icon for train and add new images for TabLayout-service bar
    TODO - [] text for subway fragment is mis-aligned with pictures

 NOTE:
    NOTE - Customize 'Result' output. Currently just displays price ... can also display amount of money saved e.g -> PRICE (BONUS AMOUNT) -> [ $18.33 (BONUS $0.92) ]
**/

public class MainActivity extends BaseNavigationDrawerSetupActivity {

    Button buttonCalculate;
    Button buttonShowLogs;
    Button buttonDeleteLogs;
    Button buttonShowTable;
    Button buttonStatusInfo;
    CheckBox checkboxNewCard;
    EditText edittextBalance;
    EditText edittextRides;
    TextView textviewResult;
    Spinner spinnerCardType;

    // SK:
    TextView mtaFareTextView;
    // END_SK

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SK:
        mtaFareTextView = findViewById(R.id.mtaFareTextView);
        // END_SK

        edittextBalance = findViewById(R.id.currentBalanceNumber);
        edittextRides = findViewById(R.id.ridesDesiredNumber);
        textviewResult = findViewById(R.id.resultTextView);
        buttonCalculate = findViewById(R.id.calculateButton);
        buttonShowLogs = findViewById(R.id.showMtaLogs);
        buttonDeleteLogs = findViewById(R.id.deleteMtaLogs);
        buttonShowTable = findViewById(R.id.tableButton);
        buttonStatusInfo = findViewById(R.id.statusInfoButton);
        checkboxNewCard = findViewById(R.id.newcardCheckBox);
        spinnerCardType = findViewById(R.id.cardTypeSpinner);

        db = Database.getInstance(this.getApplicationContext());

        spinnerCardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if      (pos == 0) ValueUtilities.setCardInfo(getResources(), ValueUtilities.defaultMtaPrice, 0);
                else if (pos == 1) ValueUtilities.setCardInfo(getResources(), ValueUtilities.defaultReducedMtaPrice, 1);
                else if (pos == 2) ValueUtilities.setCardInfo(getResources(), ValueUtilities.expressMtaPrice, 2);
                else if (pos == 3) ValueUtilities.setCardInfo(getResources(), ValueUtilities.expressReducedMtaPrice, 3);

                textviewResult.setText(R.string.result);
                mtaFareTextView.setText(getString(R.string.fare_per_ride, ValueUtilities.setCashFormat(ValueUtilities.getSelectedCardCost(), false)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ValueUtilities.setCardInfo(getResources(), ValueUtilities.defaultMtaPrice, 0);
                mtaFareTextView.setText(getString(R.string.fare_per_ride, ValueUtilities.setCashFormat(ValueUtilities.getSelectedCardCost(), false)));
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValueUtilities.isInvalidNumInput(edittextBalance)) edittextBalance.setText("0");
                if (ValueUtilities.isInvalidNumInput(edittextRides))   edittextRides.setText("0");
                doCalculation();
            }
        });

        // NOTE : TESTING
//        buttonShowLogs.setOnClickListener(new View.OnClickListener() { // when morePrices textview is clicked go to new activity
//            @Override
//            public void onClick(View v) {
//                Intent seeMtaLogs = new Intent(MainActivity.this, MtaLogs.class);
//                startActivity(seeMtaLogs);
//                db.displayMtaLog(); // TODO - remove
//            }
//        });
//
//        buttonDeleteLogs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                db.deleteMtaLog();
//            }
//        });
//
//        buttonShowTable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent seeMtaTable = new Intent(MainActivity.this, MtaTable.class);
//                if (ValueUtilities.isInvalidNumInput(edittextBalance))
//                    seeMtaTable.putExtra("user_balance", 0.00);
//                else
//                    seeMtaTable.putExtra("user_balance", ValueUtilities.useMtaRounderToDouble(edittextBalance.getText().toString()));
//                seeMtaTable.putExtra("user_isNewCard", checkboxNewCard.isChecked());
//
//                startActivity(seeMtaTable);
//            }
//        });
//
//        buttonStatusInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent statusInfo = new Intent (getApplicationContext(), StatusInfo.class);
//                startActivity(statusInfo);
//            }
//        });
//
       configureNavigationDrawer(this.getApplicationContext());

        // NOTE : END TESTING
    }

    private void doCalculation() {
        BigDecimal balance = new BigDecimal(String.valueOf(edittextBalance.getText())).setScale(2, BigDecimal.ROUND_HALF_UP);
        int rides = Integer.valueOf(String.valueOf(edittextRides.getText()));
        boolean createLog = true;

        BigDecimal price = new BigDecimal(ValueUtilities.getSelectedCardCost());
        price = price.multiply(BigDecimal.valueOf(rides));

        // TODO - add check here to see if value entered is too high and unnecessary to run calculation (set message / warning)

        ///if (checkboxNewCard.isChecked()) {
        ///    price = price.add(BigDecimal.valueOf(ValueUtilities.newmtaCardCost));
        ///}

        BigDecimal cashNeeded = price.subtract(balance);
        cashNeeded = cashNeeded.setScale(2, BigDecimal.ROUND_HALF_UP);

        if (cashNeeded.compareTo(BigDecimal.valueOf(0)) != 1) {
            textviewResult.setText(R.string.enoughCash);
            createLog = false;
        }
        else if (cashNeeded.compareTo(BigDecimal.valueOf(ValueUtilities.minimumBonusCost)) != -1) {
            BigDecimal purchaseAmount = cashNeeded.subtract(cashNeeded.multiply(BigDecimal.valueOf(ValueUtilities.mtaBonusPercentage)));

            // NOTE: changed from 0.01 -> 0.001 (need 3rd decimal value to be checked for very rare circumstances e.g -> Express/0 Balance/5 Rides/Not New Card
            for (BigDecimal i = purchaseAmount; i.compareTo(cashNeeded) == -1; i = i.add(BigDecimal.valueOf(0.001))) { // TODO - create better search -> increment faster till out of reach
                purchaseAmount = i;
                purchaseAmount = purchaseAmount.add(purchaseAmount.multiply(BigDecimal.valueOf(ValueUtilities.mtaBonusPercentage)));
                purchaseAmount = purchaseAmount.setScale(3, BigDecimal.ROUND_HALF_UP);

                if (purchaseAmount.compareTo(cashNeeded) != -1) {
                    // Do not apply bonus calculation if it becomes less than the amount needed for the bonus to trigger
                    if (i.compareTo(BigDecimal.valueOf(ValueUtilities.minimumBonusCost)) != 1) {
                        break;
                    }
                    Log.d("Cal-Bonus", "PASSED @ " + purchaseAmount);
                    cashNeeded = i.setScale(3, BigDecimal.ROUND_HALF_UP);
                    break;
                }
                else {
                    Log.d("Cal-Bonus", "FAILED @ " + purchaseAmount);
                }
            }

            if (checkboxNewCard.isChecked()) {
                if (cashNeeded.compareTo(BigDecimal.valueOf(5.55)) != -1) {
                    cashNeeded = cashNeeded.add(BigDecimal.valueOf(0.95)); // NOTE: have to use .95 because you need to take the 5% discount into effect (dirty way)
                }
                else {
                    cashNeeded = cashNeeded.add(BigDecimal.valueOf(ValueUtilities.newmtaCardCost));
                }
            }
            textviewResult.setText(ValueUtilities.setCashFormat(cashNeeded, true));
            Log.d("Cal-Form", "Minimum Required = " + cashNeeded);
        }
        else {
            if (checkboxNewCard.isChecked()) {
                cashNeeded = cashNeeded.add(BigDecimal.valueOf(ValueUtilities.newmtaCardCost));
            }
            textviewResult.setText(ValueUtilities.setCashFormat(cashNeeded, true));
            Log.d("Cal-Form", "Rides * RidePrice = Price - AvailableBalance = NeededCash");
            Log.d("Cal-Form", rides + " * " + ValueUtilities.getSelectedCardCost() + " = " + price + " - " + balance + " = " + cashNeeded);
        }

        if (createLog) {
            createMtaLog(cashNeeded);
        }
    }

    private void createMtaLog(BigDecimal cashNeeded) {
        double balance = Double.parseDouble(edittextBalance.getText().toString());
        double cost = cashNeeded.doubleValue();

        String log = String.format("[%1$-25s" +
                        "[%2$-25s" +
                        "[%3$-25s" +
                        "[%4$-25s" +
                        "[%5$-25s" +
                        "[%6$-25s" , ValueUtilities.getSelectedCardName() + "]",
                "Balance = $" + ValueUtilities.setCashFormat(balance, false) + "]",
                "Rides = " + edittextRides.getText().toString() + "]",
                "New Card = " + (checkboxNewCard.isChecked() ? "Yes" : "No") + "]",
                "Cost = $" + ValueUtilities.setCashFormat(cashNeeded, false) + "]",
                new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.US).format(new Date()) + "]"
        );
        Log.d("M-LOG", log);
        db.insertMtaLog(new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.US).format(new Date()),
                ValueUtilities.getSelectedCardName(),
                balance,
                Integer.parseInt(edittextRides.getText().toString()),
                checkboxNewCard.isChecked() ? "Yes" : "No",
                cost);
    }

}
