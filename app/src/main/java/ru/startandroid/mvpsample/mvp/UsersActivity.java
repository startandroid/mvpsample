package ru.startandroid.mvpsample.mvp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ru.startandroid.mvpsample.R;
import ru.startandroid.mvpsample.common.User;
import ru.startandroid.mvpsample.common.UserAdapter;
import ru.startandroid.mvpsample.database.DbHelper;

public class UsersActivity extends AppCompatActivity {

    private UserAdapter userAdapter;

    private EditText editTextName;
    private EditText editTextEmail;
    private ProgressDialog progressDialog;

    private UsersPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        init();
    }

    private void init() {

        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.add();
            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clear();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        userAdapter = new UserAdapter();

        RecyclerView userList = (RecyclerView) findViewById(R.id.list);
        userList.setLayoutManager(layoutManager);
        userList.setAdapter(userAdapter);


        DbHelper dbHelper = new DbHelper(this);
        UsersModel usersModel = new UsersModel(dbHelper);
        presenter = new UsersPresenter(usersModel);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    public UserData getUserData() {
        UserData userData = new UserData();
        userData.setName(editTextName.getText().toString());
        userData.setEmail(editTextEmail.getText().toString());
        return userData;
    }

    public void showUsers(List<User> users) {
        userAdapter.setData(users);
    }

    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait));
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
