package com.example.sk.mtafare;

public class LogItem {

    private String time;
    private String faretype;
    private String isNewCard;
    private double balance;
    private double cost;
    private int rides;

    public LogItem() {}

    public LogItem(String time, String faretype, double balance, int rides, String isNewCard, double cost) {
        this.time = time;
        this.faretype = faretype;
        this.balance = balance;
        this.rides = rides;
        this.isNewCard = isNewCard;
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public String getFaretype() {
        return faretype;
    }

    public double getBalance() {
        return balance;
    }

    public int getRides() {
        return rides;
    }

    public String getIsNewCard() {
        return isNewCard;
    }

    public double getCost() {
        return cost;
    }
}
