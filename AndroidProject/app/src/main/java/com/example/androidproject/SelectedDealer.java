package com.example.androidproject;

public class SelectedDealer {
    private static SelectedDealer sharedInstance = null;

    public String dealer;

    private SelectedDealer() {
    }

    public static SelectedDealer getInstance() {
        if (sharedInstance == null) {
            synchronized (SelectedDealer.class) {
                if (sharedInstance == null) {
                    sharedInstance = new SelectedDealer();
                }
            }
        }
        return sharedInstance;
    }
}
