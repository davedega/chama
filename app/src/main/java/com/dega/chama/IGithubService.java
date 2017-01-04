package com.dega.chama;

import com.dega.chama.core.Constants;
import com.dega.chama.model.Repo;
import com.dega.chama.model.User;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by davedega on 03/01/17.
 */

public interface IGithubService {
    @GET("users/davedega")
    Observable<User> getUserInfo();

    @GET("users/davedega/repos")
    Observable<ArrayList<Repo>> getUserRepos();
}
