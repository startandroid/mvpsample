package ru.startandroid.mvpsample.mvp;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.startandroid.mvpsample.common.User;
import ru.startandroid.mvpsample.common.UserTable;
import ru.startandroid.mvpsample.database.DbHelper;

class UsersModel {

    private final DbHelper dbHelper;

    UsersModel(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    void loadUsers(LoadUserCallback callback) {
        LoadUsersTask loadUsersTask = new LoadUsersTask(callback, dbHelper);
        loadUsersTask.execute();
    }

    void addUser(ContentValues contentValues, CompleteCallback callback) {
        AddUserTask addUserTask = new AddUserTask(callback, dbHelper);
        addUserTask.execute(contentValues);
    }

    void clearUsers(CompleteCallback completeCallback) {
        ClearUsersTask clearUsersTask = new ClearUsersTask(completeCallback, dbHelper);
        clearUsersTask.execute();
    }


    interface LoadUserCallback {
        void onLoad(List<User> users);
    }

    interface CompleteCallback {
        void onComplete();
    }

    static class LoadUsersTask extends AsyncTask<Void, Void, List<User>> {

        private final LoadUserCallback callback;
        private WeakReference<DbHelper> dbHelperReference;

        LoadUsersTask(LoadUserCallback callback, DbHelper dbHelper) {
            this.callback = callback;
            dbHelperReference = new WeakReference<>(dbHelper);
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            List<User> users = new LinkedList<>();
            Cursor cursor = dbHelperReference.get().getReadableDatabase().query(UserTable.TABLE, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getLong(cursor.getColumnIndex(UserTable.COLUMN.ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.EMAIL)));
                users.add(user);
            }
            cursor.close();
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            if (callback != null) {
                callback.onLoad(users);
            }
        }
    }

    static class AddUserTask extends AsyncTask<ContentValues, Void, Void> {

        private final CompleteCallback callback;
        private WeakReference<DbHelper> dbHelperReference;

        AddUserTask(CompleteCallback callback, DbHelper dbHelper) {
            this.callback = callback;
            dbHelperReference = new WeakReference<>(dbHelper);
        }

        @Override
        protected Void doInBackground(ContentValues... params) {
            ContentValues cvUser = params[0];
            dbHelperReference.get().getWritableDatabase().insert(UserTable.TABLE, null, cvUser);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }

    static class ClearUsersTask extends AsyncTask<Void, Void, Void> {

        private final CompleteCallback callback;
        private WeakReference<DbHelper> dbHelperReference;

        ClearUsersTask(CompleteCallback callback, DbHelper dbHelper) {
            this.callback = callback;
            dbHelperReference = new WeakReference<>(dbHelper);
        }

        @Override
        protected Void doInBackground(Void... params) {
            dbHelperReference.get().getWritableDatabase().delete(UserTable.TABLE, null, null);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }
}