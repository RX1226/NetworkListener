package com.github.rx1226.network.listener;

public @interface NetworkState {
    String AVAILABLE = "AVAILABLE";
    String LOST = "LOST";
    String CHANGE_TO_WIFI = "CHANGE_TO_WIFI";
    String CHANGE_TO_MOBILE = "CHANGE_TO_MOBILE";
}
