package com.example.sk.mtafare;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;

/*  NOTE:
        NOTE: can use this to hide unused values and just skip immediately: if (Double.valueOf(cost) <= 0.00) continue;
**/

public class MtaTable extends BaseNavigationDrawerSetupActivity {

    TextView[] slot = new TextView[4];
    BigDecimal totalAmountNoDiscount;
    BigDecimal breakAmount = new BigDecimal(100); // TODO - find correct val and store this in util

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("History");
        setContentView(R.layout.activity_mta_table);

        double mta_balance = getIntent().getDoubleExtra("user_balance", 0.00);
        boolean mta_isNewCard = getIntent().getBooleanExtra("user_isNewCard", false);

        TableLayout prices = findViewById(R.id.tabelayout_prices);
        TextView textviewBalance = findViewById(R.id.table_balanceTextView);
        TextView textviewCardtype = findViewById(R.id.table_cardtypeTextView);
        TextView textviewIsNewCard = findViewById(R.id.table_isnewcardTextView);

        textviewBalance.setText(getResources().getString(R.string.table_balance, ValueUtilities.setCashFormat(mta_balance, false)));
        textviewCardtype.setText(getResources().getString(R.string.table_cardtype, ValueUtilities.getSelectedCardName()));
        textviewIsNewCard.setText(getResources().getString(R.string.table_isnewcard, mta_isNewCard ? "Yes" : "No"));

        prices.setStretchAllColumns(true);
        prices.bringToFront();

        TableRow trHeading = new TableRow(this);
        TextView[] heading = new TextView[4];

        for (int i = 0; i < heading.length; i++) { // NOTE: can add this to XML as it is always same (after testing)
            heading[i] = new TextView(this);
            heading[i].setTextSize(getResources().getDimension(R.dimen.table_heading_textSize));
            heading[i].setGravity(Gravity.CENTER_HORIZONTAL);
            heading[i].setBackgroundColor(Color.YELLOW);
            heading[i].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        }
        heading[0].setText(getResources().getString(R.string.table_column_heading_1));
        heading[1].setText(getResources().getString(R.string.table_column_heading_2));
        heading[2].setText(getResources().getString(R.string.table_column_heading_3));
        heading[3].setText(getResources().getString(R.string.table_column_heading_4));

        for (TextView aHeading : heading) trHeading.addView(aHeading);
        prices.addView(trHeading);

        TableRow[] tr = new TableRow[100];

        for (int j = 0, r = 1; j < tr.length; j++, r++) {
            BigDecimal cost = doTableCalculation(new BigDecimal(mta_balance), r, mta_isNewCard);

            if (mta_isNewCard) {
                if (cost.compareTo(BigDecimal.valueOf(5.55)) != -1) { // less than
                    cost = cost.add(BigDecimal.valueOf(0.95)); // NOTE: have to use .95 because you need to take the 5% discount into effect (dirty way)
                }
                else {
                    cost = cost.add(BigDecimal.valueOf(ValueUtilities.newmtaCardCost));
                }
            }

            tr[j] = new TableRow(this);

            if (j % 2 == 1) {
                tr[j].setBackgroundColor(Color.rgb(240,240,240));
            }

            for (int i = 0; i < slot.length; i++) {
                slot[i] = new TextView(this);
                slot[i].setTextSize(getResources().getDimension(R.dimen.table_row_textSize));
                slot[i].setGravity(Gravity.CENTER_HORIZONTAL);
            }

            slot[0].setText(String.format("$%s", ValueUtilities.setCashFormat(cost, false)));
            slot[1].setText(String.format("%d", r));
            if (cost.doubleValue() == 0)
                slot[2].setText(String.format("%s", "$0.00"));
            else
                slot[2].setText(String.format("%s", ValueUtilities.setCashFormat(totalAmountNoDiscount.subtract(cost).subtract(new BigDecimal(mta_balance)).abs(), true)));
            slot[3].setText(String.format("%s", ValueUtilities.setCashFormat(totalAmountNoDiscount, true)));

            for (TextView aSlot : slot) tr[j].addView(aSlot);

            prices.addView(tr[j]);

            if (totalAmountNoDiscount.compareTo(breakAmount) == 1) {
                break;
            }
        }

        configureNavigationDrawer(this.getApplicationContext());
    }

    private BigDecimal doTableCalculation(BigDecimal balance, int rides, boolean isNewCard) {
        BigDecimal price = new BigDecimal(ValueUtilities.getSelectedCardCost());
        price = price.multiply(BigDecimal.valueOf(rides));

        totalAmountNoDiscount = price.setScale(2, BigDecimal.ROUND_HALF_UP);

        if (isNewCard) {
            totalAmountNoDiscount = totalAmountNoDiscount.add(BigDecimal.valueOf(ValueUtilities.newmtaCardCost));
        }

        BigDecimal cashNeeded = price.subtract(balance);
        cashNeeded = cashNeeded.setScale(2, BigDecimal.ROUND_HALF_UP);

        if (cashNeeded.compareTo(BigDecimal.valueOf(0)) != 1) {
            return new BigDecimal(0.00);
        }
        else if (cashNeeded.compareTo(BigDecimal.valueOf(ValueUtilities.minimumBonusCost)) != -1) {
            BigDecimal purchaseAmount = cashNeeded.subtract(cashNeeded.multiply(BigDecimal.valueOf(ValueUtilities.mtaBonusPercentage)));

            for (BigDecimal i = purchaseAmount; i.compareTo(cashNeeded) == -1; i = i.add(BigDecimal.valueOf(0.001))) {
                purchaseAmount = i;
                purchaseAmount = purchaseAmount.add(purchaseAmount.multiply(BigDecimal.valueOf(ValueUtilities.mtaBonusPercentage)));
                purchaseAmount = purchaseAmount.setScale(3, BigDecimal.ROUND_HALF_UP);

                if (purchaseAmount.compareTo(cashNeeded) != -1) {
                    // Do not apply bonus calculation if it becomes less than the amount needed for the bonus to trigger
                    if (i.compareTo(BigDecimal.valueOf(ValueUtilities.minimumBonusCost)) != 1) {
                        break;
                    }
                    cashNeeded = i.setScale(3, BigDecimal.ROUND_HALF_UP);
                    break;
                }
            }
        }

        return cashNeeded;
    }
}
