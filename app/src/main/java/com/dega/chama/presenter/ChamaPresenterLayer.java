package com.dega.chama.presenter;

import android.util.Log;

import com.dega.chama.IGithubService;
import com.dega.chama.core.Constants;
import com.dega.chama.model.Repo;
import com.dega.chama.model.User;
import com.dega.chama.model.UserAndRepos;
import com.dega.chama.view.interfaces.ChamaInterface;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by davedega on 03/01/17.
 */

public class ChamaPresenterLayer implements ChamaPresenterInteractor {

    private ChamaInterface view;

    private Retrofit retrofit;

    private IGithubService githubService;

    public ChamaPresenterLayer(ChamaInterface view) {
        this.view = view;
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    @Override
    public void fetchUser() {
        githubService = retrofit.create(IGithubService.class);
        Observable<User> githubObservable = githubService.getUserInfo();
        githubObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        Log.e("USER", "onCompleted :)");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("USER", e.getMessage());
                    }

                    @Override
                    public final void onNext(User user) {
                        view.onUserResponse(user);
                    }
                });
    }

    @Override
    public void fetchRepos() {
        githubService = retrofit.create(IGithubService.class);
        Observable<ArrayList<Repo>> reposObservable = githubService.getUserRepos();
        reposObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Repo> repos) {
                        view.onReposRespone(repos);

                    }
                });
    }

    @Override
    public void fetchAtSameTime() {

        githubService = retrofit.create(IGithubService.class);
        Observable<User> githubObservable = githubService.getUserInfo();
        Observable<ArrayList<Repo>> reposObservable = githubService.getUserRepos();
        Observable<UserAndRepos> combined = Observable.zip(githubObservable, reposObservable, (user, repos) -> new UserAndRepos(repos, user));

        combined
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserAndRepos>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserAndRepos userAndRepos) {
                        view.onUserResponse(userAndRepos.getUser());
                        view.onReposRespone(userAndRepos.getRepos());
                    }
                });

    }

    @Override
    public void fetchOneAfterAnother() {

    }
}
