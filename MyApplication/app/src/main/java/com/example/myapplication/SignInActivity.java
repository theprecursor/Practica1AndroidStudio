package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.modelo.Usuario;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnHome;
    Button btnRegister;
    TextView txtEmail;
    TextView txtpassword1;
    TextView txtpassword2;
    DbHelper controlador_db;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        btnHome = findViewById(R.id.btnHome);
        btnRegister = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.textEmail);
        txtpassword1 = findViewById(R.id.txtPaswd);
        txtpassword2 = findViewById(R.id.txtPasswdConfirm);
        btnRegister.setOnClickListener(this);
        btnHome.setOnClickListener(this);

        controlador_db = new DbHelper(getApplicationContext());
        //SQLiteDatabase db = controlador_db.getWritableDatabase();
        //controlador_db.insertarUsuarios(new Usuario("fernando@fernando.com","fernando"));
        //Log.i("usuarios", "onCreate: Usuario Creado");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnHome.getId()) {
            Intent intent  = new Intent (this, MainActivity.class);
            startActivity(intent);
        }
        if(view.getId() == btnRegister.getId()){
            String password1 = txtpassword1.getText().toString();
            String password2 = txtpassword2.getText().toString();
            System.out.println(password1 +" " +password2 + " " + txtEmail.getText().toString());
            if(password1.equals(password2)){
                if(controlador_db.comprobarusuarios(new Usuario(txtEmail.getText().toString(), password1)) == false) {
                    String email = txtEmail.getText().toString();
                    long id = controlador_db.insertarUsuarios(new Usuario(email, password1));
                    System.out.println(email + " " + password1);
                    Toast.makeText(getApplicationContext(), "USUARIO CREADO", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "EL USUARIO EXISTE", Toast.LENGTH_SHORT).show();
                }
            }
            }else{
                Toast.makeText(getApplicationContext(), "CONTRASEÑAS DIFERENTES", Toast.LENGTH_SHORT).show();
                System.out.println("ERROR CONTRASEÑAS DIFERENTES");
            }
        }
    }
