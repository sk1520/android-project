package com.example.sk.mtafare;

import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;

final class ValueUtilities {

    static final double defaultMtaPrice = 2.75;
    static final double defaultReducedMtaPrice = 1.35;
    static final double expressMtaPrice = 6.50;
    static final double expressReducedMtaPrice = 3.25;

    static final double mtaBonusPercentage = 0.05;
    static final double minimumBonusCost = 5.50;
    static final double newmtaCardCost = 1.00;

    private static double selectedCardCost;
    private static String selectedCardName;

    static String getSelectedCardName() {
        return selectedCardName;
    }

    static void setSelectedCardName(Resources res, int pos) {
        String cardName[] = res.getStringArray(R.array.typeOfCard);
        selectedCardName = cardName[pos];
    }

    static double getSelectedCardCost() {
        return selectedCardCost;
    }

    static void setSelectedCardCost(double cost) {
        selectedCardCost = cost;
    }

    static void setCardInfo(Resources res, double cost, int pos) {
        setSelectedCardName(res, pos);
        setSelectedCardCost(cost);
    }

    static String setCashFormat(double cash, boolean showSign) {
        DecimalFormat c;
        if (showSign) c = new DecimalFormat("$###,##0.00");
        else          c = new DecimalFormat("###,##0.00");
        return c.format(cash);
    }

    static String setCashFormatNoComma(BigDecimal cash) {
        DecimalFormat c = new DecimalFormat("#####0.00");
        return c.format(cash);
    }

    static String setCashFormat(BigDecimal cash, boolean showSign) {
        DecimalFormat c;
        if (showSign) c = new DecimalFormat("$###,##0.00");
        else          c = new DecimalFormat("###,##0.00");
        return c.format(cash);
    }

    static String setCashFormat(String cash, boolean showSign) {
        DecimalFormat c;
        if (showSign) c = new DecimalFormat("$###,##0.00");
        else          c = new DecimalFormat("###,##0.00");
        return c.format(cash);
    }

    static double useMtaRounderToDouble(String cash) {
        BigDecimal mta_rounded_balance = new BigDecimal(cash).setScale(2, BigDecimal.ROUND_HALF_UP);
        return mta_rounded_balance.doubleValue();
    }

    static String useMtaRounderToString(String cash) {
        BigDecimal mta_rounded_balance = new BigDecimal(cash).setScale(2, BigDecimal.ROUND_HALF_UP);
        return mta_rounded_balance.toString();
    }

    static boolean isInvalidNumInput(EditText text) {
        return (TextUtils.isEmpty(text.getText().toString()) || text.getText().toString().equals("."));
    }
}
