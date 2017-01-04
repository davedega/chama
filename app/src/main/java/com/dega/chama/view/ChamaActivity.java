package com.dega.chama.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

    private LinearLayout reposLayout;
    private LinearLayout buttonsLayout;

    private Button clearBtn;
    private Button parallelBtn;
    private Button sequentlyBtn;
    private RecyclerView reposList;
    private ProgressBar progressBar;
    private ReposAdapter adapter;

    private ChamaPresenterInteractor presenter;


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
        clearBtn = (Button) findViewById(R.id.clear_btn);
        parallelBtn = (Button) findViewById(R.id.parallel_button);
        sequentlyBtn = (Button) findViewById(R.id.sequently_button);
        reposLayout = (LinearLayout) findViewById(R.id.repos_layout);
        buttonsLayout = (LinearLayout) findViewById(R.id.buttons_layout);

        reposList = (RecyclerView) findViewById(R.id.recycler_view);
        reposList.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        clearBtn.setOnClickListener(
                view -> {
                    resetScreen();
                }
        );
        parallelBtn.setOnClickListener(view -> {
            presenter.fetchAtSameTime();
        });

        sequentlyBtn.setOnClickListener(view -> {
                        presenter.fetchUser();
                        presenter.fetchRepos();
        });
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

    @Override
    public void resetScreen() {
        adapter.clear();
        nameTextView.setText("");
        loginTextView.setText("");
        publicRepoCountTextView.setText("");
    }
}
