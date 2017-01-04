package com.dega.chama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dega.chama.core.Constants;
import com.dega.chama.model.Repo;
import com.dega.chama.model.User;
import com.dega.chama.view.adapter.ReposAdapter;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AuxActivity extends AppCompatActivity {

    private TextView name;
    private RecyclerView reposList;
    private ReposAdapter adapter;
    private ProgressBar progressBar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextView) findViewById(R.id.name_textview);

        reposList = (RecyclerView) findViewById(R.id.recycler_view);
        reposList.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        adapter = new ReposAdapter(AuxActivity.this, fetchRepos());
        reposList.setAdapter(adapter);


        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();

        IGithubService githubService = retrofit.create(IGithubService.class);
        Observable<User> github = githubService.getUserInfo();
        github.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .map(user -> {
//
//                })
                .subscribe(userInfo -> {
                    Log.i("Observable", "onSubscribe()" + userInfo);
                    Log.i("onSubscribe()->", "userinfo: " + userInfo);
                });


        Observable<User> fetchUser = Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    User data = fetchData();
                    subscriber.onNext(data); // Emit the contents of the URL
                    subscriber.onCompleted(); // Nothing more to emit
                } catch (Exception e) {
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });


        fetchUser
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
//                        pongo el nombre en el textView
                        name.setText(user.getName());
                    }
                });
    }

    public User fetchData() {
        User user = new User();
        user.setFollowers(3);
        user.setName("pepe");
        user.setFollowing(5);
        return user;
    }

    public ArrayList<Repo> fetchRepos() {

        ArrayList<Repo> repos = new ArrayList<Repo>();
        repos.add(new Repo(1234, "cosas", "juan/cosas", "este es un repo para describir la " +
                "fruncionalidadde las aplicacione sde valparaiso", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(134323, "valparaiso-api", "juan/valparaiso-api", "Primer verison de la APi" +
                " que provee de los metosos necesariosn para interactuar con" +
                "el gestor de ....ntiene los webservices ", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(6543, "valparaiso-frontend", "juan/valparaiso-frontend", "contiene" +
                "el nuevo front end creado con React native, Express", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(234098, "chama-app", "juan/chama-app", "App de ejemplo para la gente" +
                "de Chama donde se usa Rx y retrofit", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(87654, "elements-app", "juan/elements-app", "Ejercicio en Android usando" +
                "RxJava, Retrofit para la empresa Elements", "https://avatars.githubusercontent.com/u/838391?v=3"));

        return repos;
    }
}
