package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DbUsuarios extends DbHelper {

    Context context;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Boolean comprobarUsuario(String email, String pass){
        SQLiteDatabase db = getReadableDatabase();
        Boolean exist = false;
        List<Usuario> usuario = new ArrayList<>();

        if(db != null){
            try{
                Cursor c = db.rawQuery("SELECT email, password FROM " + TABLE_USUARIOS + " where  email = " + email + " and password = " + pass, null);
                if (c.moveToFirst()){
                    exist = true;
                }

//
            }catch(SQLException e){
                e.toString();
            }


        }
        db.close();
        return exist;
    }
//    public long sabernum(){
//        SQLiteDatabase db = getReadableDatabase();
//        long exist = 0;
//        List<Usuario> usuario = new ArrayList<>();
//
//        if(db != null){
//            try{
//                exist =  DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM table_name", null);
//            }catch(SQLException e){
//                e.toString();
//            }
//        }
//        db.close();
//        return exist;
//    }
    public long insertarUsuarios(String email, String password){
        long id = 0;
        try{
            DbHelper dbh = new DbHelper(context);
            SQLiteDatabase db = dbh.getWritableDatabase();
            ContentValues values = new ContentValues();
//            long num = 1 + sabernum();
//            values.put("id", num);
            values.put("email", email);
            values.put("password", password);
            id = db.insert(TABLE_USUARIOS, null, values);
        }catch(Exception ex){
            ex.toString();
        }
        return id;
    }
}
