package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.modelo.Usuario;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    public static String us;
    DbHelper controlador_db;

    Usuario user;
    EditText email;
    EditText password;
    Switch showpass;
    Button update;
    Button EditAcc;
    Button deleteAcc;
    Button Logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        controlador_db = new DbHelper(getApplicationContext());

        email = findViewById(R.id.edtxtUser);
        password = findViewById(R.id.edtextPassword);

        showpass = findViewById(R.id.switchPass);
        update = findViewById(R.id.btnUpdate);
        EditAcc = findViewById(R.id.btnCambiarDatos);
        EditAcc.setOnClickListener(this);
        update.setOnClickListener(this);
        deleteAcc = findViewById(R.id.btnDeleteAccount);
        deleteAcc.setOnClickListener(this);
        Logout = findViewById(R.id.btnLogOut);
        Logout.setOnClickListener(this);

        password.setEnabled(false);
        email.setEnabled(false);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Log.i("checkPass", "seleccionado: ");
                    password.setInputType(InputType.TYPE_CLASS_TEXT);

                }else{
                    Log.i("checkPass", "no seleccionado: ");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });


        Bundle parametros = this.getIntent().getExtras();
        user = (Usuario) parametros.getSerializable("user");
        Log.i("user", "onCreate: "+user.getPassword());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        Toast.makeText(getApplicationContext(), user.getEmail().toString() + " " + user.getPassword().toString(), Toast.LENGTH_SHORT).show();
        //  showAccount(user);

    }

    public void showAccount(Usuario user){
        email.setText(user.getEmail().toString());
        password.setText(user.getPassword().toString());

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == EditAcc.getId()){
           if(password.isEnabled() ) {
               password.setEnabled(false);
           }else{
               password.setEnabled(true);
           }
        }
        if(view.getId() == update.getId()){
            Usuario u = new Usuario(email.getText().toString(), password.getText().toString());
            long camposAfectados = controlador_db.updateUsuarios(u);
            if(camposAfectados != -1){
                Toast.makeText(this, "CAMPOS NO CAMBIADOS", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "CAMPOS CAMBIADOS", Toast.LENGTH_SHORT).show();

            }
        }
        if(view.getId() == deleteAcc.getId()){
            Usuario u = new Usuario(email.getText().toString(), password.getText().toString());
            long camposAfectados = controlador_db.deleteUsuarios(u);
            if (camposAfectados != -1) {
                Toast.makeText(this, "CUENTA CERRADA", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "CUENTA NO CERRADA", Toast.LENGTH_SHORT).show();


            }
        }
        if(view.getId() == Logout.getId()){
            Toast.makeText(this, "SESION CERRADA", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}