package com.dega.chama.core;

import android.app.Application;

import com.dega.chama.presenter.io.ChamaNetService;

/**
 * Created by davedega on 28/12/16.
 */

public class Chama extends Application {

    ChamaNetService chamaNetService;

    @Override
    public void onCreate() {
        super.onCreate();
        chamaNetService = new ChamaNetService();
    }

    public ChamaNetService getChamaNetService() {
        return chamaNetService;
    }

}
