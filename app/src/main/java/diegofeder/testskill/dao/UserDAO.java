package diegofeder.testskill.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import diegofeder.androidskilltest2a.utils.GuidGenerator;
import diegofeder.testskill.domain.User;
import diegofeder.testskill.helper.DatabaseHelper;

public class UserDAO {

    private final String TABLE = "Users";

    private SQLiteDatabase db;

    private final DatabaseHelper databaseHelper;

    public UserDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insert(User user) {
        db = databaseHelper.getWritableDatabase();
        ContentValues data = getContentValues(user);
        db.insert(TABLE, null, data);
    }

    public void delete(User user) {
        db = databaseHelper.getWritableDatabase();
        String[] params = {String.valueOf(user.getId())};
        db.delete(TABLE, "id = ?", params);
    }

    public void update(User user) {
        User userDb = findByEmail(user.getEmail());
        ContentValues data = new ContentValues();
        if (!user.getName().equals(userDb.getName())) {
            data.put("name", user.getName());
        }
        if (!user.getPassword().equals(userDb.getPassword())) {
            data.put("password", user.getPassword());
        }
        if (data.size() > 0) {
            db = databaseHelper.getWritableDatabase();
            String[] params = {userDb.getId()};
            db.update(TABLE, data, "id=?", params);
        }
    }

    public User findByEmailAndPassword(String email, String password) {
        db = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE + " WHERE email=? AND password=? LIMIT 1";
        Cursor c = db.rawQuery(sql, new String[]{email, password});
        c.moveToFirst();
        User u = populateUser(c);
        c.close();
        return u;
    }

    public User findByEmail(String email) {
        db = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE + " WHERE email=? LIMIT 1";
        Cursor c = db.rawQuery(sql, new String[]{email});
        c.moveToFirst();
        User u = populateUser(c);
        c.close();
        return u;
    }

    public User findById(String id) {
        db = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE + " WHERE id=? LIMIT 1";
        Cursor c = db.rawQuery(sql, new String[]{id});
        c.moveToFirst();
        User u = populateUser(c);
        c.close();
        return u;
    }

    private User populateUser(Cursor c) {
        User user = null;
        if (c != null && c.getCount() > 0) {
            user = new User();
            user.setId(c.getString(c.getColumnIndex("id")));
            user.setEmail(c.getString(c.getColumnIndex("email")));
            user.setName(c.getString(c.getColumnIndex("name")));
            user.setPassword(c.getString(c.getColumnIndex("password")));
        }
        return user;
    }

    public List<User> populateUserList() {
        String sql = "SELECT id, email, name, password FROM Users;";
        db = databaseHelper.getWritableDatabase();

        Cursor c = db.rawQuery(sql, null);
        List<User> listUser = new ArrayList<>();

        while (c.moveToNext()) {
            User user = new User();

            user.setId(c.getString(c.getColumnIndex("id")));
            user.setEmail(c.getString(c.getColumnIndex("email")));
            user.setName(c.getString(c.getColumnIndex("name")));
            user.setPassword(c.getString(c.getColumnIndex("password")));

            listUser.add(user);
        }
        return listUser;
    }

    private ContentValues getContentValues(User wifi) {
        ContentValues data = new ContentValues();
        data.put("id", GuidGenerator.generate());
        data.put("email", wifi.getEmail());
        data.put("name", wifi.getName());
        data.put("password", wifi.getPassword());
        return data;
    }


}
