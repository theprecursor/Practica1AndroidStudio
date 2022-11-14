package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.modelo.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogIn;
    Button btnSignIn;
    EditText Email;
    EditText Password;
    DbHelper controlador_db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = findViewById(R.id.edtextEmail);
        Password = findViewById(R.id.edtextPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        controlador_db = new DbHelper(getApplicationContext());
        mostrarUsuarios();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnLogIn.getId()){
            controlador_db= new DbHelper(getApplicationContext());
            String email = Email.getText().toString();
            String pass = Password.getText().toString();


            Usuario usuarioLogin = controlador_db.listarUsuariosBy(new Usuario(email,pass));



            if(usuarioLogin != null ){
                Toast.makeText(getApplicationContext(), "SESION INICIADA", Toast.LENGTH_SHORT).show();
                Bundle extras = new Bundle();
                extras.putSerializable("user",usuarioLogin); // se obtiene el valor mediante getString(...)
                Intent intent = new Intent(this, UserActivity.class);
                //Agrega el objeto bundle a el Intne
                intent.putExtras(extras);
                //Inicia Activity
                startActivity(intent);
            }else if(usuarioLogin == null){
                System.out.println("ERROR SESION NO INICIADA");
                Toast.makeText(getApplicationContext(), "ERROR SESION NO INICIADA", Toast.LENGTH_SHORT).show();
            }
            System.out.println(email + " " + pass);
        }

        if(view.getId() == btnSignIn.getId()){
            Intent intent  = new Intent (this, SignInActivity.class);

            startActivity(intent);
            finish();
        }
    }


    public void mostrarUsuarios(){
        controlador_db= new DbHelper(getApplicationContext());
        List<Usuario> usuarios = controlador_db.listarUsuarios();
        for (Usuario u:usuarios) {
            Log.i("usuarios", "mostrarUsuarios: "+u.toString());
        }

        if(usuarios.isEmpty()){
            Log.i("usuarios", "No hay usuarios");
        }
    }

}