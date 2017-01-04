package com.dega.chama.model;

import java.util.ArrayList;

/**
 * Created by davedega on 04/01/17.
 */

public class UserAndRepos {
    private ArrayList<Repo> repos;
    private User user;

    public UserAndRepos(ArrayList<Repo> repos, User user) {
        this.repos = repos;
        this.user = user;
    }

    public ArrayList<Repo> getRepos() {
        return repos;
    }

    public User getUser() {
        return user;
    }
}
