package com.dega.chama.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dega.chama.R;
import com.dega.chama.model.Repo;
import com.dega.chama.model.User;
import com.dega.chama.presenter.ChamaPresenterInteractor;
import com.dega.chama.presenter.ChamaPresenterLayer;
import com.dega.chama.view.adapter.ReposAdapter;
import com.dega.chama.view.interfaces.ChamaInterface;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class ChamaActivity extends AppCompatActivity implements ChamaInterface {

    private TextView loginTextView;
    private TextView nameTextView;
    private TextView publicRepoCountTextView;
    private TextView responseTimeTextView;

    private Button startBtn;
    private Button parallelBtn;
    private Button sequentlyBtn;
    private RecyclerView reposList;
    private ReposAdapter adapter;

    private ChamaPresenterInteractor presenter;


    private ProgressBar progressBar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ChamaPresenterLayer(this);
        initViews();
    }


    @Override
    public void initViews() {
        loginTextView = (TextView) findViewById(R.id.login_textview);
        nameTextView = (TextView) findViewById(R.id.name_textview);
        publicRepoCountTextView = (TextView) findViewById(R.id.public_repo_textview);
        responseTimeTextView = (TextView) findViewById(R.id.response_time_textview);
        startBtn = (Button) findViewById(R.id.start_btn);
        reposList = (RecyclerView) findViewById(R.id.recycler_view);
        reposList.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        startBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        presenter.fetchUser();
//                        presenter.fetchRepos();
                        presenter.fetchAtSameTime();
                    }
                }
        );
//        jrxBtn.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                        adapter = new ReposAdapter(ChamaActivity.this, ChamaData.fetchRepos());
//                        reposList.setAdapter(adapter);
//
//                        Observable<User> fetchUser = Observable.create(new Observable.OnSubscribe<User>() {
//                            @Override
//                            public void call(Subscriber<? super User> subscriber) {
//                                try {
//                                    User data = ChamaData.fetchData();
//                                    subscriber.onNext(data); // Emit the contents of the URL
//                                    subscriber.onCompleted(); // Nothing more to emit
//                                } catch (Exception e) {
//                                    subscriber.onError(e); // In case there are network errors
//                                }
//                            }
//                        });
//
//
//                        fetchUser
//                                .subscribeOn(Schedulers.newThread()) // Create a new Thread
//                                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
//                                .subscribe(new Action1<User>() {
//                                    @Override
//                                    public void call(User user) {
////                        pongo el nombre en el textView
////                                        name.setText(user.getName());
//                                    }
//                                });
//                    }
//                }
//        );
    }

    @Override
    public void onUserResponse(User user) {
        nameTextView.setText(user.getName());
        loginTextView.setText(user.getLogin());
        publicRepoCountTextView.setText("" + user.getPublicRepos());
    }

    @Override
    public void onReposRespone(ArrayList<Repo> repos) {
        Log.e("ChamaActivity", "onReposRespone()");
        Log.e("ChamaActivity", "repos.size(): " + repos.size());
        adapter = new ReposAdapter(ChamaActivity.this, repos);
        reposList.setAdapter(adapter);
    }

    @Override
    public void setResponseTime(String time) {
        responseTimeTextView.setText(time + " ms.");
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getApplicationContext(), "Ups!" + error.toString(), Toast.LENGTH_SHORT).show();
    }
}
