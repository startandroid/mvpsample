package ru.startandroid.mvpsample.mvp;

import android.content.ContentValues;
import android.text.TextUtils;

import ru.startandroid.mvpsample.R;
import ru.startandroid.mvpsample.common.UserTable;

class UsersPresenter {

    private UsersActivity view;
    private final UsersModel model;

    UsersPresenter(UsersModel model) {
        this.model = model;
    }

    void attachView(UsersActivity usersActivity) {
        view = usersActivity;
    }

    void detachView() {
        view = null;
    }

    void viewIsReady() {
        loadUsers();
    }

    private void loadUsers() {
        model.loadUsers(users -> view.showUsers(users));
    }

    void add() {
        UserData userData = view.getUserData();
        if (TextUtils.isEmpty(userData.getName()) || TextUtils.isEmpty(userData.getEmail())) {
            view.showToast(R.string.empty_values);
            return;
        }

        ContentValues cv = new ContentValues(2);
        cv.put(UserTable.COLUMN.NAME, userData.getName());
        cv.put(UserTable.COLUMN.EMAIL, userData.getEmail());
        view.showProgress();
        model.addUser(cv, () -> {
            view.hideProgress();
            loadUsers();
        });
    }

    void clear() {
        view.showProgress();
        model.clearUsers(() -> {
            view.hideProgress();
            loadUsers();
        });
    }
}