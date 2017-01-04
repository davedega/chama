package com.dega.chama.view.interfaces;

import com.dega.chama.model.Repo;
import com.dega.chama.model.User;

import java.util.ArrayList;

/**
 * Created by davedega on 03/01/17.
 */

public interface ChamaInterface {
    void initViews();

    void onUserResponse(User user);

    void onReposRespone(ArrayList<Repo> repos);

    void setResponseTime(String time);

    void onError(String error);
}
