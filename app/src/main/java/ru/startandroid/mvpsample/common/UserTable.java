package ru.startandroid.mvpsample.common;

public class UserTable {

    public static final String TABLE = "users";

    public static class COLUMN {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
    }

    public static final String CREATE_SCRIPT =
            String.format("create table %s ("
                            + "%s integer primary key autoincrement,"
                            + "%s text,"
                            + "%s text" + ");",
                    TABLE, COLUMN.ID, COLUMN.NAME, COLUMN.EMAIL);
}
