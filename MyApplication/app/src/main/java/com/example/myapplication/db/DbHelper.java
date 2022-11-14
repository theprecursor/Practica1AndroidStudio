package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NOMBRE = "reciclaje.db";
    public static final String TABLE_USUARIOS = "t_usuarios";
    private static final String instruccion_usuario = "CREATE TABLE "+TABLE_USUARIOS+" (email VARCHAR(100), password VARCHAR(100))";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(instruccion_usuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("bbdd","upgrade bbdd");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(sqLiteDatabase);
    }

    @Override public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

//      Para Crear la base de datos
//      @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS +
//                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "nombre TEXT NOT NULL," +
//                "telefono TEXT NOT NULL," +
//                "correo_electronico TEXT)");
//    }
//
//
    public List<Usuario> listarUsuarios(){
        SQLiteDatabase db = getReadableDatabase();

        List<Usuario> usuario = new ArrayList<>();

        if(db != null){
            String[] campos = {"email", "password"};

            Cursor c = db.query(TABLE_USUARIOS,campos,null,null,null,null,null,null);

            Usuario u;
            if(c.moveToFirst()){
                do{
                    u = new Usuario(c.getString(0),c.getString(1));
                    usuario.add(u);
                }while (c.moveToNext());
            }
            c.close();
        }
        db.close();
        return usuario;
    }

    public boolean comprobarusuarios(Usuario u){
        boolean user = false;
        SQLiteDatabase db = getReadableDatabase();

        Usuario usuario = null;

        if(db != null){
            String[] campos = {"email", "password"};

            //Cursor c = db.query(TABLE_USUARIOS,campos,"email="+u.getEmail(),null,null,null,null,null);

            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE  email = '" + u.getEmail() + "' AND password = '" +u.getPassword()+"'", null);

            if(c.getCount() == 0){
                usuario = null;
            }else{
                c.moveToFirst();
                user = true;
            }
            c.close();
        }
        db.close();
        return user;
    }

    public Usuario listarUsuariosBy(Usuario u){
        SQLiteDatabase db = getReadableDatabase();

        Usuario usuario = null;

        if(db != null){
            String[] campos = {"email", "password"};

            //Cursor c = db.query(TABLE_USUARIOS,campos,"email="+u.getEmail(),null,null,null,null,null);

            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE  email = '" + u.getEmail() + "' AND password = '" +u.getPassword()+"'", null);

            if(c.getCount() == 0){
                usuario = null;
            }else{
                c.moveToFirst();
                usuario = new Usuario(c.getString(0),c.getString(1));
            }
            c.close();
        }
        db.close();
        return usuario;
    }

    public long insertarUsuarios(Usuario u){
        long nreg_Afectados = -1;
        SQLiteDatabase db = getWritableDatabase();

        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("email",u.getEmail());
            valores.put("password", u.getPassword());
            nreg_Afectados = db.insert(TABLE_USUARIOS,null,valores);
        }
        db.close();
        return nreg_Afectados;
    }

    public long updateUsuarios(Usuario u){
        long nreg_Afectados = -1;
        SQLiteDatabase db = getWritableDatabase();

        if(db != null){
            String strSQL = "UPDATE "+TABLE_USUARIOS+" SET password = '"+u.getPassword()+"' WHERE email = '"+ u.getEmail()+"'";
            db.execSQL(strSQL);
        }
        db.close();
        return nreg_Afectados;
    }

    public long deleteUsuarios(Usuario u){
        long nreg_Afectados = -1;
        SQLiteDatabase db = getWritableDatabase();

        if(db != null){

            nreg_Afectados = db.delete(TABLE_USUARIOS,"email = '"+u.getEmail()+"'",null);
        }
        db.close();
        return nreg_Afectados;
    }

}
